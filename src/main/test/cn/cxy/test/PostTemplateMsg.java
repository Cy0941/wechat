package cn.cxy.test;

import cn.cxy.entity.ModelTemplateMsg;
import cn.cxy.entity.TemplateMsg;
import cn.cxy.util.MsgUtils;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by lenovo on 2016/11/3.
 */
public class PostTemplateMsg {

    /**
     * TODO {"errcode":41005,"errmsg":"media data missing hint"}
     */
    @Test
    public void postTemplateMsg(){
        TemplateMsg templateMsg = new TemplateMsg();
        templateMsg.setTouser("oHeSqv24-IsvgByZ6K1LMXf6jmbI");//发送对象的OpenId
        templateMsg.setTemplate_id("czAAW45Xe7jrxZyXmIoRLJ8O6S0DqYiLHZjIAQKAYJM");//模板消息ID
        templateMsg.setUrl("http://15a9142o39.51mypc.cn");
        Map<String,Object> data = new HashMap<String, Object>();
//        data.put("num",new ModelTemplateMsg("321","#173177"));
        data.put("num",new ModelTemplateMsg("5,000,000","#173177"));
        data.put("data",new ModelTemplateMsg("2016-12-30日前","#173177"));
        data.put("bank",new ModelTemplateMsg("HSBC","#173177"));
        data.put("end",new ModelTemplateMsg("6313","#173177"));
        data.put("expect",new ModelTemplateMsg("2017-01-05日前","#173177"));
        data.put("real",new ModelTemplateMsg("2017-01-03","#173177"));
        templateMsg.setData(data);
        MsgUtils.postTemplateMsg(templateMsg);
    }

}
