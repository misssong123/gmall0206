package com.meng.gmall.user.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.meng.gmall.bean.UmsMemberReceiveAddress;
import com.meng.gmall.service.UserMemberReceiveAddressService;
import com.meng.gmall.user.mapper.UmsMemberReceiveAddressMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Service
public class UserMemberReceiveAddressServiceImpl implements UserMemberReceiveAddressService {
    @Autowired
    UmsMemberReceiveAddressMapper umsMemberReceiveAddressMapper;
    @Override
    public List<UmsMemberReceiveAddress> getReceiveAddressByMemberId(String memberId) {
        UmsMemberReceiveAddress demo = new UmsMemberReceiveAddress();
        demo.setMemberId(memberId);
        List<UmsMemberReceiveAddress> umsMemberReceiveAddresses = umsMemberReceiveAddressMapper.select(demo);
        return umsMemberReceiveAddresses;
    }
}
