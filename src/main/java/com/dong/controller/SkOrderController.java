package com.dong.controller;


import com.dong.pojo.SkUser;
import com.dong.service.ISkOrderInfoService;
import com.dong.service.ISkOrderService;
import com.dong.vo.OrderDetailVo;
import com.dong.vo.RespBean;
import com.dong.vo.RespBeanEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

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
@RequestMapping("/order")
public class SkOrderController {
    @Autowired
    private ISkOrderInfoService iSkOrderInfoService;
    /**
     * 订单详情页面
     * @param user
     * @return
     */
    @RequestMapping("/detail")
    @ResponseBody
    public RespBean detail(SkUser user,Long orderId){
        if(user==null){
            return RespBean.error(RespBeanEnum.SESSION_ERROR);
        }
        OrderDetailVo detail= iSkOrderInfoService.detail(orderId);
        System.out.println(detail);
        return RespBean.success(detail);
    }
}
