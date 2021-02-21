package com.meng.gmall.service;

import com.meng.gmall.bean.UmsMemberReceiveAddress;

import java.util.List;

public interface UserMemberReceiveAddressService {
    List<UmsMemberReceiveAddress> getReceiveAddressByMemberId(String memberId);
}
