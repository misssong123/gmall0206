package com.meng.gmall.user.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.meng.gmall.bean.UmsMember;
import com.meng.gmall.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class UserController {
    @ResponseBody
    @RequestMapping("index")

    public String index() {
        return "hello world";
    }

    @Reference
    UserService userService;

    @RequestMapping("getAllUser")
    @ResponseBody
    public List<UmsMember> getAllUser() {
        List<UmsMember> umsMembers = userService.getAllUser();
        return umsMembers;
    }
}
