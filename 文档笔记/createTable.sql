-- #### DDL

-- 创建库
create database applogsdb;

-- 使用库
use applogsdb;

-------------------- 建表sql ----------------------------
-- ext_startup_logs 表
CREATE external TABLE ext_startup_logs (
	createdAtMs BIGINT,
	appId string,
	tenantId string,
	deviceId string,
	appVersion string,
	appChannel string,
	appPlatform string,
	osType string,
	deviceStyle string,
	country string,
	province string,
	ipAddress string,
	network string,
	carrier string,
	brand string,
	screenSize string
) PARTITIONED BY (
	ym string,
	day string,
	hm string
) ROW FORMAT SERDE 'org.openx.data.jsonserde.JsonSerDe' STORED AS TEXTFILE;

-- ext_error_logs 表
CREATE external TABLE ext_error_logs (
	createdAtMs BIGINT,
	appId string,
	tenantId string,
	deviceId string,
	appVersion string,
	appChannel string,
	appPlatform string,
	osType string,
	deviceStyle string,
	errorBrief string,
	errorDetail string
) PARTITIONED BY (
	ym string,
	day string,
	hm string
) ROW FORMAT SERDE 'org.openx.data.jsonserde.JsonSerDe' STORED AS TEXTFILE;

-- ext_event_logs 表
CREATE external TABLE ext_event_logs (
	createdAtMs BIGINT,
	appId string,
	tenantId string,
	deviceId string,
	appVersion string,
	appChannel string,
	appPlatform string,
	osType string,
	deviceStyle string,
	eventId string,
	eventDurationSecs BIGINT,
	paramKeyValueMap Map < string, string >
) PARTITIONED BY (
	ym string,
	day string,
	hm string
) ROW FORMAT SERDE 'org.openx.data.jsonserde.JsonSerDe' STORED AS TEXTFILE;

-- ext_page_logs 表
CREATE external TABLE ext_page_logs (
	createdAtMs BIGINT,
	appId string,
	tenantId string,
	deviceId string,
	appVersion string,
	appChannel string,
	appPlatform string,
	osType string,
	deviceStyle string,
	pageViewCntInSession INT,
	pageId string,
	visitIndex INT,
	nextPage string,
	stayDurationSecs BIGINT
) PARTITIONED BY (
	ym string,
	day string,
	hm string
) ROW FORMAT SERDE 'org.openx.data.jsonserde.JsonSerDe' STORED AS TEXTFILE;

-- ext_usage_logs  表
CREATE external TABLE ext_usage_logs (
	createdAtMs BIGINT,
	appId string,
	tenantId string,
	deviceId string,
	appVersion string,
	appChannel string,
	appPlatform string,
	osType string,
	deviceStyle string,
	singleUseDurationSecs BIGINT,
	singleUploadTraffic BIGINT,
	singleDownloadTraffic BIGINT
) PARTITIONED BY (
	ym string,
	day string,
	hm string
) ROW FORMAT SERDE 'org.openx.data.jsonserde.JsonSerDe' STORED AS TEXTFILE;

