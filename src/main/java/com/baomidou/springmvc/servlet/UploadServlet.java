package com.baomidou.springmvc.servlet;


import org.apache.commons.io.FileUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collection;

@WebServlet(name = "uploadServlet", urlPatterns = "/uploadServlet")
@MultipartConfig
public class UploadServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");
        resp.setCharacterEncoding("utf-8");
        resp.setContentType("text/html;charset=utf-8");
        String savePath = req.getServletContext().getRealPath("/WEB-INF/uploadFile");
        Collection<Part> parts = req.getParts();
        if (parts.size() == 1) {
            //通过表单file控件(<input type="file" name="file">)的名字直接获取Part对象
            //Servlet3没有提供直接获取文件名的方法,需要从请求头中解析出来
            //Content-Disposition: form-data; name="file"; filename="3.rar"
            //Content-Type: application/octet-stream
            Part part = req.getPart("file");
            copyFile(savePath, part);
        } else {
            for (Part part : parts) {
                copyFile(savePath, part);
            }
        }

        PrintWriter out = resp.getWriter();
        out.println("上传成功");
        out.flush();
        out.close();
    }

    private void copyFile(String savePath, Part part) throws IOException {
        //Content-Disposition: form-data; name="file"; filename="3.rar"
        //Content-Type: application/octet-stream
        String header = part.getHeader("content-disposition");
        String fileName = getFileName(header);
        String copyTo = savePath + File.separator + fileName;
        FileUtils.copyInputStreamToFile(part.getInputStream(), new File(copyTo));
    }

    private String getFileName(String disposition) {
        //Content-Disposition: form-data; name="file"; filename="3.rar"
        String[] tmp1 = disposition.split(";");//filename="3.rar"
        String[] tmp2 = tmp1[2].split("=");
        //获取文件名，兼容各种浏览器的写法
        String fileName = tmp2[1].substring(tmp2[1].lastIndexOf("\\") + 1).replaceAll("\"", "");
        return fileName;
    }
}
