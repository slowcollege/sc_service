package com.slow.college.utils;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;

/**
 *
 * @author glcword
 *
 */
public class EasySslSocketFactory extends SSLSocketFactory {
	private SSLContext sslCtx;

	public EasySslSocketFactory() throws NoSuchAlgorithmException,
			KeyManagementException, KeyStoreException, CertificateException,
			IOException {
		KeyStore trustStore = KeyStore.getInstance(KeyStore.getDefaultType());
		trustStore.load(null, null);
		sslCtx = SSLContext.getInstance("TLS");
		sslCtx.init(null, new TrustManager[] { new EasyX509TrustManager(
				trustStore, TrustManagerFactory.getDefaultAlgorithm()) },
				new SecureRandom());
	}

	@Override
	public Socket createSocket(Socket s, String host, int port,
			boolean autoClose) throws IOException {
		return sslCtx.getSocketFactory().createSocket(s, host, port, autoClose);
	}

	@Override
	public Socket createSocket(String host, int port) throws IOException,
			UnknownHostException {
		return sslCtx.getSocketFactory().createSocket(host, port);
	}

	@Override
	public Socket createSocket(InetAddress host, int port) throws IOException {
		return sslCtx.getSocketFactory().createSocket(host, port);
	}

	@Override
	public Socket createSocket(String host, int port, InetAddress localHost,
			int localPort) throws IOException, UnknownHostException {
		return sslCtx.getSocketFactory().createSocket(host, port, localHost,
				localPort);
	}

	@Override
	public Socket createSocket(InetAddress address, int port,
			InetAddress localAddress, int localPort) throws IOException {
		return sslCtx.getSocketFactory().createSocket(address, port,
				localAddress, localPort);
	}

	@Override
	public String[] getDefaultCipherSuites() {
		return sslCtx.getSocketFactory().getDefaultCipherSuites();
	}

	@Override
	public String[] getSupportedCipherSuites() {
		return sslCtx.getSocketFactory().getSupportedCipherSuites();
	}

}
