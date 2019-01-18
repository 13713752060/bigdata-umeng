package com.bd19liu.udf;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 *
 */
public class DateUtil {
	
	public static final String FORMAT_PATTERN_DATETIME = "yyyy/MM/dd HH:mm:ss";
	
	public static final String FORMAT_FOR_DAY = "yyyy/MM/dd 00:00:00";
	
	public static final String FORMAT_FOR_MONTH = "yyyy/MM/01 00:00:00";
	
	/**
	 * 得到指定date的零时刻.
	 */
	public static Date getDayBeginTime(Date d) {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(FORMAT_FOR_DAY);
			return sdf.parse(sdf.format(d));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 得到指定date的偏移量零时刻.
	 */
	public static Date getDayBeginTime(Date d, int offset) {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(FORMAT_FOR_DAY);
			Date beginDate = sdf.parse(sdf.format(d));
			Calendar c = Calendar.getInstance();
			c.setTime(beginDate);
			c.add(Calendar.DAY_OF_MONTH,offset);
			return c.getTime() ;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 得到指定date所在周的起始时刻.
	 */
	public static Date getWeekBeginTime(Date d) {
		try {
			//得到d的零时刻
			Date beginDate= getDayBeginTime(d);
			Calendar c = Calendar.getInstance();
			c.setTime(beginDate);
			int n = c.get(Calendar.DAY_OF_WEEK);
			c.add(Calendar.DAY_OF_MONTH,-(n - NumberUtil.ONE));
			return c.getTime();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 得到指定date所在周的起始时刻.
	 */
	public static Date getWeekBeginTime(Date d,int offset) {
		try {
			//得到d的零时刻
			Date beginDate= getDayBeginTime(d);
			Calendar c = Calendar.getInstance();
			c.setTime(beginDate);
			int n = c.get(Calendar.DAY_OF_WEEK);
			//定位到本周第一天
			c.add(Calendar.DAY_OF_MONTH,-(n - NumberUtil.ONE));
			c.add(Calendar.DAY_OF_MONTH,offset * NumberUtil.SEVEN);
			return c.getTime();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 得到指定date所在月的起始时刻.
	 */
	public static Date getMonthBeginTime(Date d) {
		try {
			//得到d的零时刻
			Date beginDate= getDayBeginTime(d);
			SimpleDateFormat sdf = new SimpleDateFormat(FORMAT_FOR_MONTH);
			return sdf.parse(sdf.format(beginDate));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 得到指定date所在月的起始时刻.
	 */
	public static Date getMonthBeginTime(Date d,int offset) {
		try {
			//得到d的零时刻
			Date beginDate= getDayBeginTime(d);
			SimpleDateFormat sdf = new SimpleDateFormat(FORMAT_FOR_MONTH);

			//d所在月的第一天的零时刻
			Date firstDay = sdf.parse(sdf.format(beginDate));

			Calendar c = Calendar.getInstance();
			c.setTime(firstDay);
			//对月进行滚动
			c.add(Calendar.MONTH, offset);

			return c.getTime();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
