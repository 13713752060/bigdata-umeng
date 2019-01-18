package com.bd19liu.applogs.collect.web.controller;

import com.alibaba.fastjson.JSONObject;
import com.bd19liu.app.constant.TopicConstant;
import com.bd19liu.app.model.*;
import com.bd19liu.app.util.GeoLiteUtil;
import com.bd19liu.app.util.PropertiesUtil;
import kafka.javaapi.producer.Producer;
import kafka.producer.KeyedMessage;
import kafka.producer.ProducerConfig;
import kafka.tools.ConsoleProducer;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 *
 */
@Controller
@RequestMapping("/coll")
public class CollectLogController {
	
	private Map<String, IpAddress> cacheIpMap = new HashMap<String, IpAddress>();
	
	@RequestMapping(value = "/index", method = RequestMethod.POST)
	@ResponseBody
	public AppLogEntity collect(@RequestBody AppLogEntity e, HttpServletRequest req) {
		
		//server时间
		long myTime = System.currentTimeMillis() ;
		//客户端时间
		long clientTime = Long.parseLong(req.getHeader("clientTime"));
		//时间校对
		long diff = myTime - clientTime;

		// 时间修正
		verifyTime(e,diff);
		
		// 基本属性拷贝
		copyBaseProperties(e);
		
		// 处理ip地址问题
		String clientIp = req.getRemoteAddr();
		processIp(e, clientIp);
		
		//发送给kafka
		sendMessage(e);
		
		return e;
	}
	
	
	/**
	 * 消息发送到kafka
	 */
	public void sendMessage(AppLogEntity e) {
		//创建配置对象
		Properties props = new Properties();
		props.put("metadata.broker.list", "master:9092, slave02:9092, slave03:9092");
		props.put("serializer.class", "kafka.serializer.StringEncoder");
		props.put("request.required.acks", "1");
		
		//创建生产者
		Producer<Integer, String> producer = new Producer<Integer, String>(new ProducerConfig(props));
		sendSingleLog(producer, TopicConstant.TOPIC_APP_LOG_STARTUP, e.getAppStartupLogs());
		sendSingleLog(producer, TopicConstant.TOPIC_APP_LOG_ERROR, e.getAppErrorLogs());
		sendSingleLog(producer, TopicConstant.TOPIC_APP_LOG_EVENT, e.getAppEventLogs());
		sendSingleLog(producer, TopicConstant.TOPIC_APP_LOG_PAGE, e.getAppPageLogs());
		sendSingleLog(producer, TopicConstant.TOPIC_APP_LOG_USAGE,e.getAppUsageLogs());
		//关闭连接
		producer.close();
	}
	
	/**
	 * 发送单个的log消息给kafka
	 */
	private void sendSingleLog(Producer<Integer, String> producer,String topic , AppBaseLog[] logs){
		for (AppBaseLog log : logs) {
			String logMsg = JSONObject.toJSONString(log);
			//创建消息
			KeyedMessage<Integer, String> data = new KeyedMessage<Integer, String>(topic, logMsg);
			producer.send(data);
		}
	}
	
	/**
	 *  @Description: 处理客户端ip问题
	 *  @parameter:
	 *  @Author liudugang
	 *  @Date 2019-01-07 12:07
	 *  @return
	 */
	private void processIp(AppLogEntity e, String clientIp) {
		IpAddress ipAddress = cacheIpMap.get(clientIp);
		if(null == ipAddress){
			ipAddress = new IpAddress();
			ipAddress.setCountry(GeoLiteUtil.getCountry(clientIp));
			ipAddress.setProvince(GeoLiteUtil.getProvence(clientIp));
			cacheIpMap.put(clientIp, ipAddress);
		}
		
		for (AppStartupLog appStartupLog : e.getAppStartupLogs()) {
			appStartupLog.setCountry(ipAddress.getCountry());
			appStartupLog.setProvince(ipAddress.getProvince());
			appStartupLog.setIpAddress(clientIp);
		}
	}
	
	/**
	 *  @Description:  时间修正
	 *  				客户端的时间，加上时间差。
	 *  @parameter:
	 *  @Author liudugang
	 *  @Date 2019-01-07 10:42
	 *  @return
	 */
	public void verifyTime(AppLogEntity e, long diff){
		for(AppBaseLog log : e.getAppStartupLogs()){
			log.setCreatedAtMs(log.getCreatedAtMs() + diff);
		}
		for(AppBaseLog log : e.getAppErrorLogs()){
			log.setCreatedAtMs(log.getCreatedAtMs() + diff);
		}
		for(AppBaseLog log : e.getAppEventLogs()){
			log.setCreatedAtMs(log.getCreatedAtMs() + diff);
		}
		for(AppBaseLog log : e.getAppPageLogs()){
			log.setCreatedAtMs(log.getCreatedAtMs() + diff);
		}
		for(AppBaseLog log : e.getAppUsageLogs()){
			log.setCreatedAtMs(log.getCreatedAtMs() + diff);
		}
	}
	
	/**
	 *  @Description: 基本属性拷贝
	 *  @parameter:
	 *  @Author liudugang
	 *  @Date 2019-01-05 22:00
	 *  @return
	 */
	public void copyBaseProperties(AppLogEntity e){
		PropertiesUtil.copyProperties(e, e.getAppStartupLogs());
		PropertiesUtil.copyProperties(e, e.getAppErrorLogs());
		PropertiesUtil.copyProperties(e, e.getAppEventLogs());
		PropertiesUtil.copyProperties(e, e.getAppPageLogs());
		PropertiesUtil.copyProperties(e, e.getAppUsageLogs());
	}
	
}