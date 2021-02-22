package com.meng.gmall.manager.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.meng.gmall.bean.PmsSkuAttrValue;
import com.meng.gmall.bean.PmsSkuImage;
import com.meng.gmall.bean.PmsSkuInfo;
import com.meng.gmall.bean.PmsSkuSaleAttrValue;
import com.meng.gmall.manager.mapper.PmsSkuAttrValueMapper;
import com.meng.gmall.manager.mapper.PmsSkuImageMapper;
import com.meng.gmall.manager.mapper.PmsSkuInfoMapper;
import com.meng.gmall.manager.mapper.PmsSkuSaleAttrValueMapper;
import com.meng.gmall.service.SkuService;
import com.meng.gmall.util.RedisUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import redis.clients.jedis.Jedis;

import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
public class SkuServiceImpl implements SkuService {

    @Autowired
    PmsSkuInfoMapper pmsSkuInfoMapper;
    @Autowired
    PmsSkuImageMapper pmsSkuImageMapper;
    @Autowired
    PmsSkuAttrValueMapper pmsSkuAttrValueMapper;
    @Autowired
    PmsSkuSaleAttrValueMapper pmsSkuSaleAttrValueMapper;
    @Autowired
    RedisUtil redisUtil;
    //保存sku数据信息
    @Override
    public String saveSkuInfo(PmsSkuInfo pmsSkuInfo) {
        //保存skuInfo信息
        pmsSkuInfoMapper.insertSelective(pmsSkuInfo);
        String skuId = pmsSkuInfo.getId();
        //保存sku图片信息
        List<PmsSkuImage> skuImageList = pmsSkuInfo.getSkuImageList();
        for (PmsSkuImage pmsSkuImage : skuImageList) {
            pmsSkuImage.setSkuId(skuId);
            pmsSkuImageMapper.insertSelective(pmsSkuImage);
        }
        //保存sku平台属性信息
        List<PmsSkuAttrValue> skuAttrValueList = pmsSkuInfo.getSkuAttrValueList();
        for (PmsSkuAttrValue pmsSkuAttrValue : skuAttrValueList) {
            pmsSkuAttrValue.setSkuId(skuId);
            pmsSkuAttrValueMapper.insertSelective(pmsSkuAttrValue);
        }
        //保存sku销售属性信息
        List<PmsSkuSaleAttrValue> skuSaleAttrValueList = pmsSkuInfo.getSkuSaleAttrValueList();
        for (PmsSkuSaleAttrValue pmsSkuSaleAttrValue : skuSaleAttrValueList) {
            pmsSkuSaleAttrValue.setSkuId(skuId);
            pmsSkuSaleAttrValueMapper.insertSelective(pmsSkuSaleAttrValue);
        }
        return "success";
    }
    //通过主键获取sku,从数据库中进行获取
    public PmsSkuInfo getSkuByIdFromDb(String skuId) {
        PmsSkuInfo pmsSkuInfo = new PmsSkuInfo();
        pmsSkuInfo.setId(skuId);
        PmsSkuInfo skuInfo = pmsSkuInfoMapper.selectOne(pmsSkuInfo);
        //获取sku对应的图片信息
        PmsSkuImage pmsSkuImage = new PmsSkuImage();
        pmsSkuImage.setSkuId(skuId);
        List<PmsSkuImage> pmsSkuImages = pmsSkuImageMapper.select(pmsSkuImage);
        skuInfo.setSkuImageList(pmsSkuImages);
        return skuInfo;
    }
    //通过主键获取sku
    @Override
    public PmsSkuInfo getSkuById(String skuId) {
        PmsSkuInfo pmsSkuInfo = null;
        //获取jedis连接
        Jedis jedis = redisUtil.getJedis();
        String key = "sku:"+skuId+":info";
        String skuInfo = jedis.get(key);
        //若缓存中存在，则直接返回，否则查询数据库
        if (StringUtils.isNotBlank(skuInfo)){
            pmsSkuInfo = JSON.parseObject(skuInfo, PmsSkuInfo.class);
        }else {
            // 设置分布式锁,避免缓存击穿,设置值避免误删其他的锁
            String token = UUID.randomUUID().toString();
            String OK = jedis.set("sku:" + skuId + ":lock", token, "nx", "px", 10);
            if (StringUtils.isNotBlank(OK)&& OK.equals("OK")){
                pmsSkuInfo = getSkuByIdFromDb(skuId);
                if(pmsSkuInfo!=null){
                    // mysql查询结果存入redis
                    jedis.set("sku:"+skuId+":info",JSON.toJSONString(pmsSkuInfo));
                }else{
                    // 如果数据库中不存在该sku
                    // 为了防止缓存穿透将，null或者空字符串值设置给redis
                    jedis.setex("sku:"+skuId+":info",60*3,JSON.toJSONString(""));
                }
                //比较是否为同一个进程上的锁，避免误删锁的操作
                /*String lockToken = jedis.get("sku:" + skuId + ":lock");
                if(StringUtils.isNotBlank(lockToken)&&lockToken.equals(token)){
                    //jedis.eval("lua");可与用lua脚本，在查询到key的同时删除该key，防止高并发下的意外的发生
                    jedis.del("sku:" + skuId + ":lock");// 用token确认删除的是自己的sku的锁
                }*/
                //lua脚本解决，避免判断后锁恰好失效
                String script ="if redis.call('get', KEYS[1]) == ARGV[1] then return redis.call('del', KEYS[1]) else return 0 end";
                jedis.eval(script, Collections.singletonList("sku:" + skuId + ":lock"),Collections.singletonList(token));
            }else {
                //自旋，等待下次调用
                try {
                    TimeUnit.SECONDS.sleep(3);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return getSkuById(skuId);
            }
        }
        jedis.close();
        return pmsSkuInfo;
    }
    //获取同一个spu下的sku信息
    @Override
    public List<PmsSkuInfo> getSkuSaleAttrValueListBySpu(String productId) {
        List<PmsSkuInfo> pmsSkuInfos = pmsSkuInfoMapper.getSkuSaleAttrValueListBySpu(productId);
        return pmsSkuInfos;
    }
}
