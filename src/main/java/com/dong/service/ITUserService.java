package com.dong.service;

import com.dong.pojo.SkUser;
import com.baomidou.mybatisplus.extension.service.IService;
import com.dong.vo.RegisterVo;
import com.dong.vo.RespBean;
import com.dong.vo.VCodeVo;
import com.dong.vo.loginVo;
import org.apache.catalina.User;
import org.apache.ibatis.annotations.Mapper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author DongZhi
 * @since 2022-02-22
 */
public interface ITUserService extends IService<SkUser> {

    /**
     * 登录
     * @param loginVo
     * @param httpServletRequest
     * @param httpServletResponse
     * @return
     */
    RespBean doLogin(loginVo loginVo, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse);

    /**
     * 获取Cookie
     * @param userTicket
     * @param request
     * @param response
     * @return
     */
    SkUser getUserByCookie(String userTicket, HttpServletRequest request, HttpServletResponse response);
    /**
     * 注册
     */
   RespBean toRegister(RegisterVo registerVo, HttpServletRequest request, HttpServletResponse response);
   RespBean doCode(VCodeVo vCode) throws IOException;
}
