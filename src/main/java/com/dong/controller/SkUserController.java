package com.dong.controller;


import com.baomidou.mybatisplus.extension.api.R;
import com.dong.pojo.SkGoods;
import com.dong.service.ITUserService;
import com.dong.service.impl.TUserServiceImpl;
import com.dong.vo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.thymeleaf.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.math.BigDecimal;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author DongZhi
 * @since 2022-03-09
 */
@Controller
@RequestMapping("/sk_user")
public class SkUserController extends SkGoods {
    @Autowired
    TUserServiceImpl tUserServiceImpl;
    @Autowired
    RedisTemplate redisTemplate;
    @RequestMapping("/toRegister")
    public String toRegister(){
        return "register";
    }
    @RequestMapping("/doRegister")
    @ResponseBody
    public RespBean deRegister(RegisterVo registerVo, HttpServletRequest request, HttpServletResponse response){
        return   tUserServiceImpl.toRegister(registerVo,request,response);
    }
    @RequestMapping("/pap")
    public String toPap(Model model){
        return "pap";
    }
    @RequestMapping("/vCode")
    @ResponseBody
    public RespBean toCode(VCodeVo vCode) throws IOException {
        return tUserServiceImpl.doCode(vCode);
    }
    @RequestMapping("/vOneCode")
    @ResponseBody
    public RespBean doCode(VCodeVo vCode){
        String vCode01 = (String) redisTemplate.opsForValue().get(vCode.getMobile());
        if (StringUtils.isEmpty(vCode01)){
            return RespBean.error(RespBeanEnum.CODE_ERROR);
        }
        if (!vCode01.equals(vCode.getVCode())){
            return RespBean.error(RespBeanEnum.CODE02_ERROR);
        }
        return RespBean.success();
    }

}


