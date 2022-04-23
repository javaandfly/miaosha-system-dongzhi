package com.dong.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@AllArgsConstructor
public enum RespBeanEnum {
    SUCCESS(200,"SECCESS"),
    ERROR(500,"服务器异常"),
    LOGIN2_ERROR(500230,"该用户已存在"),
    LOGIN_ERROR(500210,"用户名或者密码错误"),
    Mobile_ERROR(500211,"手机号格式错误"),
    BIND_ERROR(500222,"参数校验异常"),
    SESSION_ERROR(500214,"用户不存在"),
    EMPTY_STOCK(500500,"库存不足"),
    REPEATE_ERROR(500501,"该商品每人限购一件"),
    REQUEST_ERROR(500111,"验证码发送失败，请重试"),
    CODE_ERROR(500121,"验证码已过期，请重新发送"),
    CODE02_ERROR(500121,"验证码不正确，请检查验证码"),
    //订单模块5003xx
    ORDER_NOT_EXIST(500300, "订单信息不存在");

    private final Integer code;
    private final String message;
}
