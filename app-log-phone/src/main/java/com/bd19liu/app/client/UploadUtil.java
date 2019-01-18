package com.bd19liu.app.client;

import java.io.InputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
/**
 * @Description: 模拟手机上报日志程序
 * @Author: liudugang
 * @Date: 2019/1/5  19:49
 */
public class UploadUtil {
    
    public static void upload(String josn) {
        
        try {
            URL url = new URL("http://172.16.176.101:8080/app-web/coll/index");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            //设置请求方式为post
            conn.setRequestMethod("POST");
            
            //时间头用来供server端进行时钟校对的。
            conn.setRequestProperty("clientTime", System.currentTimeMillis()+"");
            
            //允许上传数据
            conn.setDoOutput(true);
            //设置请求的头信息,设置内容类型
            conn.setRequestProperty("Content-Type","application/json");
            
            //输出流
            OutputStream out = conn.getOutputStream();
            out.write(josn.getBytes());
            
            // 只有当获取响应码，才会发送请求。
            int code = conn.getResponseCode();
    
            out.flush();
            out.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
