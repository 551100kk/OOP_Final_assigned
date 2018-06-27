package controll;

import java.sql.SQLException;
import java.util.Vector;
import javax.swing.JOptionPane;

import controll.MysqlExe.RetVal;

/**
 * This class is used to update the ticket by the booking code.
 * @author Jerry
 */
public class UpdateTicket {
	private String uid;
	private String code;
	private int count;
	
	/**
	 * @param uid User ID.
	 * @param code Booking code.
	 * @param count Ticket count.
	 */
	public UpdateTicket(String uid, String code, int count) {
		this.uid = uid;
		this.code = code;
		this.count = count;
	}
	
	/**
	 * Update the ticket.
	 */
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
				String seat = ret.res.getString("seat_id");
				arr.add(seat);
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
		if (count < 0 || arr.size() < count) {
			JOptionPane.showMessageDialog(null, "票數不足!", "InfoBox: Failed",
					JOptionPane.ERROR_MESSAGE);
			return;
		}
		try {
			MysqlExe.execStmt(String.format("DELETE FROM tickets WHERE code=%s LIMIT %d", code, arr.size() - count));
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "更新失敗!", "InfoBox: Failed",
					JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
			return;
		}
		JOptionPane.showMessageDialog(null, "更新成功!", "InfoBox: 查詢訂票",
				JOptionPane.INFORMATION_MESSAGE);
	}
}
