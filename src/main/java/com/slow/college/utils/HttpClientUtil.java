package com.slow.college.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.Map;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.cookie.CookiePolicy;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Description: BODY DATA 模式请求
 * @author songming
 */
public class HttpClientUtil {

	private static  final Logger log = LoggerFactory.getLogger(HttpClientUtil.class);

	/**
	 * Send Post With Body Data
	 * 
	 * @param param
	 *            参数对象
	 */
	public static String postRequest(String url, Object param) {
		if (StringUtils.isBlank(url) || param == null) {
			log.error("PostRequest with obj invalid call");
			return null;
		}
		HttpClient hc = new HttpClient();
		PostMethod pm = new PostMethod(url);
		Class<?> c = param.getClass();
		for (Field f : c.getDeclaredFields()) {
			f.setAccessible(true);
			try {
				Object val = f.get(param);
				if (val instanceof BigDecimal) {
					pm.setParameter(f.getName(), ((BigDecimal) val).setScale(2).toString());
				} else {
					pm.setParameter(f.getName(), val.toString());
				}
			} catch (IllegalArgumentException | IllegalAccessException e) {
				e.printStackTrace();
			}
		}
		StringBuffer sbParam = new StringBuffer();
		for (NameValuePair pair : pm.getParameters()) {
			sbParam.append("|").append(pair.toString());
		}
		log.info("PostRequest params: " + sbParam.toString());
		hc.getHttpConnectionManager().getParams().setConnectionTimeout(30000);
		hc.getHttpConnectionManager().getParams().setSoTimeout(30000);
		hc.getParams().setCookiePolicy(CookiePolicy.IGNORE_COOKIES);
		StringBuffer sb = new StringBuffer();
		try {
			hc.executeMethod(pm);
			InputStream is = pm.getResponseBodyAsStream();
			BufferedReader br = new BufferedReader(new InputStreamReader(is, "utf-8"));
			String tmp;
			while ((tmp = br.readLine()) != null) {
				sb.append(tmp);
			}
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return sb.toString();
	}

	/**
	 * Send Post With Body Data
	 */
	public static String postRequest(String url, Map<String, String> map) {
		if (StringUtils.isBlank(url) || map == null) {
			log.error("PostRequest with map invalid call");
			return null;
		}
		HttpClient hc = new HttpClient();
		PostMethod pm = new PostMethod(url);
		for (String key : map.keySet()) {
			pm.setParameter(key, map.get(key));
		}
		StringBuffer sbParam = new StringBuffer();
		for (NameValuePair pair : pm.getParameters()) {
			sbParam.append("|").append(pair.toString());
		}
		log.info("PostRequest with map params: " + sbParam.toString());
		hc.getHttpConnectionManager().getParams().setConnectionTimeout(30000);
		hc.getHttpConnectionManager().getParams().setSoTimeout(30000);
		hc.getParams().setCookiePolicy(CookiePolicy.IGNORE_COOKIES);
		StringBuffer sb = new StringBuffer();
		try {
			hc.executeMethod(pm);
			InputStream is = pm.getResponseBodyAsStream();
			BufferedReader br = new BufferedReader(new InputStreamReader(is, "utf-8"));
			String tmp;
			while ((tmp = br.readLine()) != null) {
				sb.append(tmp);
			}
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return sb.toString();
	}
}
