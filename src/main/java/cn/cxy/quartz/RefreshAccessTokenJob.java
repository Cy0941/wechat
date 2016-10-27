package cn.cxy.quartz;

import cn.cxy.entity.AccessToken;
import cn.cxy.util.JsonUtil;
import cn.cxy.value.InitConfig;
import cn.cxy.value.WeChatUrl;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by lenovo on 2016/10/26.
 */
public class RefreshAccessTokenJob extends QuartzJobBean {

    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        String format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        System.err.println("-------开始获取普通Accesstoken："+ format +"-------");
        try {
            CloseableHttpClient httpClient = HttpClients.createDefault();
            String uri = WeChatUrl.NORMAL_ACCESS_TOKEN_URL.replace("APPID", InitConfig.APP_ID).replace("APPSECRET",InitConfig.APP_SECRET);
            HttpGet httpGet = new HttpGet(uri);
            CloseableHttpResponse response = httpClient.execute(httpGet);
            int code = response.getStatusLine().getStatusCode();
            if (code == 200){
                HttpEntity entity = response.getEntity();
                String s = EntityUtils.toString(entity);
                AccessToken accessToken = JsonUtil.deserialize(s, AccessToken.class);
                System.err.println(accessToken);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("-------获取普通Accesstoken结束-------");
    }
}
