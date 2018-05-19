package com.example.demo.controller;

import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.util.Map;


@Controller
public class LoginController
{
    @RequestMapping(value = "/login",method = RequestMethod.GET)
    public ModelAndView login(Map<String,Object> map){
        return new ModelAndView("userlogin");
    }

    @RequestMapping(value = "/",method = RequestMethod.GET)
    public ModelAndView index(Map<String,Object> map){
        return new ModelAndView("index");
    }
}





