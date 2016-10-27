package cn.cxy.value;

import lombok.Data;

/**
 * Created by lenovo on 2016/10/27.
 */

@Data
public class WeixinContext {

    private static String access_token;

    public static String getAccesstoken(){
        return access_token;
    }

    public static void setAccesstoken(String accesstoken){
        access_token = accesstoken;
    }

}
