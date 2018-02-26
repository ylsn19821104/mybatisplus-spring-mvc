package com.baomidou.springmvc.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;

@WebFilter(filterName = "Servlet3Filter", urlPatterns = "/servlet3Demo")
public class Servlet3Filter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        System.out.println("Servlet3Filter:过滤器初始化");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        System.out.println("Servlet3Filter:过滤器执行");
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {

        System.out.println("Servlet3Filter:过滤器销毁");
    }
}
