package cn.cxy.controller;

import cn.cxy.util.MsgUtils;
import cn.cxy.util.Sha1Utils;
import cn.cxy.value.Constants;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Map;

/**
 * Created by lenovo on 2016/10/19.
 */

@Controller
public class InitController {

    private static Logger logger = Logger.getLogger(InitController.class);

    /**
     * 微信接入
     * @param request
     * @param response
     * @throws IOException
     */
    @RequestMapping(value = "/init",method = RequestMethod.GET)
    public void initGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
//        1）将token、timestamp、nonce三个参数进行字典序排序
//        2）将三个参数字符串拼接成一个字符串进行sha1加密
//        3）开发者获得加密后的字符串可与signature对比，标识该请求来源于微信
        logger.info("-------微信校验-------");
        String signature = request.getParameter("signature");
        String timestamp = request.getParameter("timestamp");
        String echostr = request.getParameter("echostr");
        String nonce = request.getParameter("nonce");
        if (StringUtils.isNotBlank(echostr)){
            String[] arr = {Constants.TOKEN,timestamp,nonce};
            Arrays.sort(arr);//字典排序
            StringBuilder sb = new StringBuilder();
            for (String str : arr){
                sb.append(str);
            }
            String sha1 = Sha1Utils.sha1(sb.toString());
            if (sha1.equalsIgnoreCase(signature)){
                logger.info("----------"+sha1+"-----------");
                response.getWriter().println(echostr);
            }
        }
    }

    /**
     * 微信默认处理消息
     * @param request
     * @param response
     */
    @RequestMapping(name = "init",method = RequestMethod.POST)
    public void initPost(HttpServletRequest request,HttpServletResponse response){
        try {
            Map<String, String> map = MsgUtils.req2Map(request);
            String s = MsgUtils.msgHandle(map);
            //TODO 请求头部信息
            //使用merge tools 解决冲突
            //how to use the eclipse's egit - merge tools
            response.setContentType("application/xml;charset=UTF-8");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(s);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
