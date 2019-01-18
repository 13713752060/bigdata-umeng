package com.bd19liu.log.intercepter;

import com.alibaba.fastjson.JSONObject;
import org.apache.flume.Context;
import org.apache.flume.Event;
import org.apache.flume.interceptor.Interceptor;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * @Description: 自定义日志收集拦截器
 * @Author: liudugang
 * @Date: 2019/1/7  19:43
 */
public class LogCollectIntercepter implements Interceptor {
    private final boolean preserveExisting;
    
    private LogCollectIntercepter(boolean preserveExisting) {
        this.preserveExisting = preserveExisting;
    }
    @Override
    public void initialize() {
    }
    @Override
    public Event intercept(Event event) {
        Map<String, String> headers = event.getHeaders();
        // 时间处理
        byte[] body = event.getBody();
        String jsonStr = new String(body);
        AppBaseLog appBaseLog = JSONObject.parseObject(jsonStr, AppBaseLog.class);
        Long createdAtMs = appBaseLog.getCreatedAtMs();
        headers.put(LogCollectIntercepter.Constants.TIMESTAMP, Long.toString(createdAtMs));
    
        //处理log类型的头
        //pageLog
        String logType = "" ;
        if(jsonStr.contains("pageId")){
            logType = "page" ;
        }
        //eventLog
        else if (jsonStr.contains("eventId")) {
            logType = "event";
        }
        //usageLog
        else if (jsonStr.contains("singleUseDurationSecs")) {
            logType = "usage";
        }
        //errorLog
        else if (jsonStr.contains("errorBrief")) {
            logType = "error";
        }
        //startupLog
        else if (jsonStr.contains("network")) {
            logType = "startup";
        }
        headers.put("logType", logType);
        return event;
    }
    @Override
    public List<Event> intercept(List<Event> events) {
        Iterator i$ = events.iterator();
        
        while(i$.hasNext()) {
            Event event = (Event)i$.next();
            this.intercept(event);
        }
        
        return events;
    }
    @Override
    public void close() {
    }
    
    public static class Constants {
        public static String TIMESTAMP = "timestamp";
        public static String PRESERVE = "preserveExisting";
        public static boolean PRESERVE_DFLT = false;
        
        public Constants() {
        }
    }
    
    public static class Builder implements org.apache.flume.interceptor.Interceptor.Builder {
        private boolean preserveExisting;
        
        public Builder() {
            this.preserveExisting = LogCollectIntercepter.Constants.PRESERVE_DFLT;
        }
        
        @Override
        public Interceptor build() {
            return new LogCollectIntercepter(this.preserveExisting);
        }
        @Override
        public void configure(Context context) {
            this.preserveExisting = context.getBoolean(LogCollectIntercepter.Constants.PRESERVE, Boolean.valueOf(LogCollectIntercepter.Constants.PRESERVE_DFLT)).booleanValue();
        }
    }
}