package com.slow.college.utils;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.net.ssl.HttpsURLConnection;
import java.io.*;
import java.net.*;
import java.util.Map;
import java.util.Set;
import java.util.zip.GZIPInputStream;


/**
 * 根据url获取页面信息的工具类
 *
 * @author songming
 *
 */
public final class NetUtils {

	private NetUtils() {

	}

	public static final String FF_UA = "Mozilla/5.0 (Macintosh; Intel Mac OS X 10.6; rv:14.0) Gecko/20100101 Firefox/14.0.1";
	public static final String CHROME_USER_AGENT = "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_6_8) AppleWebKit/535.19 (KHTML, like Gecko) Chrome/18.0.1025.163 Safari/535.19";
	public static final String IPHONE_USER_AGENT = "Mozilla/5.0 (iPhone; U; CPU iPhone OS 4_3_5 like Mac OS X; zh-cn) AppleWebKit/533.17.9 (KHTML, like Gecko) Version/5.0.2 Mobile/8L1 Safari/6533.18.5";
	// private static final Proxy PROXY = new Proxy(Proxy.Type.HTTP,
	// new InetSocketAddress("10.0.1.11", 12345));
	private static final Logger LOG = LogManager.getLogger(NetUtils.class);
	public static final int DEFAULT_TIME_OUT_SECOND = 50;

	public static String getRedirectURL(String link) {
		HttpURLConnection conn = null;
		try {
			conn = createHttpConn(link, null);
			conn.setConnectTimeout(1000 * DEFAULT_TIME_OUT_SECOND);
			conn.setReadTimeout(1000 * DEFAULT_TIME_OUT_SECOND);
			conn.setRequestProperty("user-agent", CHROME_USER_AGENT);

			conn.getResponseCode();
			link = conn.getURL().toString();
			return link;
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("重定向错误");
		} finally {
			if (conn != null) {
				conn.disconnect();
			}
		}
		return null;
	}

	/**
	 * 根据url获取页面的信息
	 *
	 * @param url
	 * @return byte数组
	 */
	public static byte[] getBinary(String url) {
		byte[] data = null;
		try {
			data = getBinary(url, 1000 * DEFAULT_TIME_OUT_SECOND, false);
		} catch (java.net.SocketTimeoutException e) {
			System.out.println("超时：" + url);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		if (data == null) {
			try {
				// 第2次超时时间加倍
				data = getBinary(url, 1000 * DEFAULT_TIME_OUT_SECOND * 2, false);
			} catch (java.net.SocketTimeoutException e) {
				System.out.println("超时：" + url);
			} catch (MalformedURLException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return data;
	}

	/**
	 * 根据url获取页面的信息是否需要解压
	 *
	 * @param url
	 * @return byte数组
	 */
	public static byte[] getBinary(String url, boolean gzip) {
		byte[] data = null;
		try {
			data = getBinary(url, 1000 * DEFAULT_TIME_OUT_SECOND, gzip);
		} catch (java.net.SocketTimeoutException e) {
			System.out.println("超时：" + url);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		if (data == null) {
			try {
				// 第2次超时时间加倍
				data = getBinary(url, 1000 * DEFAULT_TIME_OUT_SECOND * 2, gzip);
			} catch (java.net.SocketTimeoutException e) {
				System.out.println("超时：" + url);
			} catch (MalformedURLException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return data;
	}

	public static byte[] getBinary(String url, Map<String, String> map) {
		byte[] data = null;
		try {
			data = getBinary(url, 1000 * DEFAULT_TIME_OUT_SECOND, map);
		} catch (java.net.SocketTimeoutException e) {
			System.out.println("超时：" + url);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		if (data == null) {
			try {
				// 第2次超时时间加倍
				data = getBinary(url, 1000 * DEFAULT_TIME_OUT_SECOND * 2, map);
			} catch (java.net.SocketTimeoutException e) {
				System.out.println("超时：" + url);
			} catch (MalformedURLException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return data;
	}

	/**
	 *
	 * @param url
	 * @param param
	 * @return
	 */
	public static byte[] getBinary(String url, String param) {
		byte[] data = null;
		try {
			data = getBinary(url, 1000 * DEFAULT_TIME_OUT_SECOND, param);
		} catch (java.net.SocketTimeoutException e) {
			System.out.println("超时：" + url);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		if (data == null) {
			try {
				// 第2次超时时间加倍
				data = getBinary(url, 1000 * DEFAULT_TIME_OUT_SECOND * 2, param);
			} catch (java.net.SocketTimeoutException e) {
				System.out.println("超时：" + url);
			} catch (MalformedURLException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return data;
	}

	/**
	 *
	 * @param url
	 * @param timeoutSecond
	 * @param param
	 * @return
	 * @throws MalformedURLException
	 * @throws IOException
	 */
	private static byte[] getBinary(String url, int timeoutSecond, String param)
			throws MalformedURLException, IOException {
		InputStream in = null;
		HttpURLConnection conn = null;
		conn = createHttpConn(url, null);
		conn.setConnectTimeout(1000 * DEFAULT_TIME_OUT_SECOND);
		conn.setReadTimeout(1000 * DEFAULT_TIME_OUT_SECOND);
		conn.setRequestMethod("POST");// 提交模式
		conn.setDoOutput(true);// 是否输入参数
		byte[] bypes = param.toString().getBytes();
		conn.getOutputStream().write(bypes);// 输入参数
		int code = conn.getResponseCode();

		if (code >= 400) {
			// if (logger.isDebugEnabled()) {
			// logger.debug(conn.getResponseCode() + ","
			// + conn.getResponseMessage() + "," + url);
			// }
			return null;
		}

		in = conn.getInputStream();
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		byte[] data = new byte[1024 * 4];
		int count = in.read(data);
		while (count > 0) {
			out.write(data, 0, count);
			count = in.read(data);
		}
		out.flush();
		closeInputStream(in);
		closeHttpURLConnection(conn);
		return out.toByteArray();
	}

	private static byte[] getBinary(String url, int timeoutSecond, Map<String, String> map)
			throws MalformedURLException, IOException {
		InputStream in = null;
		HttpURLConnection conn = null;
		conn = createHttpConn(url, null);
		conn.setConnectTimeout(timeoutSecond);
		conn.setReadTimeout(timeoutSecond);
		for (String str : map.keySet()) {
			conn.setRequestProperty(str, map.get(str));
		}
		int code = conn.getResponseCode();
		if (code >= 400) {
			// if (logger.isDebugEnabled()) {
			// logger.debug(conn.getResponseCode() + ","
			// + conn.getResponseMessage() + "," + url);
			// }
			return null;
		}
		in = conn.getInputStream();
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		byte[] data = new byte[1024 * 4];
		int count = in.read(data);
		while (count > 0) {
			out.write(data, 0, count);
			count = in.read(data);
		}
		out.flush();
		closeInputStream(in);
		closeHttpURLConnection(conn);
		return out.toByteArray();
	}

	private static byte[] getBinary(String url, int timeoutSecond, boolean gzip)
			throws MalformedURLException, IOException {
		InputStream in = null;
		HttpURLConnection conn = null;
		conn = createHttpConn(url, null);
		conn.setConnectTimeout(timeoutSecond);
		conn.setReadTimeout(timeoutSecond);
		conn.setRequestProperty("user-agent", CHROME_USER_AGENT);
		int code = conn.getResponseCode();
		if (code >= 400) {
			// if (logger.isDebugEnabled()) {
			// logger.debug(conn.getResponseCode() + ","
			// + conn.getResponseMessage() + "," + url);
			// }
			return null;
		}
		in = conn.getInputStream();
		if (gzip) {
			String encoding = conn.getContentEncoding();
			encoding = encoding != null ? encoding.toLowerCase() : "";
			if (in != null && encoding.contains("gzip")) {
				in = new GZIPInputStream(in);
			}
		}
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		byte[] data = new byte[1024 * 4];
		int count = in.read(data);
		while (count > 0) {
			out.write(data, 0, count);
			count = in.read(data);
		}
		out.flush();
		closeInputStream(in);
		closeHttpURLConnection(conn);
		return out.toByteArray();
	}

	public static void closeHttpURLConnection(HttpURLConnection conn) {
		if (conn != null) {
			conn.disconnect();
		}
	}

	public static void closeInputStream(InputStream in) {
		if (in != null) {
			try {
				in.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public static void closeOutputStream(OutputStream out) {
		if (out != null) {
			try {
				out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public static String filteURL(String url) {
		int index = url.indexOf("?");
		if (index > 0) {
			return url.substring(0, index);
		}
		return url;
	}

	public static String doHttpPost(String xmlInfo, String URL, String respCharsetName) {
		// System.out.println("发起的数据:"+xmlInfo);
		byte[] xmlData = xmlInfo.getBytes();
		InputStream instr = null;
		try {
			URLConnection urlCon = createHttpConn(URL, null);
			urlCon.setDoOutput(true);
			urlCon.setDoInput(true);
			urlCon.setUseCaches(false);
			urlCon.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
			urlCon.setRequestProperty("Content-length", String.valueOf(xmlData.length));
			// System.out.println(String.valueOf(xmlData.length));
			DataOutputStream printout = new DataOutputStream(urlCon.getOutputStream());
			printout.write(xmlData);
			printout.flush();
			printout.close();
			instr = urlCon.getInputStream();
			byte[] bis = IOUtils.toByteArray(instr);
			String ResponseString = new String(bis, respCharsetName);
			if ((ResponseString == null) || ("".equals(ResponseString.trim()))) {
				System.out.println("返回空");
			}
			// System.out.println("返回数据为:" + ResponseString);
			return ResponseString;

		} catch (Exception e) {
			e.printStackTrace();
			return "0";
		} finally {
			try {
				instr.close();

			} catch (Exception ex) {
				return "0";
			}
		}
	}

	public static String doHttpPost(String xmlInfo, String URL, Map<String, String> requestPropertys) {
		// System.out.println("发起的数据:"+xmlInfo);
		byte[] xmlData = xmlInfo.getBytes();
		InputStream instr = null;
		try {
			URLConnection urlCon = createHttpConn(URL, null);
			urlCon.setDoOutput(true);
			urlCon.setDoInput(true);
			urlCon.setUseCaches(false);
			Set<String> keys = requestPropertys.keySet();
			for (String key : keys) {
				urlCon.setRequestProperty(key, requestPropertys.get(key));
			}
			// urlCon.setRequestProperty("Content-Type",
			// "application/json;charset=UTF-8");
			urlCon.setRequestProperty("Content-length", String.valueOf(xmlData.length));
			DataOutputStream printout = new DataOutputStream(urlCon.getOutputStream());
			printout.write(xmlData);
			printout.flush();
			printout.close();
			instr = urlCon.getInputStream();
			byte[] bis = IOUtils.toByteArray(instr);
			String ResponseString = new String(bis, "UTF-8");
			if ((ResponseString == null) || ("".equals(ResponseString.trim()))) {
				System.out.println("返回空");
			}
			// System.out.println("返回数据为:" + ResponseString);
			return ResponseString;

		} catch (Exception e) {
			e.printStackTrace();
			return "0";
		} finally {
			try {
				instr.close();

			} catch (Exception ex) {
				return "0";
			}
		}
	}

	public static String doHttpPost1(String xmlInfo, String URL) {
		byte[] xmlData = xmlInfo.getBytes();
		InputStream instr = null;
		String ResponseString = null;
		try {
			URLConnection urlCon = createHttpConn(URL, null);
			urlCon.setDoOutput(true);
			urlCon.setDoInput(true);
			urlCon.setUseCaches(false);
			// urlCon.setRequestProperty("Content-Type",
			// "application/json;charset=UTF-8");
			urlCon.setRequestProperty("Content-length", String.valueOf(xmlData.length));
			DataOutputStream printout = new DataOutputStream(urlCon.getOutputStream());
			printout.write(xmlData);
			printout.flush();
			printout.close();
			instr = urlCon.getInputStream();
			byte[] bis = IOUtils.toByteArray(instr);
			if (bis != null) {
				ResponseString = new String(bis, "UTF-8");
			}
			// if ((ResponseString == null) ||
			// ("".equals(ResponseString.trim()))) {
			// System.out.println("返回空");
			// }

			// return ResponseString;
		} catch (Exception e) {
			LOG.error("Exception", e);

		} finally {
			try {
				instr.close();
			} catch (Exception ex) {
				LOG.error("Exception", ex);
			}
		}
		return ResponseString;
	}

	public static String doHttpPost2(String xmlInfo, String URL) {
		byte[] xmlData = xmlInfo.getBytes();
		InputStream instr = null;
		String ResponseString = null;
		try {
			URLConnection urlCon = createHttpConn(URL, null);
			urlCon.setDoOutput(true);
			urlCon.setDoInput(true);
			urlCon.setUseCaches(false);
			// urlCon.setRequestProperty("Content-Type",
			// "application/json;charset=UTF-8");
			urlCon.setRequestProperty("Content-length", String.valueOf(xmlData.length));
			DataOutputStream printout = new DataOutputStream(urlCon.getOutputStream());
			printout.write(xmlData);
			printout.flush();
			printout.close();
			instr = urlCon.getInputStream();
			byte[] bis = IOUtils.toByteArray(instr);
			if (bis != null) {
				ResponseString = new String(bis, "UTF-8");
			}
			// if ((ResponseString == null) ||
			// ("".equals(ResponseString.trim()))) {
			// System.out.println("返回空");
			// }

			// return ResponseString;
		} catch (Exception e) {

		} finally {
			try {
				instr.close();
			} catch (Exception ex) {
			}
		}
		return ResponseString;
	}

	public static String httpPost(String xmlInfo, String httpsUrl) {
		System.out.println("发起的数据:" + xmlInfo);
		byte[] xmlData = xmlInfo.getBytes();
		InputStream instr = null;
		try {
			URLConnection urlCon = createHttpConn(httpsUrl, null);
			urlCon.setDoOutput(true);
			urlCon.setDoInput(true);
			urlCon.setUseCaches(false);
			urlCon.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
			urlCon.setRequestProperty("Content-length", String.valueOf(xmlData.length));
			// System.out.println(String.valueOf(xmlData.length));
			DataOutputStream printout = new DataOutputStream(urlCon.getOutputStream());
			printout.write(xmlData);
			printout.flush();
			printout.close();
			instr = urlCon.getInputStream();
			byte[] bis = IOUtils.toByteArray(instr);
			String ResponseString = new String(bis, "UTF-8");
			if ((ResponseString == null) || ("".equals(ResponseString.trim()))) {
				System.out.println("返回空");
			}
			// System.out.println("返回数据为:" + ResponseString);
			return ResponseString;

		} catch (Exception e) {
			e.printStackTrace();
			return "0";
		} finally {
			try {
				instr.close();

			} catch (Exception ex) {
				return "0";
			}
		}
	}

	public static String httpsPost(String xmlStr, String httpsUrl) {
		HttpsURLConnection urlCon = null;

		String res = "";
		try {
			urlCon = (HttpsURLConnection) (new URL(httpsUrl)).openConnection();
			urlCon.setDoInput(true);
			urlCon.setDoOutput(true);
			urlCon.setRequestMethod("POST");
			if (xmlStr != "" && xmlStr.length() > 0) {
				urlCon.setRequestProperty("Content-Length", String.valueOf(xmlStr.getBytes().length));
			}
			urlCon.setUseCaches(false);
			// 设置为gbk可以解决服务器接收时读取的数据中文乱码问题
			if (xmlStr != "" && xmlStr.length() > 0) {
				urlCon.getOutputStream().write(xmlStr.getBytes("gbk"));
			}
			urlCon.getOutputStream().flush();
			urlCon.getOutputStream().close();
			BufferedReader in = new BufferedReader(new InputStreamReader(urlCon.getInputStream()));

			String line;
			while ((line = in.readLine()) != null) {
				res = res + line;
			}

			// System.out.println(res);

		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return res;
	}

	public static String httpsPost1(String xmlStr, String httpsUrl) throws Exception {
		HttpsURLConnection urlCon = null;

		String res = "";
		urlCon = (HttpsURLConnection) (new URL(httpsUrl)).openConnection();
		urlCon.setDoInput(true);
		urlCon.setDoOutput(true);
		urlCon.setRequestMethod("POST");
		if (xmlStr != "" && xmlStr.length() > 0) {
			urlCon.setRequestProperty("Content-Length", String.valueOf(xmlStr.getBytes().length));
		}
		urlCon.setUseCaches(false);
		// 设置为gbk可以解决服务器接收时读取的数据中文乱码问题
		if (xmlStr != "" && xmlStr.length() > 0) {
			urlCon.getOutputStream().write(xmlStr.getBytes("gbk"));
		}
		urlCon.getOutputStream().flush();
		urlCon.getOutputStream().close();
		BufferedReader in = new BufferedReader(new InputStreamReader(urlCon.getInputStream()));

		String line;
		while ((line = in.readLine()) != null) {
			res = res + line;
		}

		// System.out.println(res);

		return res;
	}

	/**
	 * 创建HttpURLConnection
	 *
	 * @param urlStr
	 * @param proxy
	 * @return
	 * @throws IOException
	 */
	public static HttpURLConnection createHttpConn(String urlStr, Proxy proxy) throws IOException {
		if (urlStr.startsWith("https")) {
			try {
				HttpsURLConnection.setDefaultSSLSocketFactory(new EasySslSocketFactory());
			} catch (Exception e) {
				throw new IOException(e);
			}
			HttpsURLConnection.setDefaultHostnameVerifier(new EasyHostnameVerifier());
		}
		HttpURLConnection conn = null;
		if (proxy == null) {
			conn = (HttpURLConnection) new URL(urlStr).openConnection();
		} else {
			conn = (HttpURLConnection) new URL(urlStr).openConnection(proxy);
		}
		return conn;
	}


    /**
     * 判定是http还是https
     */
    public static String checkHttpType(String param,String url){
        String result = null;
        try {
            if ( StringUtils.isNotBlank(url) ) {
                if ( url.contains("https:") ) {
                    result = httpsPost(param,url);
                } else if ( url.contains("http:") ) {
                    result = doHttpPost2(param,url);
                }
            }
        } catch (Exception e) {
            LOG.info(url + "\t 接口调用失败：" + e.getMessage());
        }
        return result;
    }
    
}
