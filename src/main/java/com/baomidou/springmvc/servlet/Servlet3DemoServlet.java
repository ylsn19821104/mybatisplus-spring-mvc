package com.baomidou.springmvc.servlet;

import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.servlet.FrameworkServlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(
        name = "Servlet3Demo", urlPatterns = "/servlet3DemoServlet",
        displayName = "Servlet3Demo",
        description = "Servlet3.0 Demo",
        initParams = {
                @WebInitParam(name = "admin", value = "sky"),
                @WebInitParam(name = "email", value = "admin@example.com")
        })
public class Servlet3DemoServlet extends HttpServlet  {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        WebApplicationContext currentWebApplicationContext = ContextLoader.getCurrentWebApplicationContext();
        System.err.println(currentWebApplicationContext.getApplicationName());

        WebApplicationContext webApplicationContext = WebApplicationContextUtils.getWebApplicationContext(req.getServletContext());
        System.err.println(webApplicationContext.getApplicationName());

        String attrName = FrameworkServlet.class.getName() + ".CONTEXT." + "SpringMVC";
        WebApplicationContext springMVC = (WebApplicationContext) req.getServletContext().getAttribute(attrName);
        System.err.println(springMVC.getId());
        resp.getWriter().write(springMVC.getId());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doGet(req, resp);
    }
}
