package com.slow.college.utils;

import java.io.UnsupportedEncodingException;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

public class DesEncryptUtil {
	
	static final String GLOBAL_PWD = "huolixyz";
	
	public DesEncryptUtil() {
	}

	// 测试
	public static void main(String args[]) throws UnsupportedEncodingException {
		// 待加密内容
		String str = "a234567";
		// 密码，长度要是8的倍数
		// String password = "huolixyz";

		String result = DesEncryptUtil.encrypt(str.getBytes());
		System.out.println("加密后：" + result);

		// 直接将如上内容解密
		try {
			String decryResult = DesEncryptUtil.decrypt(result);
			System.out.println("解密后：" + decryResult);
		} catch (Exception e1) {
			e1.printStackTrace();
		}

	}

	/**
	 * 加密
	 */
	public static String encrypt(byte[] datasource) {
		return encrypt(datasource, GLOBAL_PWD);
	}
	
	/**
	 * 加密2
	 */
	public static String encrypt(byte[] datasource, String password) {
		try {
			SecureRandom random = new SecureRandom();
			DESKeySpec desKey = new DESKeySpec(password.getBytes());
			// 创建一个密匙工厂，然后用它把DESKeySpec转换成
			SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
			SecretKey securekey = keyFactory.generateSecret(desKey);
			// Cipher对象实际完成加密操作
			Cipher cipher = Cipher.getInstance("DES");
			// 用密匙初始化Cipher对象
			cipher.init(Cipher.ENCRYPT_MODE, securekey, random);
			// 现在，获取数据并加密
			// 正式执行加密操作
			return parseByte2HexStr(cipher.doFinal(datasource));
		} catch (Throwable e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 解密
	 */
	public static String decrypt(String src) throws Exception {
		return decrypt(src, GLOBAL_PWD);
	}

	/**
	 * 解密2
	 */
	public static String decrypt(String src, String password) throws Exception {
		// DES算法要求有一个可信任的随机数源
		SecureRandom random = new SecureRandom();
		// 创建一个DESKeySpec对象
		DESKeySpec desKey = new DESKeySpec(password.getBytes());
		// 创建一个密匙工厂
		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
		// 将DESKeySpec对象转换成SecretKey对象
		SecretKey securekey = keyFactory.generateSecret(desKey);
		// Cipher对象实际完成解密操作
		Cipher cipher = Cipher.getInstance("DES");
		// 用密匙初始化Cipher对象
		cipher.init(Cipher.DECRYPT_MODE, securekey, random);
		// 真正开始解密操作
		return new String(cipher.doFinal(parseHexStr2Byte(src)));
	}
	
	public static String parseByte2HexStr(byte buf[]) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < buf.length; i++) {
			String hex = Integer.toHexString(buf[i] & 0xFF);
			if (hex.length() == 1) {
				hex = '0' + hex;
			}
			sb.append(hex.toUpperCase());
		}
		return sb.toString();
	}
	
	public static byte[] parseHexStr2Byte(String hexStr) {
		if (hexStr.length() < 1)
			return null;
		byte[] result = new byte[hexStr.length() / 2];
		for (int i = 0; i < hexStr.length() / 2; i++) {
			int high = Integer.parseInt(hexStr.substring(i * 2, i * 2 + 1), 16);
			int low = Integer.parseInt(hexStr.substring(i * 2 + 1, i * 2 + 2),
					16);
			result[i] = (byte) (high * 16 + low);
		}
		return result;
	}
}