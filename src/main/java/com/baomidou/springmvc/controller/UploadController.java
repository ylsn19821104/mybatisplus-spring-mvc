package com.baomidou.springmvc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/upload")
public class UploadController {

    @RequestMapping("/")
    public ModelAndView index(ModelAndView modelAndView) {
        modelAndView.setViewName("upload");
        return modelAndView;
    }
}
