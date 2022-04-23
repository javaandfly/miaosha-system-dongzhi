package com.dong.service;

import com.dong.pojo.SkGoods;
import com.baomidou.mybatisplus.extension.service.IService;
import com.dong.pojo.SkUser;
import com.dong.vo.GoodVo;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author DongZhi
 * @since 2022-03-09
 */
public interface ISkGoodsService extends IService<SkGoods> {
    //显示部分信息
    List<GoodVo> findGoodVo();
    //根据id找信息
    GoodVo findGoodsVoByGoodsId(Long goodsId);
}

