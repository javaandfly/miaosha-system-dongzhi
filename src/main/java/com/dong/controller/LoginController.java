package com.dong.controller;

import com.dong.service.ITUserService;
import com.dong.service.impl.TUserServiceImpl;
import com.dong.vo.RegisterVo;
import com.dong.vo.RespBean;
import com.dong.vo.loginVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@Controller
@RequestMapping("/login")
@Slf4j
public class LoginController {
    @Autowired
    TUserServiceImpl itUserService;
    //跳转登陆页面
    @RequestMapping("/toLogin")
    public String toLogin(){
        return "login";
    }
    //登录功能
    @RequestMapping("/doLogin")
    @ResponseBody
    //@Valid作用是验证 有效
    public RespBean doLogin(@Valid loginVo loginVo, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse){
        return itUserService.doLogin(loginVo,httpServletRequest,httpServletResponse);
    }

}
