import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import tools.timeTableParser;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.SQLException;
import java.awt.event.ActionEvent;
import java.text.SimpleDateFormat;
import java.util.*;
import javax.swing.JTabbedPane;

import data.constVar;
import data.station;

import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import java.awt.Font;

public class mainUI {
	private JFrame frame;
	private myTimeTable winTimeTable;
	private discountTimeTable winDiscount;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					mainUI window = new mainUI();
					window.frame.setVisible(true);
					window.winTimeTable = new myTimeTable();
					window.winDiscount = new discountTimeTable();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public mainUI() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setTitle("\u9AD8\u9435\u8A02\u7968\u7CFB\u7D71");
		frame.setBounds(100, 100, 366, 699);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JButton updateButton = new JButton("Update Time Table");
		updateButton.setBounds(0, 0, 350, 23);
		updateButton.addActionListener(new ActionListener() {
			/**
			 * Update time table database.
			 */
			public void actionPerformed(ActionEvent arg0) {
				updateButton.setEnabled(false);
				Date myDate = new Date();
				SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
				try {
					for (int i = 0; i < constVar.FETCH_DAYS; i++) {
						String date = formatter.format(myDate);
						new timeTableParser().updateDay(date);
						Calendar c = Calendar.getInstance(); 
						c.setTime(myDate); 
						c.add(Calendar.DATE, 1);
						myDate = c.getTime();
					}
					JOptionPane.showMessageDialog(null, "Successed.", "InfoBox: Finish", JOptionPane.INFORMATION_MESSAGE);
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
		tabbedPane_1.setBounds(0, 206, 350, 465);
		frame.getContentPane().add(tabbedPane_1);
		
		JPanel panel = new JPanel();
		tabbedPane_1.addTab("\u4E00\u822C\u8A02\u7968", null, panel, null);
		panel.setLayout(null);
		
		JPanel panel_1 = new JPanel();
		tabbedPane_1.addTab("\u9000\u7968\u8207\u4FEE\u6539", null, panel_1, null);
		panel_1.setLayout(null);
		
		JPanel panel_2 = new JPanel();
		tabbedPane_1.addTab("\u689D\u4EF6\u5F0F\u8A02\u7968", null, panel_2, null);
		panel_2.setLayout(null);
		
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
		label_1.setBounds(20, 91, 32, 15);
		panel_3.add(label_1);
		
		JComboBox<String> dateCheap = new JComboBox<String>();
		dateCheap.setBounds(54, 88, 281, 21);
		panel_3.add(dateCheap);
		
		JLabel label_2 = new JLabel("\u51FA\u767C\u6642\u9593");
		label_2.setBounds(20, 119, 57, 15);
		panel_3.add(label_2);
		
		JComboBox<String> startTime = new JComboBox<String>();
		startTime.setBounds(75, 116, 92, 21);
		panel_3.add(startTime);
		
		JLabel label_3 = new JLabel("\u62B5\u9054\u6642\u9593");
		label_3.setBounds(188, 119, 57, 15);
		panel_3.add(label_3);
		
		JComboBox<String> endTime = new JComboBox<String>();
		endTime.setBounds(243, 116, 92, 21);
		panel_3.add(endTime);
		
		JComboBox<String> startStation = new JComboBox<String>();
		startStation.setBounds(54, 144, 70, 21);
		panel_3.add(startStation);
		
		JLabel label_4 = new JLabel("\u8D77\u7AD9");
		label_4.setBounds(20, 147, 32, 15);
		panel_3.add(label_4);
		
		JComboBox<String> endStation = new JComboBox<String>();
		endStation.setBounds(168, 144, 70, 21);
		panel_3.add(endStation);
		
		JLabel label_5 = new JLabel("\u8A16\u7AD9");
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
				if (startStation.getSelectedIndex() > endStation.getSelectedIndex()) direction = 1;
				
				//Start Search
				try {
					winDiscount.setTitle(dateCheap.getSelectedItem() + " From " + startStation.getSelectedItem() + " To " + endStation.getSelectedItem());
					winDiscount.showDiscount(Integer.parseInt(((String) dateCheap.getSelectedItem()).replace("/", "")), direction, 
							Integer.parseInt(((String) startTime.getSelectedItem()).replace(":", "")),
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

		initializeDate(dateCheap);
		initializeTime(startTime);
		initializeTime(endTime);
		initializeStaion(startStation);
		initializeStaion(endStation);

		ImageIcon ii = new ImageIcon("train.png");	
		JLabel image = new JLabel(ii);
		image.setBounds(0, 20, 350, 176);
		frame.getContentPane().add(image);
	
		
	}
	
	private void initializeDate(JComboBox<String> box) {
		Date myDate = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");
		for (int i = 0; i < constVar.FETCH_DAYS; i++) {
			String date = formatter.format(myDate);
			box.insertItemAt(date, i);
			
			Calendar c = Calendar.getInstance(); 
			c.setTime(myDate); 
			c.add(Calendar.DATE, 1);
			myDate = c.getTime();
		}
		box.setSelectedItem(box.getItemAt(0));
	}
	
	private void initializeTime(JComboBox<String> box) {
		for (int hour = 0; hour < 24; hour++) {
			for (int min = 0; min < 60; min++) {
				box.addItem(String.format("%02d:%02d", hour, min));
			}
		}
	}	
	
	private void initializeStaion(JComboBox<String> box) {
		for (String sta: station.CHI_NAME) {
			box.addItem(sta);
		}
	}
}
