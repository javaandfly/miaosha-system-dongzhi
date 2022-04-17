package com.dong.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.conditions.update.UpdateChainWrapper;
import com.dong.exception.GlobalException;
import com.dong.mapper.SkGoodsMapper;
import com.dong.mapper.SkOrderMapper;
import com.dong.mapper.SkUserMapper;
import com.dong.pojo.*;
import com.dong.mapper.SkOrderInfoMapper;
import com.dong.service.ISkGoodsSeckillService;
import com.dong.service.ISkGoodsService;
import com.dong.service.ISkOrderInfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dong.service.ISkOrderService;
import com.dong.vo.GoodVo;
import com.dong.vo.OrderDetailVo;
import com.dong.vo.RespBean;
import com.dong.vo.RespBeanEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author DongZhi
 * @since 2022-03-09
 */
@Service
public class SkOrderInfoServiceImpl extends ServiceImpl<SkOrderInfoMapper, SkOrderInfo> implements ISkOrderInfoService {
   @Autowired
   private ISkGoodsService   skGoodsService;
    @Autowired
   private SkOrderInfoMapper skOrderInfoMapper;
    @Autowired
   private ISkOrderService   skOrderService;
    @Autowired
    RedisTemplate redisTemplate;
    @Autowired
    private ISkGoodsSeckillService skGoodsSeckillService;
    @Autowired
    private SkUserMapper         userMapper;
    @Override
    @Transactional
    public SkOrderInfo secKill(SkUser user, GoodVo goods) {
        //秒杀商品表减库存
        //防止二次买入
        SkGoodsSecKill seckillGoods = skGoodsSeckillService.getOne(new
                QueryWrapper<SkGoodsSecKill>().eq("goods_id",
                goods.getId()));
        //设置商品的数量减一
        seckillGoods.setStockCount(seckillGoods.getStockCount()-1);
        //然后修改成减一后的对象
        boolean b = skGoodsSeckillService.update(new UpdateWrapper<SkGoodsSecKill>().setSql("stock_count = " +
                "stock_count-1").eq(
                "goods_id", seckillGoods.getId()).gt("stock_count", 0));
        if (seckillGoods.getStockCount()<1){
            return null;
        }
        //新建一个SkOrderInfo对象
        SkOrderInfo order = new SkOrderInfo();
        //设置用户id等于user表里的id
        order.setUserId(user.getId());
        order.setGoodsId(goods.getId());
        order.setDeliveryAddrId(0l);
        order.setGoodsName(goods.getGoodsName());
        order.setGoodsCount(1);
        order.setGoodsPrice(seckillGoods.getSeckillPrice());
        order.setOrderChannel(1);
        order.setStatus(0);
        order.setCreateDate(new Date());
        //持久化到数据库 添加进去
        skOrderInfoMapper.insert(order);
        //生成秒杀订单
       SkOrder seckillOrder = new SkOrder();
        seckillOrder.setUserId(user.getId());
        seckillOrder.setOrderId(order.getId());
        seckillOrder.setGoodsId(goods.getId());
        //持久化到数据库
        skOrderService.save(seckillOrder);
        //为了防止超卖 把这个skorder存在redis里 防止两个人一起进来，就是用了一个索引
        redisTemplate.opsForValue().set("order"+user.getId()+":"+goods.getId(),seckillOrder,60, TimeUnit.MINUTES);
        return order;

    }

    @Override
    @Transactional
    public OrderDetailVo detail(Long orderId) {
        if (orderId==null){
        throw new GlobalException(RespBeanEnum.ORDER_NOT_EXIST);
        }
        SkOrderInfo order = skOrderInfoMapper.selectById(orderId);
        System.out.println(order);
        SkUser user=userMapper.selectById(order.getUserId());
        GoodVo goodsVo = skGoodsService.findGoodsVoByGoodsId(order.getGoodsId());
        OrderDetailVo detail = new OrderDetailVo();
        detail.setOrder(order);
        detail.setUser(user);
        detail.setGoodVo(goodsVo);
        return detail;
    }

}
