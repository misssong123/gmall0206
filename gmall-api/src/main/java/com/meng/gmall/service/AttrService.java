package com.meng.gmall.service;

import com.meng.gmall.bean.PmsBaseAttrInfo;
import com.meng.gmall.bean.PmsBaseAttrValue;

import java.util.List;

public interface AttrService {
    List<PmsBaseAttrInfo> attrInfoList(String catalog3Id);

    void saveInfo(PmsBaseAttrInfo pmsBaseAttrInfo);

    List<PmsBaseAttrValue> getAttrValueList(String attrId);
}
