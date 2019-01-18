package com.bd19liu.test.udfdatedemo;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 得到指定date的零时刻.
 */
public class DateUtil {
	
	public static final String FORMAT_PATTERN_DATETIME = "yyyy/MM/dd HH:mm:ss";
	
	public static final String FORMAT_FOR_DAY = "yyyy/MM/dd 00:00:00";
	
	public static final String FORMAT_FOR_MONTH = "yyyy/MM/01 00:00:00";
	
	public static Date getZeroDate(Date d) {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(DateUtil.FORMAT_FOR_DAY);
			return sdf.parse(sdf.format(d));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
