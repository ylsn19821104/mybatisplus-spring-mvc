package com.baomidou.springmvc.common;

import org.springframework.web.servlet.HandlerExecutionChain;
import org.springframework.web.servlet.HandlerMapping;

import javax.servlet.http.HttpServletRequest;

public class DiguaHandlerMapping implements HandlerMapping {
    @Override
    public HandlerExecutionChain getHandler(HttpServletRequest request) throws Exception {
        String url = request.getRequestURI().toString();
        String method = request.getMethod();
        if ("url".startsWith("/tudou")) {
            if ("GET".equals(method)) {

            } else if ("POST".equals(method)) {

            }
        }
        return null;
    }
}
