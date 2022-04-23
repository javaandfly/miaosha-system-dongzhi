package com.dong.mapper;

import com.dong.pojo.SkGoods;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dong.vo.GoodVo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author DongZhi
 * @since 2022-03-09
 */
@Mapper

public interface SkGoodsMapper extends BaseMapper<SkGoods> {

    /**
     * 功能描述: 获取商品列表
     */
    List<GoodVo> findGoodVo();

    /**
     * 功能描述: 获取商品详情
     */
    GoodVo findGoodsVoByGoodsId(Long goodsId);
}
