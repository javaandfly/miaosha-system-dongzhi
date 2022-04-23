package com.dong.service;

import com.dong.pojo.SkOrder;
import com.dong.pojo.SkOrderInfo;
import com.baomidou.mybatisplus.extension.service.IService;
import com.dong.pojo.SkUser;
import com.dong.vo.GoodVo;
import com.dong.vo.OrderDetailVo;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author DongZhi
 * @since 2022-03-09
 */
@Mapper
public interface ISkOrderInfoService extends IService<SkOrderInfo> {
    /**
     * 秒杀详情
     * @param user
     * @param goods
     * @return
     */
    SkOrderInfo secKill(SkUser user, GoodVo goods);

    /**
     * 订单详情
     * @param orderId
     * @return
     */
    OrderDetailVo detail(Long orderId);
}
