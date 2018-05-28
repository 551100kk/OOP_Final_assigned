package data;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;

public class constVar {
	public static final int FETCH_DAYS = 28;
	public static final String[] WEEKDAYS = {
		"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"
	};
	public static final int BUSS_CABIN = 5;	// 0 base
	public static final int TOTAL_CABIN = 9;
	public static final int TOTAL_SEATS = 737; 
	public static final int CABIN_SEATS[] = {63, 96, 88, 96, 83, 66, 61, 96 ,88};
	public static final String[][] VALID_SEATS;
	
	public static final String[] SIDE = {"C", "D"};
	public static final String[] WINDOW = {"A", "E"};
	
	static {
		// initialize valid seats
		VALID_SEATS = new String[TOTAL_CABIN][];
		ArrayList<String> tmp = new ArrayList<String>();
		// cabin 1, 3, 5, 9
		tmp.clear();
		tmp.addAll(Arrays.asList("01A", "01B", "01C"));
		for (int i = 2; i <= 13; i++) {
			for (int j = 0; j < 5; j++) {
				tmp.add(String.format("%02d%c", i, 'A' + j));
			}
		}
		VALID_SEATS[0] = tmp.toArray(new String[tmp.size()]);
		for (int i = 14; i <= 17; i++) {
			for (int j = 0; j < 5; j++) {
				tmp.add(String.format("%02d%c", i, 'A' + j));
			}
		}
		VALID_SEATS[4] = tmp.toArray(new String[tmp.size()]);
		for (int i = 18; i <= 18; i++) {
			for (int j = 0; j < 5; j++) {
				tmp.add(String.format("%02d%c", i, 'A' + j));
			}
		}
		VALID_SEATS[2] = tmp.toArray(new String[tmp.size()]);
		VALID_SEATS[8] = tmp.toArray(new String[tmp.size()]);
		// cabin 2, 4, 8
		tmp.clear();
		for (int i = 1; i <= 20; i++) {
			for (int j = 0; j < 3; j++) {
				tmp.add(String.format("%02d%c", i, 'A' + j));
			}
		}
		for (int i = 2; i <= 19; i++) {
			for (int j = 3; j < 5; j++) {
				tmp.add(String.format("%02d%c", i, 'A' + j));
			}
		}
		VALID_SEATS[1] = tmp.toArray(new String[tmp.size()]);
		VALID_SEATS[3] = tmp.toArray(new String[tmp.size()]);
		VALID_SEATS[7] = tmp.toArray(new String[tmp.size()]);
		// cabin 6
		tmp.clear();
		tmp.addAll(Arrays.asList("01D", "01E"));
		for (int i = 2; i <= 17; i++) {
			for (int j = 0; j < 5; j++) {
				if (j == 1) continue;
				tmp.add(String.format("%02d%c", i, 'A' + j));
			}
		}
		VALID_SEATS[5] = tmp.toArray(new String[tmp.size()]);
		// cabin 7
		tmp.clear();
		for (int i = 1; i <= 11; i++) {
			for (int j = 0; j < 5; j++) {
				tmp.add(String.format("%02d%c", i, 'A' + j));
			}
		}
		tmp.addAll(Arrays.asList("12D", "12E"));
		tmp.addAll(Arrays.asList("NO1", "NO2", "NO3", "NO4"));
		VALID_SEATS[6] = tmp.toArray(new String[tmp.size()]);		
	}
	
	public static int getWeekDay(int date) throws ParseException {
		SimpleDateFormat format1=new SimpleDateFormat("yyyyMMdd");
		Date dat = format1.parse(Integer.toString(date));
		Calendar c = Calendar.getInstance();
		c.setTime(dat);
		return (c.get(Calendar.DAY_OF_WEEK) + 5) % 7;
	}
	
	public static int getWeekDay(String date) throws ParseException {
		SimpleDateFormat format1=new SimpleDateFormat("yyyyMMdd");
		Date dat = format1.parse(date.replace("/", ""));
		Calendar c = Calendar.getInstance();
		c.setTime(dat);
		return (c.get(Calendar.DAY_OF_WEEK) + 5) % 7;
	}
	
	public static void main(String[] args) {
		for (int i = 0; i < 9; i++) {
			if (VALID_SEATS[i] != null) {
				System.out.println(VALID_SEATS[i].length);
				for (String st: VALID_SEATS[i]) System.out.print(st + " ");
				System.out.println("");
			}		
		}
	}	
}
