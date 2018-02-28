package com.baomidou.springmvc.controller;

import com.baomidou.springmvc.service.system.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;


//@ControllerAdvice
public class HahaResponseBodyAdvice implements ResponseBodyAdvice<String> {
    private IUserService userService;

    public HahaResponseBodyAdvice() {
    }

    @Autowired
    public HahaResponseBodyAdvice(IUserService userService) {
        this.userService = userService;
    }

    @ModelAttribute
    public void getAllUser(Model model) {
        model.addAttribute("userList", userService.selectList(null));
    }

    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        return true;
    }

    @Override
    public String beforeBodyWrite(String body, MethodParameter returnType, MediaType selectedContentType, Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
        return body + "<br/> haha,this is been modified";
    }
}
