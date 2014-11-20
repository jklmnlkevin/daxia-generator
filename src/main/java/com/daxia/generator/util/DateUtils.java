package com.daxia.generator.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtils {
	
	/**
	 * 返回一天的最开始的时候,00:00:00
	 * @param date
	 * @return
	 */
	public static Date getBeginningOfADay(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		return cal.getTime();
	}
	
	/**
	 * 返回一天的最后的时候, 23:59:59
	 * @param date
	 * @return
	 */
	public static Date getEndOfADay(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.HOUR_OF_DAY, 23);
		cal.set(Calendar.MINUTE, 59);
		cal.set(Calendar.SECOND, 59);
		return cal.getTime();
	}
	
	public static void main(String[] args) {
	    Date now = new Date();
	    Date date = getBeginningOfADay(now);
	    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	    System.out.println(dateFormat.format(date));
	    
	    date = getEndOfADay(now);
	    System.out.println(dateFormat.format(date));
    }
}
