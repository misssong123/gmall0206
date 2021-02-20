package com.meng.gmall.manager.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.meng.gmall.bean.PmsProductInfo;
import com.meng.gmall.manager.mapper.PmsProductInfoMapper;
import com.meng.gmall.service.SpuService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
@Service
public class SpuServiceImpl implements SpuService {

    @Autowired
    PmsProductInfoMapper pmsProductInfoMapper;

    @Override
    public List<PmsProductInfo> spuList(String catalog3Id) {
        PmsProductInfo demo = new PmsProductInfo();
        demo.setCatalog3Id(catalog3Id);
        List<PmsProductInfo> pmsProductInfos = pmsProductInfoMapper.select(demo);
        return pmsProductInfos;
    }
}
