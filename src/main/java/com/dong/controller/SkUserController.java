package com.dong.controller;


import com.dong.pojo.SkGoods;
import com.dong.service.impl.TUserServiceImpl;
import com.dong.vo.RegisterVo;
import com.dong.vo.RespBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
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
@RequestMapping("/sk-user")
public class SkUserController extends SkGoods {
    @Autowired
    TUserServiceImpl tUserService;
    @RequestMapping("/toRegister")
    public String toRegister(){
        return "register";
    }
    @RequestMapping("/doRegister")
    @ResponseBody
    public RespBean deRegister(@Valid RegisterVo registerVo, HttpServletRequest request, HttpServletResponse response){
        return   tUserService.toRegister(registerVo,request,response);
    }

}
