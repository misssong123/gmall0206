package com.meng.gmall.user.controller;

import com.meng.gmall.user.beans.UmsMemberReceiveAddress;
import com.meng.gmall.user.service.UserMemberReceiveAddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class UmsMemberReceiveAddressController {
    @Autowired
    UserMemberReceiveAddressService userMemberReceiveAddressService;

    @RequestMapping("getReceiveAddressByMemberId")
    @ResponseBody
    public List<UmsMemberReceiveAddress> getReceiveAddressByMemberId(String memberId){
        List<UmsMemberReceiveAddress> umsMemberReceiveAddresses = userMemberReceiveAddressService.getReceiveAddressByMemberId(memberId);
        return umsMemberReceiveAddresses;
    }
}
