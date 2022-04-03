package com.dong.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.conditions.query.QueryChainWrapper;
import com.dong.pojo.SkGoodsSecKill;
import com.dong.pojo.SkOrder;
import com.dong.pojo.SkOrderInfo;
import com.dong.pojo.SkUser;
import com.dong.service.ISkGoodsService;
import com.dong.service.ISkOrderInfoService;
import com.dong.service.ISkOrderService;
import com.dong.vo.GoodVo;
import com.dong.vo.OrderDetailVo;
import com.dong.vo.RespBean;
import com.dong.vo.RespBeanEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author DongZhi
 * @since 2022-03-09
 */
@Controller
@RequestMapping("/secKill")
public class SkGoodsSecKillController {
    @Autowired
    ISkGoodsService iSkGoodsService;
    @Autowired
    ISkOrderService iSkOrderService;
    @Autowired
    ISkOrderInfoService    iSkOrderInfoService;
    @RequestMapping("/do_secKill")
    public String secKill(Model model, SkUser user,Long goodsId){
        //是否登录 如果没有用户信息 返回到登录页面
        if (user==null){
            return "login";
        }
        GoodVo goods = iSkGoodsService.findGoodsVoByGoodsId(goodsId);
        //如果剩下的没有了 就返回错误信息到seckill_fail.html页面
            if (goods.getStockCount()<1){
                model.addAttribute("errmsg", RespBeanEnum.EMPTY_STOCK.getMessage());
                return "seckill_fail";
            }
            //防止二次买入 每人只能买一次
        SkOrder one = iSkOrderService.getOne(new QueryWrapper<SkOrder>()
                .eq("user_id", user.getId())
                .eq("goods_id", goodsId));
            if (one!=null){
                model.addAttribute("errmsg", RespBeanEnum.REPEATE_ERROR.getMessage());
                return "seckill_fail";
            }
            //抢购页面
        SkOrderInfo order = iSkOrderInfoService.secKill(user,goods);
            //有一些信息也需要GoodsVo对象，所以这里引入
            model.addAttribute("goods",goods);
        model.addAttribute("orderInfo",order);
        return "order_detail";
    }
    @RequestMapping(value = "/do_secKill2",method = RequestMethod.POST)
    @ResponseBody
    public RespBean secKill2(Model model, SkUser user,Long goodsId){
        //是否登录 如果没有用户信息 返回到登录页面
        if (user==null){
            return RespBean.error(RespBeanEnum.SESSION_ERROR);
        }
        GoodVo goods = iSkGoodsService.findGoodsVoByGoodsId(goodsId);
        //如果剩下的没有了 就返回错误信息到seckill_fail.html页面
        if (goods.getStockCount()<1){

            return RespBean.error(RespBeanEnum.EMPTY_STOCK);
        }
//        防止二次买入 每人只能买一次
        SkOrder one = iSkOrderService.getOne(new QueryWrapper<SkOrder>()
                .eq("user_id", user.getId())
                .eq("goods_id", goodsId));
        if (one!=null){

            return RespBean.error(RespBeanEnum.REPEATE_ERROR);
        }
        //抢购页面
        SkOrderInfo order = iSkOrderInfoService.secKill(user,goods);
        return RespBean.success(order);
    }



}
