package com.bd19liu.app.client;

import com.alibaba.fastjson.JSONObject;
import com.bd19liu.app.model.*;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 *  @Description: 模拟手机端生成随机日志程序
 *  @Author liudugang
 *  @Date 2019-01-05 20:44
 */
public class TestGenData {

	// 日志收集服务器 url
	private static String url = "http://master:8080/app-web/coll/index";

	// 生成随机数
	private static Random random = new Random();

	private static String appId = "sdk34734";
	private static String[] tenantIds = {"cake"};
	private static String[] deviceIds = initDeviceId();
	private static String[] appVersions = {"3.2.1", "3.2.2"};
	private static String[] appChannels = {"youmeng1", "youmeng2"};
	private static String[] appPlatforms = {"android", "ios"};

	private static Long[] createdAtMsS = initCreatedAtMs();
	//国家，终端不用上报，服务器自动填充该属性
	private static String[] countrys = {"America", "china"};
	//省份，终端不用上报，服务器自动填充该属性
	private static String[] provinces = {"Washington", "jiangxi", "beijing"};
	//网络
	private static String[] networks = {"WiFi", "CellNetwork"};
	//运营商
	private static String[] carriers = {"中国移动", "中国电信", "EE"};
	//机型
	private static String[] deviceStyles = {"iPhone 6", "iPhone 6 Plus", "红米手机1s"};
	//分辨率
	private static String[] screenSizes = {"1136*640", "960*640", "480*320"};
	//操作系统
	private static String[] osTypes = {"8.3", "7.1.1"};
	//品牌
	private static String[] brands = {"三星", "华为", "Apple", "魅族", "小米", "锤子"};
	//事件唯一标识
	private static String[] eventIds = {"popMenu", "autoImport", "BookStore"};
	//事件持续时长
	private static Long[] eventDurationSecsS = {new Long(25), new Long(67), new Long(45)};

	static Map<String, String> map1 = new HashMap<String, String>() {
		{
			put("testparam1key", "testparam1value");
			put("testparam2key", "testparam2value");
		}
	};
	static Map<String, String> map2 = new HashMap<String, String>() {
		{
			put("testparam3key", "testparam3value");
			put("testparam4key", "testparam4value");
		}
	};
	private static Map[] paramKeyValueMapsS = {map1, map2};

	//单次使用时长(秒数),指一次启动内应用在前台的持续时长
	private static Long[] singleUseDurationSecsS = initSingleUseDurationSecs();

	private static String[] errorBriefs = {"at cn.lift.dfdf.web.AbstractBaseController.validInbound(AbstractBaseController.java:72)", "at cn.lift.appIn.control.CommandUtil.getInfo(CommandUtil.java:67)"};        //错误摘要
	private static String[] errorDetails = {"java.lang.NullPointerException\\n    " + "at cn.lift.appIn.web.AbstractBaseController.validInbound(AbstractBaseController.java:72)\\n " + "at cn.lift.dfdf.web.AbstractBaseController.validInbound", "at cn.lift.dfdfdf.control.CommandUtil.getInfo(CommandUtil.java:67)\\n " + "at sun.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43)\\n" + " at java.lang.reflect.Method.invoke(Method.java:606)\\n"};        //错误详情
	//页面id
	private static String[] pageIds = {"list.html", "main.html", "test.html"};
	//访问顺序号，0为第一个页面
	private static int[] visitIndexs = {0, 1, 2, 3, 4};
	//下一个访问页面，如为空则表示为退出应用的页面
	private static String[] nextPages = {"list.html", "main.html", "test.html", null};
	//当前页面停留时长
	private static Long[] stayDurationSecsS = {new Long(45), new Long(2), new Long(78)};

	//启动相关信息的数组
	private static AppStartupLog[] appStartupLogs = initAppStartupLogs();
	//页面跳转相关信息的数组
	private static AppPageLog[] appPageLogs = initAppPageLogs();
	//事件相关信息的数组
	private static AppEventLog[] appEventLogs = initAppEventLogs();
	//app使用情况相关信息的数组
	private static AppUsageLog[] appUsageLogs = initAppUsageLogs();
	//错误相关信息的数组
	private static AppErrorLog[] appErrorLogs = initAppErrorLogs();

	private static String[] initDeviceId() {
		String base = "device22";
		String[] result = new String[100];
		for (int i = 0; i < 100; i++) {
			result[i] = base + i + "";
		}
		return result;
	}

	private static Long[] initCreatedAtMs() {
		Long createdAtMs = System.currentTimeMillis();
		Long[] result = new Long[11];
		for (int i = 0; i < 10; i++) {
			result[i] = createdAtMs - (long) (i * 24 * 3600 * 1000);
		}
		result[10] = createdAtMs;
		return result;
	}

	private static Long[] initSingleUseDurationSecs() {
		Random random = new Random();
		Long[] result = new Long[200];
		for (int i = 1; i < 200; i++) {
			result[i] = (long) random.nextInt(200);
		}
		return result;
	}

	//启动相关信息的数组
	private static AppStartupLog[] initAppStartupLogs() {
		AppStartupLog[] result = new AppStartupLog[10];
		for (int i = 0; i < 10; i++) {
			AppStartupLog appStartupLog = new AppStartupLog();
			appStartupLog.setCountry(countrys[random.nextInt(countrys.length)]);
			appStartupLog.setProvince(provinces[random.nextInt(provinces.length)]);
			appStartupLog.setNetwork(networks[random.nextInt(networks.length)]);
			appStartupLog.setCarrier(carriers[random.nextInt(carriers.length)]);
			appStartupLog.setDeviceStyle(deviceStyles[random.nextInt(deviceStyles.length)]);
			appStartupLog.setScreenSize(screenSizes[random.nextInt(screenSizes.length)]);
			appStartupLog.setOsType(osTypes[random.nextInt(osTypes.length)]);
			appStartupLog.setBrand(brands[random.nextInt(brands.length)]);
//			appStartupLog.setCreatedAtMs(createdAtMsS[random.nextInt(createdAtMsS.length)]);
			// 修改随机生成日志的时间为当前时间，而非随机时间。
			appStartupLog.setCreatedAtMs(System.currentTimeMillis());
			result[i] = appStartupLog;
		}
		return result;
	}

	//页面跳转相关信息的数组
	private static AppPageLog[] initAppPageLogs() {
		AppPageLog[] result = new AppPageLog[10];
		for (int i = 0; i < 10; i++) {
			AppPageLog appPageLog = new AppPageLog();
			String pageId = pageIds[random.nextInt(pageIds.length)];
			int visitIndex = visitIndexs[random.nextInt(visitIndexs.length)];
			String nextPage = nextPages[random.nextInt(nextPages.length)];
			while (pageId.equals(nextPage)) {
				nextPage = nextPages[random.nextInt(nextPages.length)];
			}
			Long stayDurationSecs = stayDurationSecsS[random.nextInt(stayDurationSecsS.length)];

			appPageLog.setPageId(pageId);
			appPageLog.setStayDurationSecs(stayDurationSecs);
			appPageLog.setVisitIndex(visitIndex);
			appPageLog.setNextPage(nextPage);
//			appPageLog.setCreatedAtMs(createdAtMsS[random.nextInt(createdAtMsS.length)]);
			// 修改随机生成日志的时间为当前时间，而非随机时间。
			appPageLog.setCreatedAtMs(System.currentTimeMillis());
			result[i] = appPageLog;
		}
		return result;
	}

	//事件相关信息的数组
	private static AppEventLog[] initAppEventLogs() {
		AppEventLog[] result = new AppEventLog[10];
		for (int i = 0; i < 10; i++) {
			AppEventLog appEventLog = new AppEventLog();
			appEventLog.setEventId(eventIds[random.nextInt(eventIds.length)]);
			appEventLog.setParamKeyValueMap(paramKeyValueMapsS[random.nextInt(paramKeyValueMapsS.length)]);
			appEventLog.setEventDurationSecs(eventDurationSecsS[random.nextInt(eventDurationSecsS.length)]);
//			appEventLog.setCreatedAtMs(createdAtMsS[random.nextInt(createdAtMsS.length)]);
			// 修改随机生成日志的时间为当前时间，而非随机时间。
			appEventLog.setCreatedAtMs(System.currentTimeMillis());
			result[i] = appEventLog;
		}
		return result;
	}

	//app使用情况相关信息的数组
	private static AppUsageLog[] initAppUsageLogs() {
		AppUsageLog[] result = new AppUsageLog[10];
		for (int i = 0; i < 10; i++) {
			AppUsageLog appUsageLog = new AppUsageLog();
			appUsageLog.setSingleUseDurationSecs(singleUseDurationSecsS[random.nextInt(singleUseDurationSecsS.length)]);
			//appUsageLog.setCreatedAtMs(createdAtMsS[random.nextInt(createdAtMsS.length)]);
			// 修改随机生成日志的时间为当前时间，而非随机时间。
			appUsageLog.setCreatedAtMs(System.currentTimeMillis());
			result[i] = appUsageLog;
		}
		return result;
	}

	;

	//错误相关信息的数组
	private static AppErrorLog[] initAppErrorLogs() {
		AppErrorLog[] result = new AppErrorLog[10];
		for (int i = 0; i < 10; i++) {
			AppErrorLog appErrorLog = new AppErrorLog();
			appErrorLog.setErrorBrief(errorBriefs[random.nextInt(errorBriefs.length)]);
			appErrorLog.setErrorDetail(errorDetails[random.nextInt(errorDetails.length)]);
			
			// appErrorLog.setCreatedAtMs(createdAtMsS[random.nextInt(createdAtMsS.length)]);
			// 修改随机生成日志的时间为当前时间，而非随机时间。
			appErrorLog.setCreatedAtMs(System.currentTimeMillis());
			appErrorLog.setOsType(osTypes[random.nextInt(osTypes.length)]);
			appErrorLog.setDeviceStyle(deviceStyles[random.nextInt(deviceStyles.length)]);
			result[i] = appErrorLog;
		}
		return result;
	}

	private static void httpPost(String urlString, String params) {
		URL url;

		try {
			url = new URL(urlString);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("POST");
			conn.setDoOutput(true);
			conn.setDoInput(true);
			conn.setUseCaches(false);
			conn.setInstanceFollowRedirects(true);
			conn.setRequestProperty("User-Agent",
					"Mozilla/5.0 (Windows NT 6.1; WOW64; rv:26.0) Gecko/20100101 Firefox/26.0");
			conn.setRequestProperty("Content-Type", "application/json");
			conn.setConnectTimeout(1000 * 5);
			conn.connect();
			conn.getOutputStream().write(params.getBytes("utf8"));
			conn.getOutputStream().flush();
			conn.getOutputStream().close();
			byte[] buffer = new byte[1024];
			StringBuffer sb = new StringBuffer();
			InputStream in = conn.getInputStream();
			int httpCode = conn.getResponseCode();
			System.out.println(in.available());
			while (in.read(buffer, 0, 1024) != -1) {
				sb.append(new String(buffer));
			}
			System.out.println("sb:" + sb.toString());
			in.close();
			System.out.println(httpCode);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		Test1();
	}

	private static void Test1() {
		Random random = new Random();
		try {
			//发送数据
			for (int i = 1; i <= 200000; i++) {
				AppLogEntity logEntity = new AppLogEntity();
				//渠道
				logEntity.setAppChannel(appChannels[random.nextInt(appChannels.length)]);
				//appid
				logEntity.setAppId(appId);
				//platform
				logEntity.setAppPlatform(appPlatforms[random.nextInt(appPlatforms.length)]);
				logEntity.setAppVersion(appVersions[random.nextInt(appVersions.length)]);
				String tenantId = tenantIds[random.nextInt(tenantIds.length)];
				if (tenantId != null) {
					logEntity.setTenantId(tenantId);
				}
				logEntity.setTenantId(tenantIds[random.nextInt(tenantIds.length)]);
				logEntity.setDeviceId(deviceIds[random.nextInt(deviceIds.length)]);

				//startup log集合
				logEntity.setAppStartupLogs(new AppStartupLog[]{appStartupLogs[random.nextInt(appStartupLogs.length)]});
				logEntity.setAppEventLogs(new AppEventLog[]{appEventLogs[random.nextInt(appEventLogs.length)]});
				logEntity.setAppErrorLogs(new AppErrorLog[]{appErrorLogs[random.nextInt(appErrorLogs.length)]});
				logEntity.setAppPageLogs(new AppPageLog[]{appPageLogs[random.nextInt(appPageLogs.length)]});
				logEntity.setAppUsageLogs(new AppUsageLog[]{appUsageLogs[random.nextInt(appUsageLogs.length)]});
				try {
					//将对象转换成json string
					String json = JSONObject.toJSONString(logEntity);
					System.out.println("=======start:"+json);
					
					UploadUtil.upload(json);
					
					System.out.println("=======end:"+json);
					// 防止数据生成太快
					Thread.sleep(10000);
				} catch (Exception ex) {
					System.out.println(ex);
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	private static void Test2() {
		boolean result = map1.isEmpty();
		System.out.println(result);
	}
}
