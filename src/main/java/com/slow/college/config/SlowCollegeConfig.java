package com.slow.college.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class SlowCollegeConfig {
	
	public static String springDatasourceJdbcUrl;
	
	@Value("${spring.datasource.jdbc-url}")
	public void setSpringDatasourceJdbcUrl(String springDatasourceJdbcUrl) {
		SlowCollegeConfig.springDatasourceJdbcUrl = springDatasourceJdbcUrl;
	}
	
	public static String springDatasourceUsername;
	
	@Value("${spring.datasource.username}")
	public void setSpringDatasourceUsername(String springDatasourceUsername) {
		SlowCollegeConfig.springDatasourceUsername = springDatasourceUsername;
	}
	
	public static String springDatasourcePassword;
	
	@Value("${spring.datasource.password}")
	public void setSpringDatasourcePassword(String springDatasourcePassword) {
		SlowCollegeConfig.springDatasourcePassword = springDatasourcePassword;
	}
	
	public static String qiniuRequestUrlHead;

	@Value("${qiniu.request.url.head}")
	public void setQiniuRequestUrlHead(String qiniuRequestUrlHead) {
		SlowCollegeConfig.qiniuRequestUrlHead = qiniuRequestUrlHead;
	}
	
	public static String qiniuRequestUrlParam;

	@Value("${qiniu.request.url.param}")
	public void setQiniuRequestUrlParam(String qiniuRequestUrlParam) {
		SlowCollegeConfig.qiniuRequestUrlParam = qiniuRequestUrlParam;
	}
	
	public static String qiniuAccessKey;

	@Value("${qiniu.access.key}")
	public void setQiniuAccessKey(String qiniuAccessKey) {
		SlowCollegeConfig.qiniuAccessKey = qiniuAccessKey;
	}
	
	public static String qiniuSecretKey;

	@Value("${qiniu.secret.key}")
	public void setQiniuSecretKey(String qiniuSecretKey) {
		SlowCollegeConfig.qiniuSecretKey = qiniuSecretKey;
	}
	
	public static String qiniuBucket;

	@Value("${qiniu.bucket}")
	public void setQiniuBucket(String qiniuBucket) {
		SlowCollegeConfig.qiniuBucket = qiniuBucket;
	}
	
	public static String qiniuMyUrl;

	@Value("${qiniu.my.url}")
	public void setQiniuMyUrl(String qiniuMyUrl) {
		SlowCollegeConfig.qiniuMyUrl = qiniuMyUrl;
	}
	
}
