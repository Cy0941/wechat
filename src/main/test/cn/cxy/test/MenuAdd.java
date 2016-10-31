package cn.cxy.test;

import cn.cxy.util.JsonUtils;
import cn.cxy.value.Constants;
import cn.cxy.value.WeChatMenu;
import cn.cxy.value.WeChatUrl;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by lenovo on 2016/10/31.
 */
public class MenuAdd {

    @Test
    public void addMenu(){
        try {
            List<WeChatMenu> pMenus = new ArrayList<WeChatMenu>();
            WeChatMenu menu1 = new WeChatMenu();
            menu1.setId(1);
            menu1.setName("第一菜单");
            menu1.setType("view");
            menu1.setUrl("http://15a9142o39.51mypc.cn");
            pMenus.add(menu1);

            WeChatMenu menu2 = new WeChatMenu();
            menu2.setId(2);
            menu2.setName("第二菜单");

            List<WeChatMenu> cMenus = new ArrayList<WeChatMenu>();
            menu1 = new WeChatMenu();
            menu1.setPid(2);
            menu1.setName("扫码带提示");
            menu1.setType("scancode_waitmsg");
            menu1.setKey("a10010");
            cMenus.add(menu1);
            menu1 = new WeChatMenu();
            menu1.setPid(2);
            menu1.setName("调用系统相机");
            menu1.setType("pic_sysphoto");
            menu1.setKey("a10011");
            cMenus.add(menu1);
            menu2.setSub_button(cMenus);
            pMenus.add(menu2);

            HashMap<String,Object> map = new HashMap<String, Object>();
            map.put("button",pMenus);

            String json = JsonUtils.serialize(map);

            System.out.println(json);


            HttpClient client = HttpClients.createDefault();
            String fullUrl = WeChatUrl.MENU_ADD.replace("ACCESS_TOKEN",Constants.INSTANT_ACCESS_TOKEN);
            HttpPost httpPost = new HttpPost(fullUrl);
            httpPost.setHeader("Content-Type","application/json");//设置请求头信息
            StringEntity entity = new StringEntity(json, ContentType.create("application/json","utf-8"));
            httpPost.setEntity(entity);//请求参数信息及格式
            HttpResponse response = client.execute(httpPost);
            int code = response.getStatusLine().getStatusCode();
            if (code == 200){
                System.err.println("-----菜单创建成功------");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
