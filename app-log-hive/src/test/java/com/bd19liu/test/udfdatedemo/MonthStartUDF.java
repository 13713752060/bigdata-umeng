package com.bd19liu.test.udfdatedemo;

import org.apache.hadoop.hive.ql.exec.Description;
import org.apache.hadoop.hive.ql.exec.UDF;
import org.apache.hadoop.hive.ql.udf.UDFType;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 计算Month 开始时刻毫秒数，其实是本月1日的零时.
 */
@Description(name = "udf_getStartMonth",
		value = "getStartTimeInMonth",
		extended = "udf() ; udf('2019/01/01 00:09:04') ; udf('2019/01/01 00:09:04','yyyy-MM-dd HH-mm-ss')")
@UDFType(deterministic = true, stateful = false)
public class MonthStartUDF extends UDF {

	/**
	 * 计算本月开始时刻(毫秒数),其实是本月1日的零时.
	 */
	public long evaluate() throws ParseException {
		return evaluate(new Date());
	}

	/**
	 * 计算指定日期的月开始时刻(毫秒数)
	 */
	public long evaluate(Date d) throws ParseException {
		Date zeroDate = DateUtil.getZeroDate(d);
		SimpleDateFormat sdf = new SimpleDateFormat(DateUtil.FORMAT_FOR_MONTH) ;
		return sdf.parse(sdf.format(zeroDate)).getTime();
	}

	/**
	 * 计算指定日期,使用的格式是yyyy/MM/dd HH:mm:ss的月开始时刻(毫秒数)
	 */
	public long evaluate(String dateStr) {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(DateUtil.FORMAT_PATTERN_DATETIME);
			Date d = sdf.parse(dateStr);
			return evaluate(d);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}

	/**
	 * 计算指定日期月开始时刻,使用的格式自行指定(毫秒数)
	 */
	public long evaluate(String dateStr, String fmt) {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(fmt);
			Date d = sdf.parse(dateStr);
			return evaluate(d);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}
}