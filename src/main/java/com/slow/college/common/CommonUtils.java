package com.slow.college.common;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.slow.college.config.SlowCollegeConfig;
import com.slow.college.utils.NetUtils;
import com.slow.college.utils.QiniuUtil;

public class CommonUtils {

    private static final Logger log = LogManager.getLogger(CommonUtils.class);
    private static Gson gson = new GsonBuilder().disableHtmlEscaping().create();
    
    /**
     * 上传图片到七牛云
     */
    public static String alterBaseImg (HttpServletRequest request, String imageName) {
		log.info("开始上传图片");
		String response = null;
		try {
			String localPathDir = SlowCollegeConfig.qiniuMyUrl;
	        File logoSaveFile = new File(localPathDir);
	        if(!logoSaveFile.exists()){
	            logoSaveFile.mkdirs();
	        }
	        if (1 ==  1) {
	        	boolean isMultipart = ServletFileUpload.isMultipartContent(request);
				if (!isMultipart) {
					return null;
				}
	         	DiskFileItemFactory factory = new DiskFileItemFactory();
				ServletFileUpload upload = new ServletFileUpload(factory);
				List<?> items = upload.parseRequest(request);
				Iterator iter = items.iterator();
	            if (iter.hasNext()) {
	            	FileItem item = (FileItem) iter.next();
	                String oname = item.getFieldName();
	                String fileName = localPathDir + File.separator 
	                		+ imageName + oname;
	                InputStream inputstream =item.getInputStream();
	                byte[] bytes = new byte[1024];
	                FileOutputStream outstream = new FileOutputStream(fileName);
	                int index;
	                while ((index = inputstream.read(bytes)) != -1) {
	                    outstream.write(bytes, 0, index);
	                    outstream.flush();
	                }
	                outstream.close();
	                inputstream.close();

	                // 上传到七牛云
					String fileNames = SlowCollegeConfig.qiniuRequestUrlParam 
						+ imageName + oname;
					String uPath = localPathDir + imageName + oname;
					response = QiniuUtil.upload(uPath, fileNames);
	            } else {
	            	response = null;
	            }
	        } else {
	        	response = null;
	        }
		} catch (Exception e) {
			e.printStackTrace();
		}
		return response;
    }

    /**
     * 判定是http还是https
     */
    public static String checkHttpType(String param, String url) {
        String result = null;
        if (StringUtils.isNotBlank(url)) {
            if (url.indexOf("https:") > -1) {
                result = NetUtils.httpsPost(param, url);
            } else if (url.indexOf("http:") > -1) {
                result = NetUtils.doHttpPost2(param, url);
            }
        }
        return result;
    }
    
}
