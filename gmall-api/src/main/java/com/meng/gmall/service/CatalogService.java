package com.meng.gmall.service;

import com.meng.gmall.bean.PmsBaseCatalog1;
import com.meng.gmall.bean.PmsBaseCatalog2;
import com.meng.gmall.bean.PmsBaseCatalog3;

import java.util.List;

public interface CatalogService {
    List<PmsBaseCatalog3> getCatalog3(String catalog2Id);

    List<PmsBaseCatalog2> getCatalog2(String catalog1Id);

    List<PmsBaseCatalog1> getCatalog1();
}
