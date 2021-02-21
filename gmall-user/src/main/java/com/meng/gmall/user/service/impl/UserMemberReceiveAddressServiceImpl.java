package com.meng.gmall.user.service.impl;

import com.meng.gmall.user.beans.UmsMemberReceiveAddress;
import com.meng.gmall.user.mapper.UmsMemberReceiveAddressMapper;
import com.meng.gmall.user.service.UserMemberReceiveAddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
