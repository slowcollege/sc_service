package com.slow.college.utils;

import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;

import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;

/**
 *
 * @author glcword
 *
 */
public class EasyX509TrustManager implements X509TrustManager {
	private X509TrustManager trustMan = null;

	public EasyX509TrustManager(KeyStore keystore, String algorithm)
			throws NoSuchAlgorithmException, KeyStoreException {
		TrustManagerFactory factory = TrustManagerFactory
				.getInstance(algorithm);
		factory.init(keystore);
		TrustManager[] tms = factory.getTrustManagers();
		if (tms != null) {
			for (int i = 0; i < tms.length; i++) {
				if (tms[i] instanceof X509TrustManager) {
					trustMan = (X509TrustManager) tms[i];
				}
			}
		}
	}

	@Override
	public void checkClientTrusted(X509Certificate[] chain, String authType) {
		try {
			trustMan.checkClientTrusted(chain, authType);
		} catch (Exception e) {
		}
	}

	@Override
	public void checkServerTrusted(X509Certificate[] chain, String authType) {
		try {
			trustMan.checkServerTrusted(chain, authType);
		} catch (Exception e) {
		}
	}

	@Override
	public X509Certificate[] getAcceptedIssuers() {
		return trustMan != null ? trustMan.getAcceptedIssuers() : null;
	}

}
