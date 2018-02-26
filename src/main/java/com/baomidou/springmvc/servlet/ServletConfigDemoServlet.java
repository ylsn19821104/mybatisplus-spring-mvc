package com.baomidou.springmvc.servlet;

import javax.servlet.*;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(
        name = "ServletConfigDemoServlet",
        urlPatterns = "/servletConfigDemoServlet",
        initParams = {
                @WebInitParam(name = "admin", value = "hongxp"),
                @WebInitParam(name = "email", value = "admin@example.com")
        })
public class ServletConfigDemoServlet implements Servlet {
    private transient ServletConfig servletConfig;

    @Override
    public void init(ServletConfig config) throws ServletException {
        this.servletConfig = servletConfig;
    }

    @Override
    public ServletConfig getServletConfig() {
        return this.servletConfig;
    }

    @Override
    public void service(ServletRequest req, ServletResponse res) throws ServletException, IOException {
        ServletConfig servletConfig = getServletConfig();
        String admin = servletConfig.getInitParameter("admin");
        String email = servletConfig.getInitParameter("email");
        res.setContentType("text/html");
        PrintWriter writer = res.getWriter();
        writer.print("<html><head></head><body>"
                + "admin:" + admin
                + "<br/>email:" + email
                + "<br/></body>"
                + "</html>");
    }

    @Override
    public String getServletInfo() {
        return "ServletConfig DemoServlet";
    }

    @Override
    public void destroy() {

    }
}
