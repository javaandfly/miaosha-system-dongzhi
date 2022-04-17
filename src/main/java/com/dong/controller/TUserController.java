package com.dong.controller;


import com.dong.pojo.SkUser;
import com.dong.vo.RespBean;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author DongZhi
 * @since 2022-02-22
 */
@Controller
@RequestMapping("/user")
public class TUserController {
    /**
     * 测试
     * @param user
     * @return
     */
    @RequestMapping("/info")
    public RespBean info(SkUser user){
        return  RespBean.success(user);
    }
}
