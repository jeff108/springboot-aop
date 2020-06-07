package com.king.springbootaop.controller;


import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @RequestMapping("doBefore")
    public String doBeore(Integer age, String name) {
        //int ex = 1 / 0;
        return "hello " + name + ",your age is " + age;
    }
    @RequestMapping("err")
    public String error(){
        return "糟糕，出错了！！";
    }
}
