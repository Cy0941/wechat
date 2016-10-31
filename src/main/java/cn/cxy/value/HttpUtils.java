package cn.cxy.value;

import cn.cxy.entity.AccessToken;
import cn.cxy.util.JsonUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

/**
 * Created by lenovo on 2016/10/31.
 */
public class HttpUtils {

    /**
     * 发送Http Get 请求
     * @param fullUrl
     * @return
     */
    public static AccessToken httpGet(String fullUrl){
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
