package com.meng.gmall.manager.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.meng.gmall.bean.PmsBaseAttrInfo;
import com.meng.gmall.manager.mapper.PmsBaseAttrInfoMapper;
import com.meng.gmall.manager.mapper.PmsBaseAttrValueMapper;
import com.meng.gmall.service.AttrService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
@Service
public class AttrServiceImpl implements AttrService {
    @Autowired
    PmsBaseAttrInfoMapper pmsBaseAttrInfoMapper;
    @Autowired
    PmsBaseAttrValueMapper pmsBaseAttrValueMapper;
    @Override
    public List<PmsBaseAttrInfo> attrInfoList(String catalog3Id) {
        PmsBaseAttrInfo demo = new PmsBaseAttrInfo();
        demo.setCatalog3Id(catalog3Id);
        List<PmsBaseAttrInfo> pmsBaseAttrInfos = pmsBaseAttrInfoMapper.select(demo);
        return pmsBaseAttrInfos;
    }
}
