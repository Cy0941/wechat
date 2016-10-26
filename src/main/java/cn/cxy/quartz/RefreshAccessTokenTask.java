package cn.cxy.quartz;

import org.springframework.stereotype.Component;

/**
 * Created by lenovo on 2016/10/26.
 */

@Deprecated
@Component
public class RefreshAccessTokenTask {

    public void refreshAccessToken(){
        System.out.println("----refresh access token without extends QuartzJobBean-----");
    }
}
