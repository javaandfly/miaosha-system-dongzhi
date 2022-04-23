package com.dong.service.impl;

import com.dong.config.MailClient;
import com.dong.pojo.SkUser;
import com.dong.mapper.SkUserMapper;
import com.dong.service.ITUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dong.utils.*;
import com.dong.vo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.thymeleaf.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

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
    @Autowired
    MailClient mailClient;
    @Override
    public RespBean doLogin(loginVo loginVo, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        //获取前端账号Mobile和密码Password
        String mobile = loginVo.getMobile();
        String password = loginVo.getPassword();
        String vCode = loginVo.getVCode();
        //判断账号密码是否为空
        if (StringUtils.isEmpty(mobile) || StringUtils.isEmpty(password)){
            return RespBean.error(RespBeanEnum.LOGIN_ERROR);
        }
        //判断账号是否为手机号
        if (!ValidatorUtils.isMobile(mobile)){
            //如果不是手机号，调用公共返回类RespBean方法中的方法error 然后传入错误信息，输出

            return RespBean.error(RespBeanEnum.Mobile_ERROR);
        }
        String vCode01 = (String) redisTemplate.opsForValue().get(mobile);
        if (StringUtils.isEmpty(vCode01)){
            return RespBean.error(RespBeanEnum.CODE_ERROR);
        }
        if (!vCode01.equals(vCode)){
            return RespBean.error(RespBeanEnum.CODE02_ERROR);
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
        redisTemplate.opsForValue().set("user:"+ ticket, tUser,60, TimeUnit.MINUTES);
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
        String password = registerVo.getPassword();
        String nickname = registerVo.getNickname();
        if (mobile == null || StringUtils.isEmpty(password) || StringUtils.isEmpty(nickname)) {
            return RespBean.error(RespBeanEnum.SESSION_ERROR);
        }
        //判断账号是否为手机号
        if (!ValidatorUtils.isMobile(mobile)) {
            //如果不是手机号，调用公共返回类RespBean方法中的方法error 然后传入错误信息，输出

            return RespBean.error(RespBeanEnum.Mobile_ERROR);
        }
        SkUser skUser = tUserMapper.selectById(mobile);
        if (skUser != null) {
            return RespBean.error(RespBeanEnum.LOGIN2_ERROR);
        }
        SkUser user = new SkUser();
        user.setId(Long.parseLong(mobile));
        user.setNickname(nickname);
        user.setRegisterDate(new Date());
        user.setSalt("1a2b3c4d");
        user.setPassword(MD5Utils.fromPassToDbPass(password, user.getSalt()));
        user.setLastLoginDate(new Date());
        user.setHead(null);
        user.setLoginCount(1);
        tUserMapper.insert(user);
        return RespBean.success(user);
    }
    @Override
    public RespBean doCode(VCodeVo vCode) throws IOException {
        String email = vCode.getEmail();
        String mobile = vCode.getMobile();
        if (StringUtils.isEmpty(email)){
            return RespBean.error(RespBeanEnum.REQUEST_ERROR);
        }
        String s = new CodeV().codeV(email);
//        Socket socket = null;
//        OutputStream os = null;
//        //1.创建Socket对象，指明服务器端的ip和端口号
//        InetAddress inet = InetAddress.getByName("43.138.37.7");
//        socket = new Socket(inet, 13000);
//        //2.获取一个输出流，用于输出数据
//        os = socket.getOutputStream();
//        //3.写出数据的操作
//        String str="{"+"\""+"senduser"+"\""+":"+"\""+"3267919396@qq.com"+"\""+","+
//                "\n"+"\""+"recvuser"+"\""+":"+"\""+email+"\""+","+
//                "\n"+"\""+"sqm"+"\""+":"+"\""+"hylsbgtrleukcicj"+"\""+","+
//                "\n"+"\""+"data"+"\""+":"+"\""+"您的验证码为"+s+"\""+"}";
//        System.out.println(str);
//        os.write(str.getBytes());
        mailClient.sendMail(email,"你好，亲爱的"+mobile,"您的验证码为"+s);
        redisTemplate.opsForValue().set(mobile,s,120, TimeUnit.SECONDS);
        return RespBean.success(vCode);
    }


}
