package com.bd19liu.test.udfdatedemo;

import org.apache.hadoop.hive.ql.exec.Description;
import org.apache.hadoop.hive.ql.exec.UDF;
import org.apache.hadoop.hive.ql.udf.UDFType;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 计算day起始毫秒数
 */
@Description(name = "udf_getStartay",
		value = "getStartInDay",
		extended = "udf() ; udf('2019/01/10 00:09:04') ; udf('2019/01/10 00:09:04','yyyy-MM-dd HH-mm-ss')")
@UDFType(deterministic = true, stateful = false)
public class DayStartUDF extends UDF {

	/**
	 * 计算现在的起始时刻(毫秒数)
	 */
	public long evaluate() throws ParseException {
		return evaluate(new Date());
	}

	/**
	 * 计算某天的结束时刻(毫秒数)
	 */
	public long evaluate(Date d) throws ParseException {
		return DateUtil.getZeroDate(d).getTime();
	}

	/**
	 * 计算某天的起始时刻(毫秒数)
	 */
	public long evaluate(String dateStr)  {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(DateUtil.FORMAT_PATTERN_DATETIME);
			Date d = sdf.parse(dateStr);
			return evaluate(d);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0 ;
	}

	/**
	 * 计算某天的起始时刻(毫秒数)
	 */
	public long evaluate(String dateStr,String fmt)  {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(fmt);
			Date d = sdf.parse(dateStr);
			return evaluate(d);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0 ;
	}
}