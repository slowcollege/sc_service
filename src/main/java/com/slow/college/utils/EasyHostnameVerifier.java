package com.slow.college.utils;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;

/**
 *
 * @author glcword
 *
 */
public class EasyHostnameVerifier implements HostnameVerifier {

	@Override
	public boolean verify(String host, SSLSession session) {
		return true;
	}

}
