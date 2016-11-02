package cn.cxy.thread;

import cn.cxy.entity.AccessToken;
import cn.cxy.util.HttpUtils;
import cn.cxy.value.WeChatUrl;

/**
 * Created by lenovo on 2016/10/31.
 */
public class AccessTokenThread implements Runnable{

    public static String app_id = "";
    public static String app_secret = "";

    public static AccessToken access_token;

    public void run() {
        while (true){
            try {
                String fullUrl = WeChatUrl.NORMAL_ACCESS_TOKEN_URL.replace("APPID",app_id).replace("APPSECRET",app_secret);
                access_token = HttpUtils.httpGet(fullUrl);
                if (null != access_token){
                    System.err.println("获取到的token为："+access_token.getAccess_token());
                    //获取到accessToken后线程休眠7000秒
                    Thread.sleep((Long.valueOf(access_token.getExpires_in())-200)*1000);
                }else {
                    Thread.sleep(60*1000);
                }
            } catch (Exception e) {
                try {
                    Thread.sleep(60*1000);
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                }
                e.printStackTrace();
            }
        }
    }
}
