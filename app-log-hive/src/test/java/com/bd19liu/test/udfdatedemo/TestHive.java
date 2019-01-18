package com.bd19liu.test.udfdatedemo;

//import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 *
 */
public class TestHive {

	/**
	 *计算某天的起始时刻(毫秒数)
	 */
//	@Test
	public void testStartTime() throws ParseException {
		Date d = new Date();
		long ms = getZeroDate(d).getTime();
		System.out.println(ms);
	}

	/**
	 *计算某天的结束时刻(毫秒数)
	 */
//	@Test
	public void testEndTime() throws ParseException {

		Date d = new Date();
		Date zeroDate = getZeroDate(d) ;
		//日历
		Calendar c = Calendar.getInstance();
		c.setTime(zeroDate);

		c.add(Calendar.DAY_OF_MONTH,1);
		Date endDate = c.getTime();
		System.out.println(endDate.getTime()) ;
	}

	/**
	 * 测试周起始时间
	 */
//	@Test
	public void testWeekStartTime(){
		Date d = new Date();
		Date zeorDate = getZeroDate(d);

		Calendar c = Calendar.getInstance();
		c.setTime(zeorDate);

		int n = c.get(Calendar.DAY_OF_WEEK);
		c.add(Calendar.DAY_OF_MONTH,-(n - 1));
		//
		long ms = c.getTimeInMillis();
		System.out.println(ms);
	}



	/**
	 * 测试周结束时间
	 */
//	@Test
	public void testWeekEndTime(){
		Date d = new Date();
		Date zeorDate = getZeroDate(d);
		Calendar c = Calendar.getInstance();
		c.setTime(zeorDate);
		int n = c.get(Calendar.DAY_OF_WEEK);
		c.add(Calendar.DAY_OF_MONTH,(8 - n));
		Date weekFirstDate = c.getTime();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss") ;
		String format = sdf.format(weekFirstDate);
		System.out.println(format);
	}

	/**
	 * 得到指定date所在的月的第一天
	 */
//	@Test
	public void testMonthStartDate() throws ParseException {
		Date d = new Date();
		Date zeroDate = getZeroDate(d);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/01:00:00:00") ;
		long ms = sdf.parse(sdf.format(zeroDate)).getTime();
		System.out.println(ms);
	}

//	@Test
	public void testMonthEndDate() throws ParseException {
		Date d = new Date();
		Date zeroDate = getZeroDate(d);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/01:00:00:00") ;
		String format = sdf.format(zeroDate);
		Date firstDay = sdf.parse(format);
		Calendar c = Calendar.getInstance();
		c.setTime(firstDay);
		c.add(Calendar.MONTH,1);
		long ms = c.getTimeInMillis();
		System.out.println(ms);
	}

	/**
	 * 得到指定date的零时刻.
	 */
	private Date getZeroDate(Date d){
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd 00:00:00");
			return sdf.parse(sdf.format(d));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null ;
	}

}
