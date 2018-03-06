package com.baomidou.springmvc.listener;


import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class MyServletContextListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        System.err.println("MyServletContextListener,ServletContext初始化");
        System.err.println("MyServletContextListener," + sce.getServletContext().getServerInfo());
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        System.err.println("MyServletContextListener,ServletContext销毁");
    }
}
