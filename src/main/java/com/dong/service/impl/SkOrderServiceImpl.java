package com.dong.service.impl;

import com.dong.mapper.SkGoodsMapper;
import com.dong.pojo.SkOrder;
import com.dong.mapper.SkOrderMapper;
import com.dong.pojo.SkUser;
import com.dong.service.ISkGoodsService;
import com.dong.service.ISkOrderService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dong.vo.GoodVo;
import com.dong.vo.OrderDetailVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author DongZhi
 * @since 2022-03-09
 */
@Service
public class SkOrderServiceImpl extends ServiceImpl<SkOrderMapper, SkOrder> implements ISkOrderService {

}
