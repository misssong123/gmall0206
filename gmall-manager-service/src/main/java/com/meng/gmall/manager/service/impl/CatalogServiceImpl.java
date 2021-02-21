package com.meng.gmall.manager.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.meng.gmall.bean.PmsBaseCatalog1;
import com.meng.gmall.bean.PmsBaseCatalog2;
import com.meng.gmall.bean.PmsBaseCatalog3;
import com.meng.gmall.manager.mapper.PmsBaseCatalog1Mapper;
import com.meng.gmall.manager.mapper.PmsBaseCatalog2Mapper;
import com.meng.gmall.manager.mapper.PmsBaseCatalog3Mapper;
import com.meng.gmall.service.CatalogService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Service
public class CatalogServiceImpl implements CatalogService {

    @Autowired
    PmsBaseCatalog1Mapper pmsBaseCatalog1Mapper;

    @Autowired
    PmsBaseCatalog2Mapper pmsBaseCatalog2Mapper;

    @Autowired
    PmsBaseCatalog3Mapper pmsBaseCatalog3Mapper;

    @Override
    public List<PmsBaseCatalog3> getCatalog3(String catalog2Id) {
        PmsBaseCatalog3 demo = new PmsBaseCatalog3();
        demo.setCatalog2Id(catalog2Id);
        List<PmsBaseCatalog3> pmsBaseCatalog3s = pmsBaseCatalog3Mapper.select(demo);
        return pmsBaseCatalog3s;
    }

    @Override
    public List<PmsBaseCatalog2> getCatalog2(String catalog1Id) {
        PmsBaseCatalog2 demo = new PmsBaseCatalog2();
        demo.setCatalog1Id(catalog1Id);
        List<PmsBaseCatalog2> pmsBaseCatalog2s = pmsBaseCatalog2Mapper.select(demo);
        return pmsBaseCatalog2s;
    }

    @Override
    public List<PmsBaseCatalog1> getCatalog1() {
        List<PmsBaseCatalog1> pmsBaseCatalog1s = pmsBaseCatalog1Mapper.selectAll();
        return pmsBaseCatalog1s;
    }
}
