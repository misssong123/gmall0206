package com.meng.gmall.user.controller;

import com.meng.gmall.user.beans.UmsMember;
import com.meng.gmall.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class UserController {
    @ResponseBody
    @RequestMapping("index")
    public String index(){
        return "hello world";
    }
    @Autowired
    UserService userService;



    @RequestMapping("getAllUser")
    @ResponseBody
    public List<UmsMember> getAllUser(){
        List<UmsMember> umsMembers = userService.getAllUser();
        return umsMembers;
    }
}
