package com.meng.gmall.manager.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.meng.gmall.bean.PmsBaseSaleAttr;
import com.meng.gmall.service.SaleAttrService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@CrossOrigin
@Controller
public class SaleAttrController {
    @Reference
    SaleAttrService saleAttrService;

    @RequestMapping("baseSaleAttrList")
    @ResponseBody
    public List<PmsBaseSaleAttr> baseSaleAttrList(){
        List<PmsBaseSaleAttr> pmsBaseSaleAttrs = saleAttrService.baseSaleAttrList();
        return pmsBaseSaleAttrs;
    }
}
