package com.meng.gmall.user.service;

import com.meng.gmall.user.beans.UmsMemberReceiveAddress;

import java.util.List;

public interface UserMemberReceiveAddressService {
    List<UmsMemberReceiveAddress> getReceiveAddressByMemberId(String memberId);
}
