package cn.cxy.spring;

import cn.cxy.thread.AccessTokenThread;
import cn.cxy.value.Constants;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Configuration;

/**
 * Created by lenovo on 2016/10/31.
 */

@Configuration
public class ApplicationContextHelper implements ApplicationContextAware {

    protected ApplicationContext context;

    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.context = applicationContext;
        AccessTokenThread.app_id = Constants.APP_ID;
        AccessTokenThread.app_secret = Constants.APP_TOKEN;
        //启动线程获取普通accessToken
//        new Thread(new AccessTokenThread()).start();
    }

}
