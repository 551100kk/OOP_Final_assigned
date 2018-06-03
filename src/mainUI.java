import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import tools.BuyTicket;
import tools.SearchByID;
import tools.SearchTicket;
import tools.TimeTableParser;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.SQLException;
import java.awt.event.ActionEvent;
import java.text.SimpleDateFormat;
import java.util.*;
import javax.swing.JTabbedPane;

import data.ConstVar;
import data.Station;
import data.Train;

import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JCheckBox;
import java.awt.Color;
import javax.swing.SwingConstants;

public class MainUI {
	private JFrame frame;
	private MyTimeTable winTimeTable;
	private DiscountTimeTable winDiscount;
	private JTextField uidField;
	private BuyTicket nextGoTicket;
	private BuyTicket nextBackTicket;
	private JTextField uid_ok;
	private JTextField code_ok;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainUI window = new MainUI();
					window.frame.setVisible(true);
					window.winTimeTable = new MyTimeTable();
					window.winDiscount = new DiscountTimeTable();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public MainUI() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setTitle("\u9AD8\u9435\u8A02\u7968\u7CFB\u7D71");
		frame.setBounds(100, 100, 716, 597);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JButton updateButton = new JButton("Update Time Table");
		updateButton.setBounds(0, 0, 704, 23);
		updateButton.addActionListener(new ActionListener() {
			/**
			 * Update time table database.
			 */
			public void actionPerformed(ActionEvent arg0) {
				updateButton.setEnabled(false);
				Date myDate = new Date();
				SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
				try {
					for (int i = 0; i < ConstVar.FETCH_DAYS; i++) {
						String date = formatter.format(myDate);
						new TimeTableParser().updateDay(date);
						Calendar c = Calendar.getInstance();
						c.setTime(myDate);
						c.add(Calendar.DATE, 1);
						myDate = c.getTime();
					}
					JOptionPane.showMessageDialog(null, "Successed.", "InfoBox: Finish",
							JOptionPane.INFORMATION_MESSAGE);
				} catch (SQLException | IOException e) {
					System.out.println("Error while parsing time table");
					e.printStackTrace();
				}
				updateButton.setEnabled(true);
			}
		});
		frame.getContentPane().setLayout(null);
		frame.getContentPane().add(updateButton);

		JTabbedPane tabbedPane_1 = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane_1.setBounds(0, 206, 694, 351);
		frame.getContentPane().add(tabbedPane_1);

		JPanel panel = new JPanel();
		tabbedPane_1.addTab("\u8A02\u7968", null, panel, null);
		panel.setLayout(null);

		JLabel label_6 = new JLabel("\u65E5\u671F");
		label_6.setFont(new Font("新細明體", Font.PLAIN, 12));
		label_6.setBounds(20, 66, 32, 15);
		panel.add(label_6);

		JComboBox<String> buyDate = new JComboBox<String>();
		buyDate.setBounds(54, 63, 271, 21);
		panel.add(buyDate);

		JLabel label_9 = new JLabel("\u8D77\u7AD9");
		label_9.setFont(new Font("新細明體", Font.PLAIN, 12));
		label_9.setBounds(20, 97, 32, 15);
		panel.add(label_9);

		JComboBox<String> buyStart = new JComboBox<String>();
		buyStart.setBounds(54, 94, 60, 21);
		panel.add(buyStart);

		JButton buySearch = new JButton("\u67E5\u8A62");
		buySearch.setBounds(374, 149, 305, 23);
		panel.add(buySearch);

		JLabel lblid = new JLabel("\u4F7F\u7528\u8005ID");
		lblid.setFont(new Font("新細明體", Font.PLAIN, 12));
		lblid.setBounds(20, 38, 54, 15);
		panel.add(lblid);

		uidField = new JTextField();
		uidField.setBounds(84, 35, 241, 21);
		panel.add(uidField);
		uidField.setColumns(10);

		JLabel label_7 = new JLabel("\u8A16\u7AD9");
		label_7.setFont(new Font("新細明體", Font.PLAIN, 12));
		label_7.setBounds(124, 97, 32, 15);
		panel.add(label_7);

		JComboBox<String> buyEnd = new JComboBox<String>();
		buyEnd.setBounds(158, 94, 60, 21);
		panel.add(buyEnd);

		JLabel label_8 = new JLabel("\u5F35\u6578");
		label_8.setFont(new Font("新細明體", Font.PLAIN, 12));
		label_8.setBounds(230, 97, 32, 15);
		panel.add(label_8);

		JComboBox<String> ticketCount = new JComboBox<String>();
		ticketCount.setBounds(265, 94, 60, 21);
		panel.add(ticketCount);

		JLabel label_10 = new JLabel("\u7968\u7A2E");
		label_10.setFont(new Font("新細明體", Font.PLAIN, 12));
		label_10.setBounds(142, 125, 32, 15);
		panel.add(label_10);

		JComboBox<String> seatType = new JComboBox(ConstVar.TICKET_TYPE);
		seatType.setBounds(184, 122, 141, 21);
		panel.add(seatType);

		JLabel label_11 = new JLabel("\u4F4D\u7F6E");
		label_11.setFont(new Font("新細明體", Font.PLAIN, 12));
		label_11.setBounds(20, 125, 32, 15);
		panel.add(label_11);

		JComboBox<String> seatSide = new JComboBox(ConstVar.SEAT_TYPE);
		seatSide.setBounds(54, 122, 78, 21);
		panel.add(seatSide);

		JCheckBox goBack = new JCheckBox("\u56DE\u7A0B");

		goBack.setFont(new Font("新細明體", Font.PLAIN, 12));
		goBack.setBounds(377, 66, 60, 23);
		panel.add(goBack);

		JLabel label_12 = new JLabel("\u8ECA\u6B21\u67E5\u8A62");
		label_12.setFont(label_12.getFont().deriveFont(label_12.getFont().getStyle() | Font.BOLD));
		label_12.setBounds(10, 10, 60, 15);
		panel.add(label_12);

		JLabel label_13 = new JLabel("\u8A02\u55AE\u78BA\u8A8D");
		label_13.setFont(label_13.getFont().deriveFont(label_13.getFont().getStyle() | Font.BOLD));
		label_13.setBounds(10, 182, 679, 15);
		panel.add(label_13);

		JLabel status = new JLabel("\u5C1A\u672A\u67E5\u8A62");
		status.setHorizontalAlignment(SwingConstants.CENTER);
		status.setForeground(Color.BLUE);
		status.setBounds(74, 182, 615, 15);
		panel.add(status);

		JLabel label_15 = new JLabel("\u53BB\u7A0B");
		label_15.setFont(new Font("新細明體", Font.PLAIN, 12));
		label_15.setBounds(20, 210, 32, 15);
		panel.add(label_15);

		JComboBox<String> goCandidate = new JComboBox<String>();
		goCandidate.setEnabled(false);
		goCandidate.setBounds(52, 207, 627, 21);
		panel.add(goCandidate);

		JLabel label_16 = new JLabel("\u56DE\u7A0B");
		label_16.setFont(new Font("新細明體", Font.PLAIN, 12));
		label_16.setBounds(20, 238, 32, 15);
		panel.add(label_16);

		JComboBox<String> backCandidate = new JComboBox<String>();
		backCandidate.setEnabled(false);
		backCandidate.setBounds(52, 235, 627, 21);
		panel.add(backCandidate);

		JLabel bookCodeText = new JLabel("\u8A02\u55AE\u7DE8\u865F");
		bookCodeText.setFont(new Font("新細明體", Font.PLAIN, 12));
		bookCodeText.setBounds(20, 266, 60, 15);
		panel.add(bookCodeText);

		JLabel bookCode = new JLabel("192873645");
		bookCode.setEnabled(false);
		bookCode.setHorizontalAlignment(SwingConstants.TRAILING);
		bookCode.setFont(new Font("新細明體", Font.PLAIN, 12));
		bookCode.setBounds(90, 266, 235, 15);
		panel.add(bookCode);

		JLabel label_19 = new JLabel("\u7E3D\u50F9\u683C");
		label_19.setFont(new Font("新細明體", Font.PLAIN, 12));
		label_19.setBounds(374, 266, 60, 15);
		panel.add(label_19);

		JLabel totalPrice = new JLabel("0");
		totalPrice.setEnabled(false);
		totalPrice.setHorizontalAlignment(SwingConstants.TRAILING);
		totalPrice.setFont(new Font("新細明體", Font.PLAIN, 12));
		totalPrice.setBounds(444, 266, 235, 15);
		panel.add(totalPrice);

		JButton bookTicket = new JButton("\u8A02\u7968");
		bookTicket.setEnabled(false);
		bookTicket.setBounds(20, 291, 659, 23);
		panel.add(bookTicket);

		JLabel label_21 = new JLabel("\u51FA\u767C\u6642\u9593");
		label_21.setFont(new Font("新細明體", Font.PLAIN, 12));
		label_21.setBounds(85, 153, 57, 15);
		panel.add(label_21);

		JComboBox<String> goTime = new JComboBox<String>();
		goTime.setBounds(140, 150, 60, 21);
		panel.add(goTime);

		JLabel label_22 = new JLabel("\u62B5\u9054\u6642\u9593");
		label_22.setFont(new Font("新細明體", Font.PLAIN, 12));
		label_22.setBounds(210, 153, 57, 15);
		panel.add(label_22);

		JComboBox<String> backTime = new JComboBox<String>();
		backTime.setBounds(265, 150, 60, 21);
		panel.add(backTime);

		JLabel label_18 = new JLabel("\u51FA\u767C\u6642\u9593");
		label_18.setFont(new Font("新細明體", Font.PLAIN, 12));
		label_18.setBounds(439, 69, 57, 15);
		panel.add(label_18);

		JComboBox<String> goTime2 = new JComboBox<String>();
		goTime2.setEnabled(false);
		goTime2.setBounds(494, 66, 60, 21);
		panel.add(goTime2);

		JLabel label_20 = new JLabel("\u62B5\u9054\u6642\u9593");
		label_20.setFont(new Font("新細明體", Font.PLAIN, 12));
		label_20.setBounds(564, 69, 57, 15);
		panel.add(label_20);

		JComboBox<String> backTime2 = new JComboBox<String>();
		backTime2.setEnabled(false);
		backTime2.setBounds(619, 66, 60, 21);
		panel.add(backTime2);

		JLabel lblNewLabel_1 = new JLabel("\u53BB\u7A0B");
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.TRAILING);
		lblNewLabel_1.setBounds(37, 153, 32, 15);
		panel.add(lblNewLabel_1);

		JLabel label_23 = new JLabel("\u56DE\u7A0B\u65E5\u671F");
		label_23.setFont(new Font("新細明體", Font.PLAIN, 12));
		label_23.setBounds(374, 41, 54, 15);
		panel.add(label_23);

		JComboBox<String> buyBackDate = new JComboBox<String>();
		buyBackDate.setEnabled(false);
		buyBackDate.setBounds(438, 38, 241, 21);
		panel.add(buyBackDate);

		// check go-back
		goBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				goTime2.setEnabled(goBack.isSelected());
				backTime2.setEnabled(goBack.isSelected());
				buyBackDate.setEnabled(goBack.isSelected());
			}
		});
		// but ticket - search
		buySearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Random rn = new Random();
				// information
				String uid = uidField.getText();
				if (uid.equals("")) {
					JOptionPane.showMessageDialog(null, "使用者ID不可為空!", "InfoBox: Failed", JOptionPane.ERROR_MESSAGE);
					return;
				}
				String date = (String) buyDate.getSelectedItem();
				String backDate = (String) buyBackDate.getSelectedItem();
				int code = Math.abs(rn.nextInt());

				// user
				int startStationIndex = buyStart.getSelectedIndex();
				int endStationIndex = buyEnd.getSelectedIndex();
				if (startStationIndex == endStationIndex) {
					JOptionPane.showMessageDialog(null, "起站和訖站不可以一樣!", "InfoBox: Failed", JOptionPane.ERROR_MESSAGE);
					return;
				}
				int count = ticketCount.getSelectedIndex() + 1;
				if (goBack.isSelected() && count > 5) {
					JOptionPane.showMessageDialog(null, "來回票最多只能買5張!", "InfoBox: Failed", JOptionPane.ERROR_MESSAGE);
					return;
				}
				int goStartTime = Integer.parseInt(((String) goTime.getSelectedItem()).replace(":", ""));
				int goEndTime = Integer.parseInt(((String) backTime.getSelectedItem()).replace(":", ""));
				int backStartTime = Integer.parseInt(((String) goTime2.getSelectedItem()).replace(":", ""));
				int backEndTime = Integer.parseInt(((String) backTime2.getSelectedItem()).replace(":", ""));
				if (goBack.isSelected() && buyBackDate.getSelectedIndex() < buyDate.getSelectedIndex()) {
					JOptionPane.showMessageDialog(null, "回程日期不可比出發時間早", "InfoBox: Failed", JOptionPane.ERROR_MESSAGE);
					return;
				}
				if (goStartTime >= goEndTime) {
					JOptionPane.showMessageDialog(null, "[去程] 出發時間必須早於抵達時間!", "InfoBox: Failed",
							JOptionPane.ERROR_MESSAGE);
					return;
				}
				if (goBack.isSelected() && backStartTime >= backEndTime) {
					JOptionPane.showMessageDialog(null, "[回程] 出發時間必須早於抵達時間!", "InfoBox: Failed",
							JOptionPane.ERROR_MESSAGE);
					return;
				}

				int goEarly = 0;
				if (buyDate.getSelectedIndex() >= 5)
					goEarly = 1;
				int backEarly = 0;
				if (buyBackDate.getSelectedIndex() >= 5)
					backEarly = 1;
				int side = seatSide.getSelectedIndex();
				int type = seatType.getSelectedIndex();
				SearchTicket go = new SearchTicket();
				SearchTicket back = new SearchTicket();
				go.search(goCandidate, date, startStationIndex, endStationIndex, goStartTime, goEndTime, count, side,
						type, goEarly);
				if (goBack.isSelected())
					back.search(backCandidate, backDate, endStationIndex, startStationIndex, backStartTime, backEndTime,
							count, side, type, backEarly);
				if (go.can.isEmpty() || goBack.isSelected() && back.can.isEmpty()) {
					JOptionPane.showMessageDialog(null, "座位已售完", "InfoBox: Failed", JOptionPane.ERROR_MESSAGE);
					return;
				}

				// calculate price (defualt - random)
				int price = 2000;

				goCandidate.setEnabled(true);
				backCandidate.setEnabled(true);
				bookCode.setEnabled(true);
				totalPrice.setEnabled(true);
				bookTicket.setEnabled(true);
				status.setText("請選擇班次");
				totalPrice.setText(Integer.toString(price));
				bookCode.setText(Integer.toString(code));

				Train go_train = null;
				Train back_train = null;
				go_train = go.can.elementAt(goCandidate.getSelectedIndex());
				if (goBack.isSelected())
					back_train = back.can.elementAt(goCandidate.getSelectedIndex());
				nextGoTicket = null;
				nextBackTicket = null;
				nextGoTicket = new BuyTicket(go_train, uid, code, startStationIndex, endStationIndex, count, side, type, price);
				if (goBack.isSelected())
					nextBackTicket = new BuyTicket(back_train, uid, code, endStationIndex, startStationIndex, count, side, type, price);
			}
		});

		bookTicket.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				goCandidate.setEnabled(false);
				backCandidate.setEnabled(false);
				bookCode.setEnabled(false);
				totalPrice.setEnabled(false);
				bookTicket.setEnabled(false);
				try {
					if (nextGoTicket != null) nextGoTicket.Commit();
					if (nextBackTicket != null) nextBackTicket.Commit(); 
					JOptionPane.showMessageDialog(null, "訂票完成", "InfoBox: Successed", JOptionPane.INFORMATION_MESSAGE);		
				} catch (Exception e) {
					JOptionPane.showMessageDialog(null, "訂票失敗", "InfoBox: Failed", JOptionPane.ERROR_MESSAGE);
					e.printStackTrace();
				}
							
			}
		});
		
		JPanel panel_1 = new JPanel();
		tabbedPane_1.addTab("\u9000\u7968\u8207\u4FEE\u6539", null, panel_1, null);
		panel_1.setLayout(null);

		JPanel panel_3 = new JPanel();
		tabbedPane_1.addTab("\u67E5\u8A62", null, panel_3, null);
		panel_3.setLayout(null);

		JLabel lblNewLabel = new JLabel("\u7576\u65E5\u6642\u523B\u8868\u67E5\u8A62");
		lblNewLabel.setFont(lblNewLabel.getFont().deriveFont(lblNewLabel.getFont().getStyle() | Font.BOLD));
		lblNewLabel.setBounds(10, 10, 92, 15);
		panel_3.add(lblNewLabel);

		JComboBox<String> dateTimeTable = new JComboBox<String>();
		dateTimeTable.setBounds(54, 32, 184, 21);
		initializeDate(dateTimeTable);
		panel_3.add(dateTimeTable);

		JLabel dateLabel = new JLabel("\u65E5\u671F");
		dateLabel.setFont(new Font("新細明體", Font.PLAIN, 12));
		dateLabel.setBounds(20, 35, 32, 15);
		panel_3.add(dateLabel);

		JButton dateSearch = new JButton("\u67E5\u8A62");
		dateSearch.addActionListener(new ActionListener() {
			/**
			 * Show time table.
			 */
			public void actionPerformed(ActionEvent e) {
				winTimeTable.setTitle((String) dateTimeTable.getSelectedItem());
				winTimeTable.setTimeDown(((String) dateTimeTable.getSelectedItem()).replaceAll("/", ""));
				winTimeTable.setTimeUp(((String) dateTimeTable.getSelectedItem()).replaceAll("/", ""));
				winTimeTable.show();
			}
		});
		dateSearch.setBounds(248, 31, 87, 23);
		panel_3.add(dateSearch);

		// Cheap Ticket
		JLabel label = new JLabel("\u512A\u60E0\u8ECA\u6B21\u67E5\u8A62");
		label.setFont(label.getFont().deriveFont(label.getFont().getStyle() | Font.BOLD));
		label.setBounds(10, 63, 92, 15);
		panel_3.add(label);

		JLabel label_1 = new JLabel("\u65E5\u671F");
		label_1.setFont(new Font("新細明體", Font.PLAIN, 12));
		label_1.setBounds(20, 91, 32, 15);
		panel_3.add(label_1);

		JComboBox<String> dateCheap = new JComboBox<String>();
		dateCheap.setBounds(54, 88, 281, 21);
		panel_3.add(dateCheap);

		JLabel label_2 = new JLabel("\u51FA\u767C\u6642\u9593");
		label_2.setFont(new Font("新細明體", Font.PLAIN, 12));
		label_2.setBounds(20, 119, 57, 15);
		panel_3.add(label_2);

		JComboBox<String> startTime = new JComboBox<String>();
		startTime.setBounds(75, 116, 92, 21);
		panel_3.add(startTime);

		JLabel label_3 = new JLabel("\u62B5\u9054\u6642\u9593");
		label_3.setFont(new Font("新細明體", Font.PLAIN, 12));
		label_3.setBounds(188, 119, 57, 15);
		panel_3.add(label_3);

		JComboBox<String> endTime = new JComboBox<String>();
		endTime.setBounds(243, 116, 92, 21);
		panel_3.add(endTime);

		JComboBox<String> startStation = new JComboBox<String>();
		startStation.setBounds(54, 144, 70, 21);
		panel_3.add(startStation);

		JLabel label_4 = new JLabel("\u8D77\u7AD9");
		label_4.setFont(new Font("新細明體", Font.PLAIN, 12));
		label_4.setBounds(20, 147, 32, 15);
		panel_3.add(label_4);

		JComboBox<String> endStation = new JComboBox<String>();
		endStation.setBounds(168, 144, 70, 21);
		panel_3.add(endStation);

		JLabel label_5 = new JLabel("\u8A16\u7AD9");
		label_5.setFont(new Font("新細明體", Font.PLAIN, 12));
		label_5.setBounds(134, 147, 32, 15);
		panel_3.add(label_5);

		JButton cheapSearch = new JButton("\u67E5\u8A62");
		cheapSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int direction = 0;
				if (startTime.getSelectedIndex() >= endTime.getSelectedIndex()) {
					JOptionPane.showMessageDialog(null, "出發時間必須早於抵達時間!", "InfoBox: Failed", JOptionPane.ERROR_MESSAGE);
					return;
				}
				if (startStation.getSelectedIndex() == endStation.getSelectedIndex()) {
					JOptionPane.showMessageDialog(null, "起站和訖站不可以一樣!", "InfoBox: Failed", JOptionPane.ERROR_MESSAGE);
					return;
				}
				if (startStation.getSelectedIndex() > endStation.getSelectedIndex())
					direction = 1;

				// Start Search
				try {
					winDiscount.setTitle(dateCheap.getSelectedItem() + " From " + startStation.getSelectedItem()
							+ " To " + endStation.getSelectedItem());
					winDiscount.showDiscount(Integer.parseInt(((String) dateCheap.getSelectedItem()).replace("/", "")),
							direction, Integer.parseInt(((String) startTime.getSelectedItem()).replace(":", "")),
							Integer.parseInt(((String) endTime.getSelectedItem()).replace(":", "")),
							startStation.getSelectedIndex(), endStation.getSelectedIndex());
					winDiscount.show();
				} catch (Exception e) {
					e.printStackTrace();
					JOptionPane.showMessageDialog(null, "無優惠車票!", "InfoBox: Failed", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		cheapSearch.setBounds(248, 143, 87, 23);
		panel_3.add(cheapSearch);

		initializeDate(buyDate);
		initializeDate(buyBackDate);
		initializeTime(goTime);
		initializeTime(backTime);
		initializeTime(goTime2);
		initializeTime(backTime2);
		initializeStaion(buyStart);
		initializeStaion(buyEnd);
		initializeCount(ticketCount);

		initializeDate(dateCheap);
		initializeTime(startTime);
		initializeTime(endTime);
		initializeStaion(startStation);
		initializeStaion(endStation);
		
		JLabel label_14 = new JLabel("\u67E5\u8A62\u8A02\u7968 - \u6709\u8A02\u4F4D\u4EE3\u865F");
		label_14.setFont(label_14.getFont().deriveFont(label_14.getFont().getStyle() | Font.BOLD));
		label_14.setBounds(364, 10, 212, 15);
		panel_3.add(label_14);
		
		JLabel label_17 = new JLabel("\u4F7F\u7528\u8005ID");
		label_17.setFont(new Font("新細明體", Font.PLAIN, 12));
		label_17.setBounds(374, 38, 54, 15);
		panel_3.add(label_17);
		
		uid_ok = new JTextField();
		uid_ok.setColumns(10);
		uid_ok.setBounds(438, 35, 241, 21);
		panel_3.add(uid_ok);
		
		JLabel label_24 = new JLabel("\u8A02\u4F4D\u4EE3\u865F");
		label_24.setFont(new Font("新細明體", Font.PLAIN, 12));
		label_24.setBounds(374, 66, 54, 15);
		panel_3.add(label_24);
		
		code_ok = new JTextField();
		code_ok.setColumns(10);
		code_ok.setBounds(438, 63, 241, 21);
		panel_3.add(code_ok);
		
		JButton search_ok = new JButton("\u67E5\u8A62");
		search_ok.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String uid = uid_ok.getText();
				String code = code_ok.getText();
				SearchByID search = new SearchByID(uid, code);
				search.exec();
			}
		});
		search_ok.setBounds(374, 91, 305, 23);
		panel_3.add(search_ok);

		ImageIcon ii = new ImageIcon("img/train.png");
		JLabel image = new JLabel(ii);
		image.setBounds(0, 20, 694, 176);
		frame.getContentPane().add(image);

	}

	/**
	 * Initialize Date Combo Box
	 * 
	 * @param box
	 *            a Combo Box
	 */
	private void initializeDate(JComboBox<String> box) {
		Date myDate = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");
		for (int i = 0; i < ConstVar.FETCH_DAYS; i++) {
			String date = formatter.format(myDate);
			box.insertItemAt(date, i);

			Calendar c = Calendar.getInstance();
			c.setTime(myDate);
			c.add(Calendar.DATE, 1);
			myDate = c.getTime();
		}
		box.setSelectedItem(box.getItemAt(0));
	}

	/**
	 * Initialize Time Combo Box
	 * 
	 * @param box
	 *            a Combo Box
	 */
	private void initializeTime(JComboBox<String> box) {
		for (int hour = 0; hour < 24; hour++) {
			for (int min = 0; min < 60; min++) {
				box.addItem(String.format("%02d:%02d", hour, min));
			}
		}
	}

	/**
	 * Initialize Staion Combo Box
	 * 
	 * @param box
	 *            a Combo Box
	 */
	private void initializeStaion(JComboBox<String> box) {
		for (String sta : Station.CHI_NAME) {
			box.addItem(sta);
		}
	}

	/**
	 * Initialize Count Combo Box
	 * 
	 * @param box
	 *            a Combo Box
	 */
	private void initializeCount(JComboBox<String> box) {
		for (int i = 0; i < 10; i++) {
			box.addItem(Integer.toString(i + 1));
		}
	}
}
