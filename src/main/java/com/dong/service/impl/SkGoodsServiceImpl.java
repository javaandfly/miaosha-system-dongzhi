package com.dong.service.impl;

import com.dong.pojo.SkGoods;
import com.dong.mapper.SkGoodsMapper;
import com.dong.pojo.SkUser;
import com.dong.service.ISkGoodsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dong.vo.GoodVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author DongZhi
 * @since 2022-03-09
 */
@Service
public class SkGoodsServiceImpl extends ServiceImpl<SkGoodsMapper, SkGoods> implements ISkGoodsService {
    /**
     * 获取商品列表
     * @return
     */
    @Autowired
    SkGoodsMapper skGoodsMapper;
    @Override
    public List<GoodVo> findGoodVo() {

        return skGoodsMapper.findGoodVo();
    }
    /**
     * 功能描述: 获取商品详情
     */
    @Override
    public GoodVo findGoodsVoByGoodsId(Long goodsId) {
        return skGoodsMapper.findGoodsVoByGoodsId(goodsId);
    }

}
