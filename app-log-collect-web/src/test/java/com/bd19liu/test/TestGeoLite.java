package com.bd19liu.test;

import com.bd19liu.app.util.GeoLiteUtil;
import com.fasterxml.jackson.databind.JsonNode;
import com.maxmind.db.Reader;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;

/**
 * 通过域名或者ip 获取具体的地址（国家，省份，城市，经纬度，所属大州 ...}）
 */
public class TestGeoLite {
	@Test
	public void test1() throws IOException {
		InputStream in = ClassLoader.getSystemResourceAsStream("GeoLite2-City.mmdb");
		Reader r = new Reader(in);
//		JsonNode node = r.get(InetAddress.getByName("tianya.cn"));
//		JsonNode node = r.get(InetAddress.getByName("www.baidu.com"));
		JsonNode node = r.get(InetAddress.getByName("163.177.151.109"));
		
		System.out.println("==========="+node);
		
		//国家
		String country = node.get("country").get("names").get("zh-CN").textValue();
		System.out.println(country);
		//省份
		String area = node.get("subdivisions").get(0).get("names").get("zh-CN").textValue();
		//城市
		String city = node.get("city").get("names").get("zh-CN").textValue();

		System.out.println(country + "." + area + "." + city);
	}

	@Test
	public void test2() throws IOException {
		String ip = "127.0.0.1" ;
//		System.out.println(GeoLiteUtil.getCountry(ip));
//		System.out.println(GeoLiteUtil.getCity(ip));
//		System.out.println(GeoLiteUtil.getProvence(ip));
//		System.out.println(GeoLiteUtil.getContinent(ip));
		System.out.println(GeoLiteUtil.getLatitude(ip));
		System.out.println(GeoLiteUtil.getLongitude(ip));
		System.out.println(GeoLiteUtil.getTimeZone(ip));
	}
}
