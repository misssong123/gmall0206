package com.meng.gmall.manager.mapper;

import com.meng.gmall.bean.PmsSkuInfo;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface PmsSkuInfoMapper extends Mapper<PmsSkuInfo> {
    List<PmsSkuInfo> getSkuSaleAttrValueListBySpu(@Param("spuId")String productId);
}
