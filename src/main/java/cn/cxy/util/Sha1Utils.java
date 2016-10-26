package cn.cxy.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by lenovo on 2016/10/19.
 */
public class Sha1Utils {

    /**
     * 对字符串进行sha1加密
     * @param str
     * @return
     */
    public static String sha1(String str){
//        1）将token、timestamp、nonce三个参数进行字典序排序
//        2）将三个参数字符串拼接成一个字符串进行sha1加密
//        3）开发者获得加密后的字符串可与signature对比，标识该请求来源于微信
        try {
            StringBuilder sb = new StringBuilder();
            MessageDigest md = MessageDigest.getInstance("sha1");
            md.update(str.getBytes());
            byte[] digest = md.digest();//得到的为十进制数
            //TODO 转换为十六进制并进行连接即可
            for (byte b : digest){
                sb.append(String.format("%02x",b));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }
}
