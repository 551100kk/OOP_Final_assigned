import java.awt.EventQueue;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import data.constVar;
import data.station;
import tools.mysqlExe;

import java.awt.BorderLayout;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;

public class discountTimeTable {
	private JFrame frame;
	private DefaultTableModel model;
	private JTable table;
	public final String dirTable[] = {"timeTable_down", "timeTable_up"};
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					discountTimeTable window = new discountTimeTable();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	/**
	 * Show the frame.
	 */
	public void show() {
		frame.setVisible(true);
	}
	
	/**
	 * Set title.
	 * @param title title
	 */
	public void setTitle(String title) {
		frame.setTitle(title);
	}

	/**
	 * Create the application.
	 */
	public discountTimeTable() {
		initialize();
	}
	
	public void showDiscount(int date, int direction, int startTime, int endTime, int startStation, int endStation) throws SQLException, ParseException {
//		System.out.println(startStation);
//		System.out.println(endStation);
		int weekday = constVar.getWeekDay(date);
		String start = station.ENG_NAME[startStation];
		String end = station.ENG_NAME[endStation];
		String startC = station.CHI_NAME[startStation];
		String endC = station.CHI_NAME[endStation];
		if (direction == 1) {
			startStation = station.ENG_NAME.length - 1 - startStation;
			endStation = station.ENG_NAME.length - 1 - endStation;
		}
		model.setRowCount(0);
		mysqlExe.RetVal ret = null;
		try {
			ret = mysqlExe.execQuery(String.format(
					"Select * FROM %s WHERE date = %s AND %s != -1 AND %s != -1 AND %s >= %d AND %s <= %d AND train_id IN "
					+ "(SELECT train_id FROM earlyDiscount WHERE weekday = %d UNION SELECT train_id FROM studentDiscount WHERE weekday = %d)"
						, dirTable[direction], date, start, end, start, startTime, end, endTime, weekday, weekday));
			while (ret.res.next()) {
				String train_id = ret.res.getString("train_id");
				int st = ret.res.getInt(start);
				int ed = ret.res.getInt(end);
				StringBuffer early = new StringBuffer();
				StringBuffer student = new StringBuffer();
				
				// get discount by train id
				mysqlExe.RetVal rsDis = null;
				try {
					rsDis = mysqlExe.execQuery(String.format("SELECT * FROM earlyDiscount WHERE weekday = %d AND train_id = %s", weekday, train_id));
					while (rsDis.res.next()) {
						if (early.length() > 0) early.append(" / ");
						early.append(rsDis.res.getString("earlyD"));
					}
					rsDis.conn.close();
					rsDis = mysqlExe.execQuery(String.format("SELECT * FROM studentDiscount WHERE weekday = %d AND train_id = %s", weekday, train_id));
					while (rsDis.res.next()) {
						if (student.length() > 0) student.append(" / ");
						student.append(rsDis.res.getString("studentD") + "");
					}
					rsDis.conn.close();
				} catch (SQLException e) {
					if (rsDis.conn != null) rsDis.conn.close();
					throw e;
				}
				
				// add new row 
				int duration = (ed / 100 * 60 + ed % 100) - (st / 100 * 60 + st % 100);
				Vector<String> row = new Vector<String>();
				row.add(train_id);
				row.add(startC);
				row.add(endC);
				row.add(student.toString());
				row.add(early.toString());
				row.add(String.format("%02d:%02d", st / 100, st % 100));
				row.add(String.format("%02d:%02d", ed / 100, ed % 100));
				row.add(String.format("%02d:%02d", duration / 60, duration % 60));
				model.addRow(row);
			}
		} catch (SQLException e) {
			if (ret.conn != null) ret.conn.close();
			throw e;
		}
		if (model.getRowCount() == 0) {
			frame.setVisible(false);
			JOptionPane.showMessageDialog(null, "�L�u�f����!", "InfoBox: Failed", JOptionPane.ERROR_MESSAGE);
		}
		
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		
		// create table model
		model = new DefaultTableModel();
		model.addColumn("����");
		model.addColumn("�_��");
		model.addColumn("�W��");
		model.addColumn("�j�ǥͧ��");
		model.addColumn("�������");
		model.addColumn("�X�o�ɶ�");
		model.addColumn("��F�ɶ�");
		model.addColumn("�樮�ɶ�");
		frame.setBounds(100, 100, 750, 432);
		frame.getContentPane().setLayout(new BorderLayout(0, 0));
		
		JScrollPane scrollPane = new JScrollPane();
		frame.getContentPane().add(scrollPane);
		
		table = new JTable(model);
		scrollPane.setViewportView(table);
		
//		try {
//			showDiscount(20180522, 1, 0, 2359, 11, 1);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
	}
}
