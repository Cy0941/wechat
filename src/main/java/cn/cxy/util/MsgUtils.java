package cn.cxy.util;

import cn.cxy.entity.InstantImage;
import cn.cxy.entity.TemplateMsg;
import cn.cxy.value.Constants;
import cn.cxy.value.WeChatMsgTypes;
import cn.cxy.value.WeChatUrl;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.XMLWriter;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.util.*;

/**
 * Created by lenovo on 2016/11/2.
 */
public class MsgUtils {

    //预设消息
    static Map<String,String> defaultMsgs = new HashMap<String, String>();
    static {
        defaultMsgs.put("abc","123");
        defaultMsgs.put("hello","hello");
        defaultMsgs.put("你好","你好吗");
    }


    /**
     * post发送模板消息
     * @return
     */
    public static String postTemplateMsg(TemplateMsg msg){
        CloseableHttpResponse response = null;
        CloseableHttpClient client = null;
        try {
            client = HttpClients.createDefault();
            String url = WeChatUrl.POST_TEMPLATE.replace("ACCESS_TOKEN",Constants.INSTANT_ACCESS_TOKEN);
            HttpPost httpPost = new HttpPost(url);
            String json = JsonUtils.serialize(msg);
            StringEntity stringEntity = new StringEntity(json, ContentType.create("application/json","utf-8"));
            httpPost.setHeader("Content-Type","application/json");
            httpPost.setEntity(stringEntity);
            response = client.execute(httpPost);
            int code = response.getStatusLine().getStatusCode();
            if (code == 200){
                HttpEntity entity = response.getEntity();
                String s = EntityUtils.toString(entity);
                System.err.println(s);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                if (response != null){
                    response.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                if (client != null){
                    client.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }


    /**
     * 将需要回复的xml消息组装为map集合
     * @param msgMap
     * @return
     */
    public static String msgHandle(Map<String,String> msgMap) throws IOException {
        Map<String,String> xmlMap = new HashMap<String, String>();
        xmlMap.put("ToUserName",msgMap.get("FromUserName"));
        xmlMap.put("FromUserName",msgMap.get("ToUserName"));
        xmlMap.put("CreateTime",new Date().getTime()+"");
        String defaultMsg = "暂不支持此消息回复！";
        String s = msgMap.get("Content");
        if (WeChatMsgTypes.MSG_TEXT.equals(msgMap.get("MsgType"))){
            if (defaultMsgs.containsKey(s)){
                defaultMsg = defaultMsgs.get(s);
            }
            xmlMap.put("MsgType",WeChatMsgTypes.MSG_TEXT);
            xmlMap.put("Content",defaultMsg);
        }else if (WeChatMsgTypes.MSG_IAMGE.equals(msgMap.get("MsgType"))){
            xmlMap.put("MsgType",WeChatMsgTypes.MSG_IAMGE);
            defaultMsg = "<MediaId>ovHyNqiKNU43udNUj6W9iCGT5YvxrXGS19UycNnn1Cdbb4SL36cqrsZo_PeQo-84</MediaId>";
            xmlMap.put("Image",defaultMsg);
        }
        return map2Xml(xmlMap);
    }

    /**
     * 将map集合转为xml
     * @param map
     * @return
     */
    private static String map2Xml(Map<String,String> map) throws IOException {
        Document document = DocumentHelper.createDocument();
        Element root = document.addElement("xml");
        Set<String> keySet = map.keySet();
        for (String k : keySet){
            root.addElement(k).addText(map.get(k));
        }
        StringWriter writer = new StringWriter();
        XMLWriter xw = new XMLWriter(writer);
        xw.setEscapeText(false);//TODO 防止生成xml是转换 < 与 >
        xw.write(document);
//        document.write(writer);
        System.err.println(writer.toString());
        return writer.toString();
    }


    /**
     * request 转 map
     * @param request
     * @return
     */
    public static Map<String,String> req2Map(HttpServletRequest request) throws IOException {
        Map<String,String> result = new HashMap<String, String>();
        String string = xml2String(request);
        try {
            //转换为 Doument
            Document document = DocumentHelper.parseText(string);
            //获取根元素
            Element rootElement = document.getRootElement();
            //获取所有子元素
            List<Element> elements = rootElement.elements();
            for (Element e : elements){
                result.put(e.getName(),e.getTextTrim());
            }
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * post上传图片临时文件获得media_id
     * @param path
     * @param type
     */
    public static void handleImage(String path,String type){
        CloseableHttpClient client = null;
        CloseableHttpResponse response = null;
        try {
            client = HttpClients.createDefault();
            String url = WeChatUrl.POST_IMAGE.replace("ACCESS_TOKEN", Constants.INSTANT_ACCESS_TOKEN).replace("TYPE", type);
            HttpPost httpPost = new HttpPost(url);
            //TODO POST表单处理待上传的文件信息
            FileBody fileBody = new FileBody(new File(path));
            HttpEntity httpEntity = MultipartEntityBuilder.create().addPart("media", fileBody).build();
            httpPost.setEntity(httpEntity);

            response = client.execute(httpPost);
            int code = response.getStatusLine().getStatusCode();
            if (200 == code){
                HttpEntity entity = response.getEntity();
                String s = EntityUtils.toString(entity);
                InstantImage image = JsonUtils.deserialize(s, InstantImage.class);
                System.err.println(image);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                if (client != null){
                    client.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                if (response != null){
                    response.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    private static String xml2String(HttpServletRequest request) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(request.getInputStream()));
        String str;
        StringBuilder stringBuilder = new StringBuilder();
        while ((str = br.readLine()) != null){
            stringBuilder.append(str);
        }
        return stringBuilder.toString();
    }
}
