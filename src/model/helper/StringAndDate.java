package model.helper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;

public class StringAndDate {
	public static Date StringToDate(String date) {
		try {
			return new SimpleDateFormat("yyyy-MM-dd").parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static String DateToString(Date date) {
		try {
			return new SimpleDateFormat("yyyy-MM-dd").format(date);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}
	
	public static String changeeDate(Date date, int amount) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.DATE, amount);
		return new SimpleDateFormat("yyyy-MM-dd").format(cal.getTime());
	}
	
	public static String getNow() {
			return new SimpleDateFormat("yyyy-MM-dd").format(new Date());
	}

	
	public static Date LocalDateToDate(LocalDate ld) {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, ld.getYear());
		cal.set(Calendar.MONTH, ld.getMonthValue()-1);
		cal.set(Calendar.DAY_OF_MONTH, ld.getDayOfMonth());
		return cal.getTime();
	}
	
	public static LocalDate DateToLocalDate(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		int year = cal.get(Calendar.YEAR);
		int month = cal.get(Calendar.MONTH)+1;
		int day = cal.get(Calendar.DAY_OF_MONTH);
		if(month > 12) {
			month = 1;
			year+=1;
		}
		return LocalDate.of(year, month, day);
	}
	
	public static void main(String [] args) {
		//Vi du su dung, neu may dung date dau tien chuyen no sang localdate
		Date date = StringToDate("2018-11-16");
		boolean status = date.before(new Date());
		System.out.println(status);
		
	}
}
