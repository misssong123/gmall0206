package com.meng.gmall.manager.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.meng.gmall.bean.PmsBaseSaleAttr;
import com.meng.gmall.manager.mapper.PmsBaseSaleAttrMapper;
import com.meng.gmall.service.SaleAttrService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
@Service
public class SaleAttrServiceImpl implements SaleAttrService {
    @Autowired
    PmsBaseSaleAttrMapper pmsBaseSaleAttrMapper;
    @Override
    public List<PmsBaseSaleAttr> baseSaleAttrList() {
        List<PmsBaseSaleAttr> pmsBaseSaleAttrs = pmsBaseSaleAttrMapper.selectAll();
        return pmsBaseSaleAttrs;
    }
}
