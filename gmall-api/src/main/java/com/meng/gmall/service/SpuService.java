package com.meng.gmall.service;

import com.meng.gmall.bean.PmsProductImage;
import com.meng.gmall.bean.PmsProductInfo;
import com.meng.gmall.bean.PmsProductSaleAttr;

import java.util.List;

public interface SpuService {
    List<PmsProductInfo> spuList(String catalog3Id);

    String saveSpuInfo(PmsProductInfo pmsProductInfo);

    List<PmsProductImage> spuImageList(String spuId);

    List<PmsProductSaleAttr> spuSaleAttrList(String spuId);
}
