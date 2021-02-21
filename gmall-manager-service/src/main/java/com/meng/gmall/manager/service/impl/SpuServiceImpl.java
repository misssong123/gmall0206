package com.meng.gmall.manager.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.meng.gmall.bean.PmsProductImage;
import com.meng.gmall.bean.PmsProductInfo;
import com.meng.gmall.bean.PmsProductSaleAttr;
import com.meng.gmall.bean.PmsProductSaleAttrValue;
import com.meng.gmall.manager.mapper.PmsProductImageMapper;
import com.meng.gmall.manager.mapper.PmsProductInfoMapper;
import com.meng.gmall.manager.mapper.PmsProductSaleAttrMapper;
import com.meng.gmall.manager.mapper.PmsProductSaleAttrValueMapper;
import com.meng.gmall.service.SpuService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Service
public class SpuServiceImpl implements SpuService {

    @Autowired
    PmsProductInfoMapper pmsProductInfoMapper;
    @Autowired
    PmsProductImageMapper pmsProductImageMapper;
    @Autowired
    PmsProductSaleAttrMapper pmsProductSaleAttrMapper;
    @Autowired
    PmsProductSaleAttrValueMapper pmsProductSaleAttrValueMapper;

    //指定catalog3下的spu的展示
    @Override
    public List<PmsProductInfo> spuList(String catalog3Id) {
        PmsProductInfo demo = new PmsProductInfo();
        demo.setCatalog3Id(catalog3Id);
        List<PmsProductInfo> pmsProductInfos = pmsProductInfoMapper.select(demo);
        return pmsProductInfos;
    }

    //保存spu信息
    @Override
    public String saveSpuInfo(PmsProductInfo pmsProductInfo) {
        //保存spu信息
        pmsProductInfoMapper.insertSelective(pmsProductInfo);
        //获取当前spu的值
        String spuId = pmsProductInfo.getId();
        //保存spu图片信息
        List<PmsProductImage> spuImageLists = pmsProductInfo.getSpuImageList();
        for (PmsProductImage pmsProductImage : spuImageLists) {
            pmsProductImage.setProductId(spuId);
            pmsProductImageMapper.insertSelective(pmsProductImage);
        }
        //保存spu对应的销售属性
        List<PmsProductSaleAttr> spuSaleAttrLists = pmsProductInfo.getSpuSaleAttrList();
        for (PmsProductSaleAttr pmsProductSaleAttr : spuSaleAttrLists) {
            pmsProductSaleAttr.setProductId(spuId);
            pmsProductSaleAttrMapper.insertSelective(pmsProductSaleAttr);
            //保存销售属性对应的值
            List<PmsProductSaleAttrValue> spuSaleAttrValueLists = pmsProductSaleAttr.getSpuSaleAttrValueList();
            for (PmsProductSaleAttrValue pmsProductSaleAttrValue : spuSaleAttrValueLists) {
                pmsProductSaleAttrValue.setProductId(spuId);
                pmsProductSaleAttrValueMapper.insertSelective(pmsProductSaleAttrValue);
            }
        }
        return "success";
    }

    //展示spu下的图片
    @Override
    public List<PmsProductImage> spuImageList(String spuId) {
        PmsProductImage demo = new PmsProductImage();
        demo.setProductId(spuId);
        List<PmsProductImage> pmsProductImages = pmsProductImageMapper.select(demo);
        return pmsProductImages;
    }

    //展示spu对应的销售属性
    @Override
    public List<PmsProductSaleAttr> spuSaleAttrList(String spuId) {
        PmsProductSaleAttr pmsProductSaleAttr = new PmsProductSaleAttr();
        pmsProductSaleAttr.setProductId(spuId);
        List<PmsProductSaleAttr> pmsProductSaleAttrs = pmsProductSaleAttrMapper.select(pmsProductSaleAttr);
        //获取对应的销售属性值
        for (PmsProductSaleAttr demo : pmsProductSaleAttrs) {
            PmsProductSaleAttrValue pmsProductSaleAttrValue = new PmsProductSaleAttrValue();
            pmsProductSaleAttrValue.setSaleAttrId(demo.getSaleAttrId());
            pmsProductSaleAttrValue.setProductId(spuId);
            List<PmsProductSaleAttrValue> pmsProductSaleAttrValues = pmsProductSaleAttrValueMapper.select(pmsProductSaleAttrValue);
            demo.setSpuSaleAttrValueList(pmsProductSaleAttrValues);
        }
        return pmsProductSaleAttrs;
    }
    //通过spuId和skuId获取对应的销售属性信息
    @Override
    public List<PmsProductSaleAttr> spuSaleAttrListCheckBySku(String productId) {
        PmsProductSaleAttr pmsProductSaleAttr = new PmsProductSaleAttr();
        pmsProductSaleAttr.setProductId(productId);
        List<PmsProductSaleAttr> pmsProductSaleAttrs = pmsProductSaleAttrMapper.select(pmsProductSaleAttr);

        for (PmsProductSaleAttr productSaleAttr : pmsProductSaleAttrs) {
            String saleAttrId = productSaleAttr.getSaleAttrId();

            PmsProductSaleAttrValue pmsProductSaleAttrValue = new PmsProductSaleAttrValue();
            pmsProductSaleAttrValue.setSaleAttrId(saleAttrId);
            pmsProductSaleAttrValue.setProductId(productId);
            List<PmsProductSaleAttrValue> pmsProductSaleAttrValues = pmsProductSaleAttrValueMapper.select(pmsProductSaleAttrValue);

            productSaleAttr.setSpuSaleAttrValueList(pmsProductSaleAttrValues);
        }
            return pmsProductSaleAttrs;
    }
    //通过spuId和skuId获取对应的销售属性信息,并判断当前选中的sku对应的销售属性信息
    @Override
    public List<PmsProductSaleAttr> spuSaleAttrListCheckBySku(String productId, String id) {
        List<PmsProductSaleAttr>  pmsProductSaleAttrs = pmsProductSaleAttrMapper.spuSaleAttrListCheckBySku(productId,id);
        return pmsProductSaleAttrs;
    }
}
