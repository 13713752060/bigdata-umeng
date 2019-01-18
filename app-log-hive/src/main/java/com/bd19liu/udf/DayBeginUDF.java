package com.bd19liu.udf;

import org.apache.hadoop.hive.ql.exec.Description;
import org.apache.hadoop.hive.ql.exec.UDF;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 计算day起始毫秒数
 */
@Description(name = "udf_getdaybegin",
		value = "getdaybegin",
		  extended = "getdaybegin() ;\r\n"
				   + " getdaybegin(2) \r\n"
				   + " getdaybegin('2017/06/29 01:02:03') \r\n"
				   + " getdaybegin('2017/06/29 01:02:03',2) \r\n"
				   + " getdaybegin(date_obj) \r\n"
				   + " getdaybegin(date_obj,2)")
public class DayBeginUDF extends UDF {

	/**
	 * 计算现在的起始时刻(毫秒数)
	 */
	public long evaluate() {
		return evaluate(new Date());
	}

	/**
	 * 指定天偏移量
	 */
	public long evaluate(int offset) {
		return evaluate(DateUtil.getDayBeginTime(new Date(), offset));
	}

	/**
	 * 计算某天的结束时刻(毫秒数)
	 */
	public long evaluate(Date d) {
		return DateUtil.getDayBeginTime(d).getTime();
	}

	/**
	 * 计算某天的结束时刻(毫秒数)
	 */
	public long evaluate(Date d, int offset) {
		return DateUtil.getDayBeginTime(d, offset).getTime();
	}

	/**
	 * 计算某天的起始时刻(毫秒数)
	 */
	public long evaluate(String dateStr) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat(DateUtil.FORMAT_PATTERN_DATETIME);
		Date d = sdf.parse(dateStr);
		return evaluate(d);
	}

	/**
	 * 计算某天的起始时刻(毫秒数)
	 */
	public long evaluate(String dateStr, int offset) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat(DateUtil.FORMAT_PATTERN_DATETIME);
		Date d = sdf.parse(dateStr);
		return DateUtil.getDayBeginTime(d, offset).getTime();
	}

	/**
	 * 计算某天的起始时刻(毫秒数)
	 */
	public long evaluate(String dateStr, String fmt) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat(fmt);
		Date d = sdf.parse(dateStr);
		return DateUtil.getDayBeginTime(d).getTime();
	}

	/**
	 * 计算某天的起始时刻(毫秒数)
	 */
	public long evaluate(String dateStr, String fmt, int offset) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat(fmt);
		Date d = sdf.parse(dateStr);
		return DateUtil.getDayBeginTime(d, offset).getTime();
	}
}