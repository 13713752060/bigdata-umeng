package com.bd19liu.app.util;

import java.io.IOException;
import java.io.InputStream;

import com.fasterxml.jackson.databind.JsonNode;
import com.maxmind.db.Reader;
import java.net.InetAddress;

/**
 * @Description: ip & 域名 增强工具类
 * @Author: liudugang
 * @Date: 2019/1/7  11:31
 */
public class GeoLiteUtil {
    
    private static InputStream in;
    private static Reader r;
    
    static {
        try {
            // 注意: 因为web 程序无法通过 系统的类加载器获取流，而是使用tomcat 的类加载加载的。所以需要用该方式获取流。
            ClassLoader loader = Thread.currentThread().getContextClassLoader();
            in = loader.getResource("GeoLite2-City.mmdb").openStream();
//            in = ClassLoader.getSystemResourceAsStream("GeoLite2-City.mmdb");  // 该方式不适合web 程序。
            r = new Reader(in);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    /**
     *  @Description: 根据ip 获取所属国家
     *  @parameter:
     *  @Author liudugang
     *  @Date 2019-01-07 11:41
     *  @return 
     */
    public static String getCountry(String ip){
        JsonNode node;
        String country = "";
        try {
            node = r.get(InetAddress.getByName(ip));
            country = node.get("country").get("names").get("zh-CN").textValue();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            return country;
        }
    }
    /**
     *  @Description: 根据ip 获取所属省份
     *  @parameter:
     *  @Author liudugang
     *  @Date 2019-01-07 11:41
     *  @return
     */
    public static String getProvence(String ip){
        JsonNode node;
        String provence = "";
        try {
            node = r.get(InetAddress.getByName(ip));
            provence = node.get("subdivisions").get(0).get("names").get("zh-CN").textValue();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            return provence;
        }
    }
    /**
     *  @Description: 根据ip 获取所属城市
     *  @parameter:
     *  @Author liudugang
     *  @Date 2019-01-07 11:41
     *  @return
     */
    public static String getCity(String ip){
        JsonNode node;
        String city = "";
        try {
            node = r.get(InetAddress.getByName(ip));
            city = node.get("city").get("names").get("zh-CN").textValue();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            return city;
        }
    }
    
    
    /**
     *  @Description: 根据ip 获取所属大洲
     *  @parameter:
     *  @Author liudugang
     *  @Date 2019-01-07 11:44
     *  @return
     */
    public static String getContinent(String ip){
        JsonNode node;
        String continent = "";
        try {
            node = r.get(InetAddress.getByName(ip));
            continent = node.get("continent").get("names").get("zh-CN").textValue();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            return continent;
        }
    }
    
    /**
     *  @Description: 根据ip 获取经度
     *  @parameter:
     *  @Author liudugang
     *  @Date 2019-01-07 11:44
     *  @return
     */
    public static String getLongitude(String ip){
        JsonNode node;
        String longitude = "";
        try {
            node = r.get(InetAddress.getByName(ip));
//            longitude = node.get("location").get("longitude").textValue();
            longitude = node.get("location").get("longitude").asText();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            return longitude;
        }
    }
    /**
     *  @Description: 根据ip 获取维度
     *  @parameter:
     *  @Author liudugang
     *  @Date 2019-01-07 11:44
     *  @return
     */
    public static String getLatitude(String ip){
        JsonNode node;
        String latitude = "";
        try {
            node = r.get(InetAddress.getByName(ip));
            latitude = node.get("location").get("latitude").asText();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            return latitude;
        }
    }
    
    /**
     *  @Description: 根据ip 获取所属时区
     *  @parameter:
     *  @Author liudugang
     *  @Date 2019-01-07 11:44
     *  @return
     */
    public static String getTimeZone(String ip){
        JsonNode node;
        String time_zone = "";
        try {
            node = r.get(InetAddress.getByName(ip));
            time_zone = node.get("location").get("time_zone").textValue();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            return time_zone;
        }
    }
    
    
}
