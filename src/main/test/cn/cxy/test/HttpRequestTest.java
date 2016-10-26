package cn.cxy.test;

import cn.cxy.entity.AccessToken;
import cn.cxy.util.JsonUtil;
import cn.cxy.value.InitConfig;
import cn.cxy.value.WeChatUrl;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.junit.Test;

import java.io.IOException;

/**
 * Created by lenovo on 2016/10/26.
 */

public class HttpRequestTest {


    /**
     *
     */
    @Test
    public void getAccessToken(){
        try {
            String uri = WeChatUrl.NORMAL_ACCESS_TOKEN_URL.replace("APPID", InitConfig.APP_ID).replace("APPSECRET",InitConfig.APP_SECRET);
            HttpClient client = HttpClients.createDefault();
            HttpGet httpGet = new HttpGet(uri);
            HttpResponse response = client.execute(httpGet);
            int code = response.getStatusLine().getStatusCode();
            if (code >= 200 && code < 300){
                HttpEntity entity = response.getEntity();
                String json = EntityUtils.toString(entity);
                AccessToken accessToken = JsonUtil.deserialize(json, AccessToken.class);
                System.out.println(accessToken);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 通过HttpClient发起Http的Get或Post请求
     */
    @Test
    public void httpGet(){
        try {
            //请求主体
            CloseableHttpClient client = HttpClients.createDefault();
            //具体Get请求及请求地址
            HttpGet httpGet = new HttpGet("https://www.baidu.com?wd=你好吗");
            //得到请求结果
            CloseableHttpResponse response = client.execute(httpGet);
            int code = response.getStatusLine().getStatusCode();
            //通过返回码进行判断
            if (code >= 200 && code < 300){
                //获取返回的网页信息
                HttpEntity httpEntity = response.getEntity();
                String s = EntityUtils.toString(httpEntity);
                System.out.println(s);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
