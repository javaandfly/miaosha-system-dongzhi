package com.dong.service.impl;

import com.dong.pojo.SkUser;
import com.dong.mapper.SkUserMapper;
import com.dong.service.ITUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dong.utils.CookieUtils;
import com.dong.utils.MD5Utils;
import com.dong.utils.UUIDUtil;
import com.dong.utils.ValidatorUtils;
import com.dong.vo.RegisterVo;
import com.dong.vo.RespBean;
import com.dong.vo.RespBeanEnum;
import com.dong.vo.loginVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.thymeleaf.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author DongZhi
 * @since 2022-02-22
 */
@Service
public class TUserServiceImpl extends ServiceImpl<SkUserMapper, SkUser> implements ITUserService {

    /**
     * 登录
     * @param loginVo
     * @return
     */
    @Autowired
     SkUserMapper tUserMapper;
    @Autowired
     RedisTemplate redisTemplate;
    @Override
    public RespBean doLogin(loginVo loginVo, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        //获取前端账号Mobile和密码Password
        String mobile = loginVo.getMobile();
        String password = loginVo.getPassword();
        //判断账号密码是否为空
        if (StringUtils.isEmpty(mobile) || StringUtils.isEmpty(password)){
            return RespBean.error(RespBeanEnum.LOGIN_ERROR);
        }
        //判断账号是否为手机号
        if (!ValidatorUtils.isMobile(mobile)){
            //如果不是手机号，调用公共返回类RespBean方法中的方法error 然后传入错误信息，输出

            return RespBean.error(RespBeanEnum.Mobile_ERROR);
        }

        //根据手机号获取用户，从数据库查询
        SkUser tUser  = tUserMapper.selectById(mobile);
        //如果手机号在数据库中没有查找到
        if(null==tUser){
            return RespBean.error(RespBeanEnum.LOGIN_ERROR);
        }
        //获取掩码，把密码放进去,注意传进来的时候已经进行一次加密了 这里只需要加密一次再判断
        if(!MD5Utils.fromPassToDbPass(password,tUser.getSalt()).equals(tUser.getPassword())){
            return RespBean.error(RespBeanEnum.LOGIN_ERROR);
        }

        //生成cookie
        String ticket = UUIDUtil.uuid();
        //将用户信息存入redis中
        redisTemplate.opsForValue().set("user:" + ticket, tUser);
        CookieUtils.setCookie(httpServletRequest, httpServletResponse, "userTicket", ticket);
        return RespBean.success(ticket);

    }


    /**
     * 功能描述: 根据cookie获取用户
     */
    @Override
    public SkUser getUserByCookie(String userTicket, HttpServletRequest request, HttpServletResponse response) {
        if (StringUtils.isEmpty(userTicket)) {
            return null;
        }
        SkUser user = (SkUser) redisTemplate.opsForValue().get("user:" + userTicket);
        if (user != null) {
            CookieUtils.setCookie(request, response, "userTicket", userTicket);
        }
        return user;
    }

    @Override
    public RespBean toRegister(RegisterVo registerVo, HttpServletRequest request, HttpServletResponse response) {
        //获取前端账号Mobile和密码Password
        String mobile = registerVo.getMobile();
        String nickname=registerVo.getNickname();
        String password = registerVo.getPassword();
        if (mobile==null ||StringUtils.isEmpty(nickname)||StringUtils.isEmpty(password)){
            return RespBean.error(RespBeanEnum.SESSION_ERROR);
        }
        //判断账号是否为手机号
        if (!ValidatorUtils.isMobile(mobile)){
            //如果不是手机号，调用公共返回类RespBean方法中的方法error 然后传入错误信息，输出

            return RespBean.error(RespBeanEnum.Mobile_ERROR);
        }
        SkUser user = new SkUser();
       user.setId(Long.parseLong(mobile));
        user.setNickname(nickname);
        user.setRegisterDate(new Date());
        user.setSalt("1a2b3c4d");
        user.setLastLoginDate(new Date());
        MD5Utils.fromPassToDbPass(password,user.getSalt());
        tUserMapper.insert(user);
        System.out.println(user);
        return RespBean.success(user);
    }



}
