package com.kever.web;

import com.kever.web.bean.Handle;
import com.kever.web.help.ConfigHelp;
import com.kever.web.help.ControllerHelp;
import com.kever.web.help.HelperLoader;
import com.kever.web.util.CodeUtil;
import com.kever.web.util.StreamUtil;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.*;

@WebServlet(urlPatterns = "/*", loadOnStartup = 0)
public class DispatcherServlet extends HttpServlet {
    @Override
    public void init(ServletConfig config) throws ServletException {
        //初始化相关help类
        HelperLoader.init();
        //获取servletContext对象（用于注册servlet）
        ServletContext servletContext = config.getServletContext();
        //注册处理jsp的servlet
        ServletRegistration jspServlet = servletContext.getServletRegistration("jsp");
        jspServlet.addMapping(ConfigHelp.getAppJspPath() + "*");
        //注册处理静态资源的默认servlet
        ServletRegistration defaultServlet = servletContext.getServletRegistration("default");
        defaultServlet.addMapping(ConfigHelp.getJdbcAssertPath() + "*");
    }


    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String requestMethod = request.getMethod().toLowerCase();
        String requestPath = request.getPathInfo();
        //获取handle
        Handle handle = ControllerHelp.getHandle(requestMethod, requestPath);
        if (handle != null) {
            Class<?> controllerClass = handle.getControllerClass();
            Method actionMethod = handle.getActionMethod();
            //创建请求参数
            Map<String, Object> paramsMap = new HashMap<>();
            Enumeration<String> parameterNames = request.getParameterNames();
            while (parameterNames.hasMoreElements()) {
                String element = parameterNames.nextElement();
                String parameter = request.getParameter(element);
                paramsMap.put(element, parameter);
            }

            String body = CodeUtil.decodeUrl(StreamUtil.getString(request.getInputStream()));
            if (StringUtils.isNotEmpty(body)) {
                String[] params = StringUtils.split(body, "&");
                if (params != null && params.length > 0) {
                    for (String param : params) {
                        String[] keyValue = StringUtils.split(param, "=");
                        if (CollectionUtils.isNotEmpty(new ArrayList<Object>(Arrays.asList(keyValue))) && keyValue.length == 2) {
                            String key = keyValue[0];
                            String value = keyValue[1];
                            paramsMap.put(key, value);
                        }
                        }

                    }
                }
            }

        }
    }

