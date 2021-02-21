package com.meng.gmall.manager.service.impl;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.alibaba.dubbo.config.annotation.Service;
import com.meng.gmall.bean.PmsBaseAttrInfo;
import com.meng.gmall.bean.PmsBaseAttrValue;
import com.meng.gmall.manager.mapper.PmsBaseAttrInfoMapper;
import com.meng.gmall.manager.mapper.PmsBaseAttrValueMapper;
import com.meng.gmall.service.AttrService;
import org.springframework.beans.factory.annotation.Autowired;
import tk.mybatis.mapper.entity.Example;

import java.util.List;
@Service
public class AttrServiceImpl implements AttrService {
    @Autowired
    PmsBaseAttrInfoMapper pmsBaseAttrInfoMapper;
    @Autowired
    PmsBaseAttrValueMapper pmsBaseAttrValueMapper;
    //显示平台属性及对应的平台属性值
    @Override
    public List<PmsBaseAttrInfo> attrInfoList(String catalog3Id) {
        PmsBaseAttrInfo demo = new PmsBaseAttrInfo();
        demo.setCatalog3Id(catalog3Id);
        List<PmsBaseAttrInfo> pmsBaseAttrInfos = pmsBaseAttrInfoMapper.select(demo);
        //获取平台属性值用于填加sku时使用
        for (PmsBaseAttrInfo pmsBaseAttrInfo : pmsBaseAttrInfos){
            List<PmsBaseAttrValue> pmsBaseAttrValues = getAttrValueList(pmsBaseAttrInfo.getId());
            pmsBaseAttrInfo.setAttrValueList(pmsBaseAttrValues);
        }
        return pmsBaseAttrInfos;
    }
    //保存平台属性值
    @Override
    public void saveInfo(PmsBaseAttrInfo pmsBaseAttrInfo) {
        String id = pmsBaseAttrInfo.getId();
        //id为空表示新增
        if (StringUtils.isBlank(id)){
            pmsBaseAttrInfoMapper.insertSelective(pmsBaseAttrInfo);
            for(PmsBaseAttrValue pmsBaseAttrValue : pmsBaseAttrInfo.getAttrValueList()){
                pmsBaseAttrValue.setAttrId(pmsBaseAttrInfo.getId());
                pmsBaseAttrValueMapper.insertSelective(pmsBaseAttrValue);
            }
        }else {
            //否则表示修改
            List<PmsBaseAttrValue> attrValueList = pmsBaseAttrInfo.getAttrValueList();
            Example example = new Example(PmsBaseAttrInfo.class);
            example.createCriteria().andEqualTo("id",id);
            pmsBaseAttrInfoMapper.updateByExampleSelective(pmsBaseAttrInfo,example);

            PmsBaseAttrValue demo = new PmsBaseAttrValue();
            demo.setAttrId(id);
            pmsBaseAttrValueMapper.delete(demo);
            for(PmsBaseAttrValue pmsBaseAttrValue : pmsBaseAttrInfo.getAttrValueList()){
                pmsBaseAttrValue.setAttrId(id);
                pmsBaseAttrValueMapper.insertSelective(pmsBaseAttrValue);
            }
        }

    }
    //获取平台属性值
    @Override
    public List<PmsBaseAttrValue> getAttrValueList(String attrId) {
        PmsBaseAttrValue demo = new PmsBaseAttrValue();
        demo.setAttrId(attrId);
        List<PmsBaseAttrValue> pmsBaseAttrValues = pmsBaseAttrValueMapper.select(demo);
        return pmsBaseAttrValues;
    }
}
