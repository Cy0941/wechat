package cn.cxy.util;

import org.springframework.core.io.support.PropertiesLoaderUtils;

import java.io.IOException;
import java.util.Properties;

/**
 * Created by lenovo on 2016/10/31.
 */
public class PropUtils {

    private static Properties sysProperties;

    static {
        try {
            sysProperties = PropertiesLoaderUtils.loadAllProperties("/system.properties");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String getProperty(String key){
        return sysProperties.getProperty(key);
    }

}
