package cn.cxy.filter;

import cn.cxy.value.Constants;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.*;
import javax.servlet.Filter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;

/**
 * Created by lenovo on 2016/11/16.
 */

public class WechatFilter implements Filter {

    private static final String USER_AGENT = "User-Agent";

    private static final String REQUEST_FROM = "micromessage";

    private static final String CODE = "code";

    private static final String STATE = "state";

    public void init(FilterConfig filterConfig) throws ServletException {
        System.err.println("-------filter init-------");
    }

    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        System.out.println("------do filter-----");
        HttpServletRequest request = (HttpServletRequest)servletRequest;
        HttpServletResponse response = (HttpServletResponse)servletResponse;
        String agent = request.getParameter(USER_AGENT);
        if (StringUtils.indexOfIgnoreCase(agent, REQUEST_FROM) > 0){
            //微信访问
            String code = request.getParameter(CODE);
            String state = request.getParameter(STATE);
            if (StringUtils.isNotBlank(code) && StringUtils.isNotBlank(state) && Constants.STATE.equals(state)){
                //已授权
                //TODO 获取用户信息及处理
            }else {
                //未授权
                String requestUrl = request.getRequestURI();
                String queryString = request.getQueryString();
                if (StringUtils.isNotBlank(queryString)){
                    requestUrl = requestUrl + "?" + queryString;
                }
                String url = Constants.OAUTH_URL.replace("APPID",Constants.APP_ID)
                                                .replace("REDIRECT_URI", URLEncoder.encode(requestUrl,"utf-8"))
                                                .replace("SCOPE","snsapi_base")
                                                .replace("STATE",Constants.STATE);
                response.sendRedirect(url);
                return;
            }
        }
        filterChain.doFilter(request,response);
    }

    public void destroy() {
        System.err.println("-------filter desotry-------");
    }
}
