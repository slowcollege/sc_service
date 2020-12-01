package com.slow.college.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.commons.fileupload.FileItem;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.gson.Gson;
import com.qiniu.common.QiniuException;
import com.qiniu.common.Zone;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import com.slow.college.config.SlowCollegeConfig;

public class QiniuUtil {

	// 设置需要操作的账号的ak 和sk
	private static final String ACCESS_KEY = SlowCollegeConfig.qiniuAccessKey;
	private static final String SECRET_KEY = SlowCollegeConfig.qiniuSecretKey;

	// 要上传的空间
	private static final String bucketName = SlowCollegeConfig.qiniuBucket;

	// 秘钥
	private static final Auth auth = Auth.create(ACCESS_KEY, SECRET_KEY);

	private static final Logger log = LogManager.getLogger(QiniuUtil.class);
	
	// 普通上传
	public static String upload(String filePath, String fileName) throws IOException {
		String res = "";
		// 构造一个带指定Zone对象的配置类
		Configuration cfg = new Configuration(Zone.zone1());
		// 创建上传对象
		UploadManager manager = new UploadManager(cfg);

		String upToken = auth.uploadToken(bucketName);
		log.info(1);
		try {
			Response response = manager.put(filePath, fileName, upToken);
			// 解析上传成功的结果
			DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
			System.out.println(putRet.key);
			System.out.println(putRet.hash);
			res = SlowCollegeConfig.qiniuRequestUrlHead + "/" + fileName;	//JFServerConfig.getConfig().getQiniuRequestUrlHead() 
		} catch (QiniuException ex) {
			Response r = ex.response;
			System.err.println(r.toString());
			try {
				System.err.println(r.bodyString());
			} catch (QiniuException ex2) {
				// ignore
			}
		}
		return res;

	}

	@SuppressWarnings({ "rawtypes", "unused" })
	public static void uploadToQiNiuYuns(HashMap<String, FileItem> files) throws IOException {
		// 构造一个带指定Zone对象的配置类
		Configuration cfg = new Configuration(Zone.zone2());
		// ...其他参数参考类注释
		// 华东 Zone.zone0()
		// 华北 Zone.zone1()
		// 华南 Zone.zone2()
		// 北美 Zone.zoneNa0()

		UploadManager uploadManager = new UploadManager(cfg);
		// ...生成上传凭证，然后准备上传
		// String accessKey = "nPVt7d0gCZP9CdLVjw6o";// 这里请替换成自己的AK
		// String secretKey = "p-GkW8RS4Y0_EffU";// 这里请替换成自己的SK
		// String bucket = "shouxiang";// 这里请替换成自己的bucket--空间名

		// 默认不指定key的情况下，以文件内容的hash值作为文件名
		// String key = null;

		Iterator iter = files.entrySet().iterator();
		int i = 0;
		while (iter.hasNext()) {
			i++;
			Map.Entry entry = (Map.Entry) iter.next();
			String fileName = (String) entry.getKey();
			FileItem val = (FileItem) entry.getValue();
			InputStream inputStream = val.getInputStream();
			ByteArrayOutputStream swapStream = new ByteArrayOutputStream();
			byte[] buff = new byte[600]; // buff用于存放循环读取的临时数据
			int rc = 0;
			while ((rc = inputStream.read(buff, 0, 100)) > 0) {
				swapStream.write(buff, 0, rc);
			}
			byte[] uploadBytes = swapStream.toByteArray(); // uploadBytes
															// 为转换之后的结果
			Auth auth = Auth.create(ACCESS_KEY, SECRET_KEY);
			String upToken = auth.uploadToken(bucketName);
			try {
				Response response = uploadManager.put(uploadBytes, fileName, upToken);
				// 解析上传成功的结果
				DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
				System.out.println(putRet.key);
				System.out.println(putRet.hash);
			} catch (QiniuException ex) {
				Response r = ex.response;
				System.err.println(r.toString());
				try {
					System.err.println(r.bodyString());
				} catch (QiniuException ex2) {
					// ignore
				}
			}
		}

	}

	public static String uploadToQiNiuYun(String fileName, FileItem item) throws IOException {
		String retPath = "";

		// 构造一个带指定Zone对象的配置类
		Configuration cfg = new Configuration(Zone.zone1());
		// ...其他参数参考类注释
		// 华东 Zone.zone0()
		// 华北 Zone.zone1()
		// 华南 Zone.zone2()
		// 北美 Zone.zoneNa0()

		UploadManager uploadManager = new UploadManager(cfg);
		// ...生成上传凭证，然后准备上传
		// String accessKey = "nPVt7d0gCZP9CdLVjw6o";// 这里请替换成自己的AK
		// String secretKey = "p-GkW8RS4Y0_EffU";// 这里请替换成自己的SK
		// String bucket = "shouxiang";// 这里请替换成自己的bucket--空间名

		// 默认不指定key的情况下，以文件内容的hash值作为文件名
		// String key = null;

		InputStream inputStream = item.getInputStream();
		ByteArrayOutputStream swapStream = new ByteArrayOutputStream();
		byte[] buff = new byte[600]; // buff用于存放循环读取的临时数据
		int rc = 0;
		while ((rc = inputStream.read(buff, 0, 100)) > 0) {
			swapStream.write(buff, 0, rc);
		}
		byte[] uploadBytes = swapStream.toByteArray(); // uploadBytes
														// 为转换之后的结果
		Auth auth = Auth.create(ACCESS_KEY, SECRET_KEY);
		String upToken = auth.uploadToken(bucketName);
		System.out.println(ACCESS_KEY);
		System.out.println(SECRET_KEY);
		System.out.println(bucketName);
		System.out.println(upToken);
		try {
			Response response = uploadManager.put(uploadBytes, fileName, upToken);
			// 解析上传成功的结果
			DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
			retPath = putRet.key;
			System.out.println(putRet.key);
			System.out.println(putRet.hash);
		} catch (QiniuException ex) {
			Response r = ex.response;
			System.err.println(r.toString());
			try {
				System.err.println(r.bodyString());
			} catch (QiniuException ex2) {
				// ignore
			}
		}
		return retPath;
	}
}
