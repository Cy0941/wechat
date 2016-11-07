package cn.cxy.util;

import cn.cxy.entity.AccessToken;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

/**
 * Created by lenovo on 2016/10/31.
 */
public class HttpUtils {

    /**
     * get请求
     * @return
     */
    public static String httpGet(String url){
        CloseableHttpClient httpClient = null;
        CloseableHttpResponse response = null;
        String result = "";
        try {
            httpClient = HttpClients.createDefault();
            HttpGet httpGet = new HttpGet(url);
            response = httpClient.execute(httpGet);
            int code = response.getStatusLine().getStatusCode();
            if (code == 200){
                HttpEntity entity = response.getEntity();
                result = EntityUtils.toString(entity);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                if (response != null){
                    response.close();
                }
                if (httpClient != null){
                    httpClient.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    /**
     * post请求
     * @return
     */
    public static String httpPost(String data,String url){
        CloseableHttpClient httpClient = null;
        CloseableHttpResponse response = null;
        String result = "";

        try {
            httpClient = HttpClients.createDefault();
            HttpPost httpPost = new HttpPost(url);
            StringEntity entity = new StringEntity(data, ContentType.create("application/json","utf-8"));
            httpPost.setHeader("Content-Type","application/json");
            httpPost.setEntity(entity);
            response = httpClient.execute(httpPost);
            int code = response.getStatusLine().getStatusCode();
            if (code == 200){
                HttpEntity responseEntity = response.getEntity();
                result = EntityUtils.toString(responseEntity);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 获取access_token
     * @param fullUrl
     * @return
     */
    public static AccessToken getAccessToken(String fullUrl){
        AccessToken accessToken = null;
        try {
            HttpClient client = HttpClients.createDefault();
            HttpGet httpGet = new HttpGet(fullUrl);
            HttpResponse response = client.execute(httpGet);
            int code = response.getStatusLine().getStatusCode();
            if (code == 200){
                HttpEntity entity = response.getEntity();
                String s = EntityUtils.toString(entity);
                accessToken = JsonUtils.deserialize(s,AccessToken.class);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return accessToken;
    }

}
