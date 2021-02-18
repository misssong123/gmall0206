package com.meng.gmall.user.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.meng.gmall.bean.UmsMember;
import com.meng.gmall.service.UserService;
import com.meng.gmall.user.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserMapper userMapper;
    @Override
    public List<UmsMember> getAllUser() {
        List<UmsMember> umsMembers = userMapper.selectAll();
        return umsMembers;
    }
}
