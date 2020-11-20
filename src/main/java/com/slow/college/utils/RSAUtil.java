/*   
 * Copyright © 2014-2016 jia-fu.cn All Rights Reserved.
 *   
 * This software is the confidential and proprietary information of   
 * TAOLUE Co.,Ltd. You shall not disclose such Confidential Information   
 * and shall use it only in accordance with the terms of the agreements   
 * you entered into with TAOLUE Co.,Ltd.    
 *   
 */

package com.slow.college.utils;

import java.io.ByteArrayOutputStream;
import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.Cipher;


/**
 * @Description: RSA工具
 * @author LHG
 * @date 2016年3月21日
 */
public class RSAUtil {

	/** */
	/**
	 * 加密算法RSA
	 */
	public static final String KEY_ALGORITHM = "RSA";

	/** */
	/**
	 * 签名算法
	 */
	public static final String SIGNATURE_ALGORITHM_MD5 = "MD5withRSA";
	public static final String SIGNATURE_ALGORITHM_SHA1 = "SHA1withRSA";
	

	/** */
	/**
	 * 获取私钥的key
	 */
	private static final String PRIVATE_KEY = "RSAPrivateKey";

	/** */
	/**
	 * RSA最大加密明文大小
	 */
	private static final int MAX_ENCRYPT_BLOCK = 117;

	/** */
	/**
	 * RSA最大解密密文大小
	 */
	private static final int MAX_DECRYPT_BLOCK = 128;

	/** */
	/**
	 * <p>
	 * 生成密钥对(公钥和私钥)
	 * </p>
	 *
	 * @return
	 * @throws Exception
	 */
	public static Map<String, Object> genKeyPair() throws Exception {
		KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance(KEY_ALGORITHM);
		keyPairGen.initialize(1024);
		KeyPair keyPair = keyPairGen.generateKeyPair();
		RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
		RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
		
		byte[] publicKeyByte=publicKey.getEncoded();
		
		String publicKeyString=Base64Util.encode(publicKeyByte);
		System.out.println("public key:"+publicKeyString);
		
		byte[] privateKeyByte=privateKey.getEncoded();
		String privateKeyString=Base64Util.encode(privateKeyByte);
		System.out.println("private key:"+privateKeyString);
		Map<String, Object> keyMap = new HashMap<String, Object>(2);
		//keyMap.put(orgPublicKey, publicKey);
		// keyMap.put(PRIVATE_KEY, privateKey);
		return keyMap;
	}

	/**
	 * <p>
	 * 用私钥对信息生成数字签名
	 * </p>
	 *
	 * @param data
	 *            已加密数据
	 * @param privateKey
	 *            私钥(BASE64编码)
	 *
	 * @return
	 * @throws Exception
	 */
	public static String sign(byte[] data, String privateKey, String algorithm) throws Exception {
		byte[] keyBytes = Base64Util.decode(privateKey);
		PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
		KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
		PrivateKey privateK = keyFactory.generatePrivate(pkcs8KeySpec);
		Signature signature = Signature.getInstance(algorithm);
		signature.initSign(privateK);
		signature.update(data);
		return Base64Util.encode(signature.sign());
	}
	public static String sign(byte[] data, String privateKey) throws Exception {
		return sign(data, privateKey, SIGNATURE_ALGORITHM_SHA1);
	}


	/** */
	/**
	 * <p>
	 * 校验数字签名
	 * </p>
	 *
	 * @param data
	 *            已加密数据
	 * @param publicKey
	 *            公钥(BASE64编码)
	 * @param sign
	 *            数字签名
	 *
	 * @return
	 * @throws Exception
	 *
	 */
	public static boolean verify(byte[] data, String publicKey, String sign, String algorithm) throws Exception {
		byte[] keyBytes = Base64Util.decode(publicKey);
		X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
		KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
		PublicKey publicK = keyFactory.generatePublic(keySpec);
		Signature signature = Signature.getInstance(algorithm);
		signature.initVerify(publicK);
		signature.update(data);
		return signature.verify(Base64Util.decode(sign));
	}

	public static boolean verify(byte[] data, String publicKey, String sign) throws Exception {
		return verify(data, publicKey, sign, SIGNATURE_ALGORITHM_SHA1);
	}


	/** */
	/**
	 * <P>
	 * 私钥解密
	 * </p>
	 *
	 * @param encryptedData
	 *            已加密数据
	 * @param privateKey
	 *            私钥(BASE64编码)
	 * @return
	 * @throws Exception
	 */
	public static byte[] decryptByPrivateKey(byte[] encryptedData, String privateKey) throws Exception {
		byte[] keyBytes = Base64Util.decode(privateKey);
		PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
		KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
		Key privateK = keyFactory.generatePrivate(pkcs8KeySpec);
		Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
		cipher.init(Cipher.DECRYPT_MODE, privateK);
		int inputLen = encryptedData.length;
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		int offSet = 0;
		byte[] cache;
		int i = 0;
		// 对数据分段解密
		while (inputLen - offSet > 0) {
			if (inputLen - offSet > MAX_DECRYPT_BLOCK) {
				cache = cipher.doFinal(encryptedData, offSet, MAX_DECRYPT_BLOCK);
			} else {
				cache = cipher.doFinal(encryptedData, offSet, inputLen - offSet);
			}
			out.write(cache, 0, cache.length);
			i++;
			offSet = i * MAX_DECRYPT_BLOCK;
		}
		byte[] decryptedData = out.toByteArray();
		out.close();
		return decryptedData;
	}

	/** */
	/**
	 * <p>
	 * 公钥解密
	 * </p>
	 *
	 * @param encryptedData
	 *            已加密数据
	 * @param publicKey
	 *            公钥(BASE64编码)
	 * @return
	 * @throws Exception
	 */
	public static byte[] decryptByPublicKey(byte[] encryptedData, String publicKey) throws Exception {
		byte[] keyBytes = Base64Util.decode(publicKey);
		X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(keyBytes);
		KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
		Key publicK = keyFactory.generatePublic(x509KeySpec);
		Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
		cipher.init(Cipher.DECRYPT_MODE, publicK);
		int inputLen = encryptedData.length;
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		int offSet = 0;
		byte[] cache;
		int i = 0;
		// 对数据分段解密
		while (inputLen - offSet > 0) {
			if (inputLen - offSet > MAX_DECRYPT_BLOCK) {
				cache = cipher.doFinal(encryptedData, offSet, MAX_DECRYPT_BLOCK);
			} else {
				cache = cipher.doFinal(encryptedData, offSet, inputLen - offSet);
			}
			out.write(cache, 0, cache.length);
			i++;
			offSet = i * MAX_DECRYPT_BLOCK;
		}
		byte[] decryptedData = out.toByteArray();
		out.close();
		return decryptedData;
	}

	/** */
	/**
	 * <p>
	 * 公钥加密
	 * </p>
	 *
	 * @param data
	 *            源数据
	 * @param publicKey
	 *            公钥(BASE64编码)
	 * @return
	 * @throws Exception
	 */
	public static byte[] encryptByPublicKey(byte[] data, String publicKey) throws Exception {
		// byte[] keyBytes = Base64Utils.decode(publicKey);
		byte[] keyBytes = Base64Util.decode(publicKey);

		X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(keyBytes);

		KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
		Key publicK = keyFactory.generatePublic(x509KeySpec);
		// 对数据加密
		// Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
		Cipher cipher = Cipher.getInstance(KEY_ALGORITHM);
		cipher.init(Cipher.ENCRYPT_MODE, publicK);

		return cipher.doFinal(data);
	}

	/** */
	/**
	 * <p>
	 * 私钥加密
	 * </p>
	 *
	 * @param data
	 *            源数据
	 * @param privateKey
	 *            私钥(BASE64编码)
	 * @return
	 * @throws Exception
	 */
	public static byte[] encryptByPrivateKey(byte[] data, String privateKey) throws Exception {

		byte[] keyBytes = Base64Util.decode(privateKey);
		PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
		KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
		Key privateK = keyFactory.generatePrivate(pkcs8KeySpec);
		Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
		cipher.init(Cipher.ENCRYPT_MODE, privateK);
		int inputLen = data.length;
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		int offSet = 0;
		byte[] cache;
		int i = 0;
		// 对数据分段加密
		while (inputLen - offSet > 0) {
			if (inputLen - offSet > MAX_ENCRYPT_BLOCK) {
				cache = cipher.doFinal(data, offSet, MAX_ENCRYPT_BLOCK);
			} else {
				cache = cipher.doFinal(data, offSet, inputLen - offSet);
			}
			out.write(cache, 0, cache.length);
			i++;
			offSet = i * MAX_ENCRYPT_BLOCK;
		}
		//byte[] encryptedData = out.toByteArray();
		out.close();

		byte[] epByte = cipher.doFinal(data);

		return Base64Util.encodeByte(epByte);
	}

	/** */
	/**
	 * <p>
	 * 获取私钥
	 * </p>
	 *
	 * @param keyMap
	 *            密钥对
	 * @return
	 * @throws Exception
	 */
	public static String getPrivateKey(Map<String, Object> keyMap) throws Exception {
		Key key = (Key) keyMap.get(PRIVATE_KEY);
		return Base64Util.encode(key.getEncoded());
	}

	/** */
	/**
	 * <p>
	 * 获取公钥
	 * </p>
	 *
	 * @param keyMap
	 *            密钥对
	 * @return
	 * @throws Exception
	 */
	public static String getPublicKey(Map<String, Object> keyMap, String publicKey) throws Exception {
		Key key = (Key) keyMap.get(publicKey);
		return Base64Util.encode(key.getEncoded());
	}

	 public static void main(String[] args) throws Exception{
	 /* StringBuffer chkValueSb = new StringBuffer();
	 String timestamp = DateUtil.getDateNoSs();
	 chkValueSb.append("CPIC").append(",").append("PCIC").append(",").append("13901620812")
	 .append(",").append("100.00").append(",").append("").append(",")
	 .append(timestamp).append(",").append("1000000011").append(",").append(RSAUtils.MD5_KEY);
	
	 System.out.println("chkValuesb="+chkValueSb.toString());
	
	 String chkValueMD5 = MD5Util.sign(chkValueSb.toString()).toLowerCase();
	
	*/
	 //Map<String, Object> keyMap = genKeyPair();
	 //String publicKey = getPublicKey(keyMap,"416b90753bb726d860d58779e7636a49");
	 //String privateKey = getPrivateKey(keyMap);
	
	// System.out.println("publicKey="+keyMap.toString());
	// System.out.println("privateKey="+keyMap.get("PRIVATE_KEY"));
	 
	
		 
		 
	//String  sign="QTe4yXCurkOp19H6xHt3j3MkeyH7jLd4yM%2BzIdv7CkHtw5pzDLoBvWEp0tbkgmqqMY0LVX44ETVb%0AaacH%2BTXBSjFQWASBZOoCl/qPpv9gx6bG42rxPkhunxqAck0x/6pZ/EhoTeBMWhflOX0ojCHucIxO%0A08s3hxReEpc6N69lwzM%3D%0A";
	//String publicKey="MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCiuZnq5UZx/5i0VKS8sU7TU52hVYAxNY7GeZElAjVGl4FmuiN7mrMIcpuGyDiZ5XLL8JaKZQzfvi1ri5vnslRJlf2d92HPTv1Sbhlli1uVFh862QtEdp3LilqDh0ZojL9SFRipSqgVq4E4cmcdaAgyXFnKLaHegHhRKzcwhnXVEwIDAQAB";
	String s="entName=北京首都在线科技有限公司&osid=777668130&ticket=capitalonline&timestamp=1474363693";
	
	//System.out.println(verify(s.getBytes("UTF-8"), publicKey, sign));
		 
     String publicKey="MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDD6kGyneI1jSOE8pMvouzt8+xpb4IBd5t9qy/33m9sVZo9fRZ/G87FKU96miyLT9ArhE6vKHdNbrIkFzjmYNiZ4OYZM6sN22fSZHS9BG8fnHrcX2/u/aH/ydf3sreTUfx1gIksIFC+ZxeViSTgUchDaRog9AdepuYwFp21dr8nAQIDAQAB";
	 String privateKey="MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAMPqQbKd4jWNI4Tyky+i7O3z7GlvggF3m32rL/feb2xVmj19Fn8bzsUpT3qaLItP0CuETq8od01usiQXOOZg2Jng5hkzqw3bZ9JkdL0Ebx+cetxfb+79of/J1/eyt5NR/HWAiSwgUL5nF5WJJOBRyENpGiD0B16m5jAWnbV2vycBAgMBAAECgYA4hrVv8MfcxZ0y9b5r5ylPOqrCfQ7Yl4cAikzryn0Bl+1JwnakNATDtDifWi5x/5RDiggd16IccEzrxtt+9QBpW9eFs34SkIjrywPU7EIgSm4T+t3P8DsnZ/40II00ZHo75FRk0TDhcF9Q3WqmFA96vuP5NNfJBqjPFY8U5ooCcQJBAPQ5iGUctAeGtd5CaFb54ZP29gChVNlF+8j3T53uu8/6IC3+bsST0O+ibMO/ud4vrW8UWELuFtZdT+LeBVDYWsUCQQDNXG/y2x0ScqVpwdqBMyq4RHPqmFsbJuiuQdkYDm48j8Bx9gRYsifsLTZalS2eC5knN864C0wIV7wCOPyvvQ8NAkBgO8tjDExkKtC/I6u5mf0pMMb+uJOWid0M82aL9OZMrWLAvOEo1JYf1wDEoWe8BU1x17JvrSGSlnJEEvhFQvuBAkEAoQ5r5eUeSceMHwMfoNBGIrncxxGx8ftz5KZT4l/+6JQ886NkCUO/t6RFyojQe/DQrkdAYmrJuIlGM+AJnLIDSQJAT+YKb+VvQeR0OY2Efyyu1AhHnB/wDHh31VJVDRl9dr/XXc6Xw5p1ZNIOqy8fwh3Po1ai+5QCTDGAMBCVwI3S/w==";
	 String signature=sign(s.getBytes("UTF-8"),privateKey);
	 System.out.println("sign:"+signature);
	 
	 System.out.println("verify:"+verify(s.getBytes("UTF-8"),publicKey,signature));
	 
	 
//	 String aaa =
//	 "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQC/LehDzIiBZKZieF+Z2rE0V0iuy3ZWrI7Nd8Clyy4m1pQdxrJjzAiKyRSyd0+cjoM2zm2pWodJcoOxljkPcpSYxRLbt9VG5rtYRV8ZpNRK+qxoWRBAEa4V4l7bZygCWbzAVodLpXIw9dtJS8s1eKTyLcfFMLgm7vWjclkFOBN0uQIDAQAB";
//	 BASE64Decoder decoder = new BASE64Decoder();
//	 byte[] keyBytes = decoder.decodeBuffer(aaa);
//	
//	 X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(keyBytes);
//	 KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
//	 Key publicK = keyFactory.generatePublic(x509KeySpec);
//	 System.out.println(publicK);
//	
//	 Cipher cipher = Cipher.getInstance("RSA");
//	 cipher.init(Cipher.ENCRYPT_MODE, publicK);
//	 System.out.println(Base64Util.encode(cipher.doFinal("7dccf8e0727046ef69988e233e18eda9".getBytes())));
//	// System.out.println(new
//	 String(RSAUtils.encryptByPublicKey(chkValueMD5.getBytes(), publicKey)));
	 }
}
