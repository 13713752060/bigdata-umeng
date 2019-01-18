package com.bd19liu.test.udfdatedemo;

import com.bd19liu.udf.NumberUtil;
import org.apache.hadoop.hive.ql.exec.Description;
import org.apache.hadoop.hive.ql.exec.UDF;
import org.apache.hadoop.hive.ql.udf.UDFType;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 计算周结束毫秒数
 */
@Description(name = "udf_getEndWeek",
		value = "getEndTimeInWeek",
		extended = "udf() ; udf('2019/01/10 00:09:04'); udf('2019/01/10 00:09:04', 'yyyy-MM-dd HH-mm-ss')")
@UDFType(deterministic = true, stateful = false)
public class WeekEndUDF extends UDF {

	/**
	 * 计算本周结束时刻(毫秒数),其实是下周第一天的零时.
	 */
	public long evaluate() throws ParseException {
		return evaluate(new Date());
	}

	/**
	 * 计算指定日期的周结束时刻(毫秒数)
	 */
	public long evaluate(Date d) throws ParseException {
		Date zeroDate = DateUtil.getZeroDate(d);
		Calendar c = Calendar.getInstance();
		c.setTime(zeroDate);
		int n = c.get(Calendar.DAY_OF_WEEK);
		c.add(Calendar.DAY_OF_MONTH,(NumberUtil.EIGHT - n));
		return c.getTimeInMillis();
	}

	/**
	 * 计算指定日期,使用的格式是yyyy/MM/dd HH:mm:ss的周结束时刻(毫秒数)
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
	 * 计算指定日期周结束时刻,使用的格式自行指定(毫秒数)
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