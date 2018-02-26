package com.baomidou.springmvc.servlet;

import lombok.Data;
import org.springframework.web.servlet.HttpServletBean;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "TestHttpServletBean", urlPatterns = "/testHttpServletBean", initParams = {
        @WebInitParam(name = "userName", value = "hongxp"),
        @WebInitParam(name = "email", value = "admin@example.com")
})
@Data
public class TestHttpServletBean extends HttpServletBean {
    private String userName;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter out = resp.getWriter();
        out.println(userName);
        out.flush();
        out.close();
    }
}
