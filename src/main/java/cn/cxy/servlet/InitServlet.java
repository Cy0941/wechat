package cn.cxy.servlet;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

/**
 * Created by lenovo on 2016/10/27.
 */
public class InitServlet extends HttpServlet {

    private static WebApplicationContext context;
    private static String realPath;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        context = WebApplicationContextUtils.getWebApplicationContext(this.getServletContext());
        BeanFactoryContext.setContext(context);
    }
}
