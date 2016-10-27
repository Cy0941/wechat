package cn.cxy.servlet;

import lombok.Data;
import org.springframework.web.context.WebApplicationContext;

/**
 * Created by lenovo on 2016/10/27.
 */

@Data
public class BeanFactoryContext {

    private static WebApplicationContext context;

    public static Object getService(String serviceName){
        return context.getBean(serviceName);
    }

    public static void setContext(WebApplicationContext wac){
        context = wac;
    }
}
