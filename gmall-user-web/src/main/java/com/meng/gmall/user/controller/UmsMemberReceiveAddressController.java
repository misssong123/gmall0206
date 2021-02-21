package com.meng.gmall.user.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.meng.gmall.bean.UmsMemberReceiveAddress;
import com.meng.gmall.service.UserMemberReceiveAddressService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class UmsMemberReceiveAddressController {
    @Reference
    UserMemberReceiveAddressService userMemberReceiveAddressService;

    @RequestMapping("getReceiveAddressByMemberId")
    @ResponseBody
    public List<UmsMemberReceiveAddress> getReceiveAddressByMemberId(String memberId) {
        List<UmsMemberReceiveAddress> umsMemberReceiveAddresses = userMemberReceiveAddressService.getReceiveAddressByMemberId(memberId);
        return umsMemberReceiveAddresses;
    }
}
