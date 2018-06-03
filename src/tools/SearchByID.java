package tools;

import java.sql.SQLException;
import java.util.Vector;

import javax.swing.JOptionPane;

import data.ConstVar;
import data.Station;
import tools.MysqlExe.RetVal;

public class SearchByID {
	private String uid;
	private String code;
	
	public SearchByID(String uid, String code) {
		this.uid = uid;
		this.code = code;
	}

	public void exec() {
		Vector<String> arr = new Vector<String>();
		RetVal ret = null;
		boolean userCheck = true;
		try {
			ret = MysqlExe.execQuery(String.format(
					"SELECT * FROM tickets WHERE code=\"%s\"", code
					));
			while (ret.res.next()) {
				String uid = ret.res.getString("uid");
				if (!uid.equals(this.uid)) userCheck = false;
				int train_id = ret.res.getInt("train_id");
				int date = ret.res.getInt("date");
				int st = ret.res.getInt("start");
				int ed = ret.res.getInt("end");
				int t1 = ret.res.getInt("start_time");
				int t2 = ret.res.getInt("end_time");
				int dur = ConstVar.getDur(t1, t2);
				String type = ConstVar.TICKET_TYPE_2[ret.res.getInt("ticketsType")];
				String seat = ret.res.getString("seat_id");
				StringBuffer sb = new StringBuffer();
				sb.append("車次: " + train_id);
				sb.append(", 日期: " + date);
				sb.append(", 起/訖站: " + Station.CHI_NAME[st] + "->" + Station.CHI_NAME[ed]);
				sb.append(", 出發/到達時間: " + t1 + "->" + t2);
				sb.append(", 行車時間: " + dur);
				sb.append(", 座位: " + seat);
				sb.append(", 類型: " + type);
				arr.add(sb.toString());
			}
			ret.conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (ret.conn != null) ret.conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		if (arr.isEmpty()) {
			JOptionPane.showMessageDialog(null, "您輸入的訂位代號有誤，請重新輸入!", "InfoBox: Failed",
					JOptionPane.ERROR_MESSAGE);
			return;
		}
		if (!userCheck) {
			JOptionPane.showMessageDialog(null, "您輸入的身份識別號碼有誤，請重新輸入!", "InfoBox: Failed",
					JOptionPane.ERROR_MESSAGE);
			return;
		}
		JOptionPane.showMessageDialog(null, String.join("\n", arr), "InfoBox: 查詢訂票",
				JOptionPane.INFORMATION_MESSAGE);
	}
}
