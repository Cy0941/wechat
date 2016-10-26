import cn.cxy.util.Sha1Utils;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

import java.util.Date;

/**
 * Created by lenovo on 2016/10/25.
 */
public class SHA1Test {

    @Test
    public void sha1(){
        System.out.println(Sha1Utils.sha1("hello"));

//        byte a  = -10;
//        System.out.println(Integer.toHexString(a));
//        System.out.println(String.format("%02x",a));
        System.out.println(Integer.toHexString(-10));//Integer.toHexString对负数处理有误
    }

    /**
     * String.format测试
     */
    @Test
    public void stringFormat(){
        System.out.println(String.format("我叫%s","小米"));
        System.err.println(String.format("我叫%2$s，他叫%1$s","小米","迅雷"));

        System.out.println(String.format("%o",8));
        System.err.println(String.format("%x",16));

        System.out.println(String.format("%1$,d",1234563453));
        System.err.println(String.format("%1$08d",123456) +" : "+ StringUtils.leftPad("123456",3,"0"));

        System.out.println(String.format("%1$.2f",12.555555));
        System.err.println(String.format("%1$tY-%1$tm-%1$te",new Date()));
    }
}
