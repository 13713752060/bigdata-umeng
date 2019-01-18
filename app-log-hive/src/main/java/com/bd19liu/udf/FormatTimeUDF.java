package com.bd19liu.udf;

import org.apache.hadoop.hive.ql.exec.Description;
import org.apache.hadoop.hive.ql.exec.UDF;
import org.apache.hadoop.hive.ql.udf.UDFType;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @Description: 指定格式化模板进行时间格式化
 * @Author: liudugang
 * @Date: 2019/1/11  12:43
 */

@Description(name = "udf_formattime",
        value = "formattime",
        extended = "formattime('yyyy/MM/dd') ;\r\n" +
                " formattime(date, 'yyyy/MM/dd') \r\n" +
                " formattime('12345678', 'yyyy/MM/dd') \r\n" +
                " formattime(12345678, 'yyyy/MM/dd')")
@UDFType(deterministic = true, stateful = false)
public class FormatTimeUDF extends UDF {
    
    /**
     * 格式化当前时间
     */
    public String evaluate(String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(new Date());
    }
    
    /**
     * 格式化long类型 timestamp
     */
    public String evaluate(long timestamp, String format) {
        Date date = new Date(timestamp);
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(date);
    }
    
    /**
     * 格式化Date
     */
    public String evaluate(Date date, String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(date);
    }
    
    /**
     * 格式化字符串类型 timestamp
     */
    public String evaluate(String timestamp, String format) {
        Date date = new Date(Long.parseLong(timestamp));
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(date);
    }
}
