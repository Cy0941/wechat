package cn.cxy.util;

import cn.cxy.value.WeChatMsgTypes;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringWriter;
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
     * 将需要回复的xml消息组装为map集合
     * @param msgMap
     * @return
     */
    public static String msgHandle(Map<String,String> msgMap) throws IOException {
        Map<String,String> xmlMap = new HashMap<String, String>();
        xmlMap.put("ToUserName",msgMap.get("FromUserName"));
        xmlMap.put("FromUserName",msgMap.get("ToUserName"));
        xmlMap.put("CreateTime",new Date().getTime()+"");
        xmlMap.put("MsgType",WeChatMsgTypes.MSG_TEXT);
        String defaultMsg = "暂不支持此消息回复！";
        String s = msgMap.get("Content");
        if (WeChatMsgTypes.MSG_TEXT.equals(msgMap.get("MsgType")) && defaultMsgs.containsKey(s)){
            defaultMsg = defaultMsgs.get(s);
        }
        xmlMap.put("Content",defaultMsg);
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
        document.write(writer);
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
