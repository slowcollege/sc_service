package com.slow.college.utils;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: yedawei
 * Date: 8/4/16
 * Time: 5:15 PM
 */
public class HttpHelper {

    public static String sendGet(final String url) {
        try(CloseableHttpClient httpclient = HttpClients.createDefault()){
            HttpGet httpget = new HttpGet(url);
            httpget.addHeader("accept", "*/*");
            httpget.addHeader("connection", "Keep-Alive");
            httpget.addHeader("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            ResponseHandler<String> responseHandler = new ResponseHandler<String>() {
                @Override
                public String handleResponse(HttpResponse response) throws ClientProtocolException, IOException {
                    int status = response.getStatusLine().getStatusCode();
                    HttpEntity entity = response.getEntity();
                    if (status >= 400) {
                        System.out.println("error:" + status + ":" + url);
                    }
                    return entity != null ? EntityUtils.toString(entity, "UTF-8") : null;
                }
            };
            return httpclient.execute(httpget, responseHandler);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    public static String sendPost(String url, String content) {
        try(CloseableHttpClient httpclient = HttpClients.createDefault()) {
            HttpPost post = new HttpPost(url);
            post.addHeader("accept", "*/*");
            post.addHeader("connection", "Keep-Alive");
            post.addHeader("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            post.setEntity(new StringEntity(content, "UTF-8"));
            try(CloseableHttpResponse response = httpclient.execute(post)) {
                return EntityUtils.toString(response.getEntity());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
