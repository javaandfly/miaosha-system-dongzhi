package com.dong.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RespBean {
    private  long code;
    private String message;
    private Object obj;

    /*
    成功的返回对象
     */
    //成功时返回的成功信息一个有参数一个无参数
    public static RespBean success(){
        return  new RespBean(RespBeanEnum.SUCCESS.getCode(),RespBeanEnum.SUCCESS.getMessage(),null);

    }
    public static RespBean success(Object obj){
        return  new RespBean(RespBeanEnum.SUCCESS.getCode(),RespBeanEnum.SUCCESS.getMessage(),obj);

    }
    /*
    失败
     */
    //失败时返回的成功信息一个有参数一个无参数

    public static RespBean error(RespBeanEnum respBeanEnum){
        return   new RespBean(respBeanEnum.getCode(),respBeanEnum.getMessage(),null);
    }
    public static  RespBean error(RespBeanEnum respBeanEnum,Object obj){
        return  new RespBean(respBeanEnum.getCode(),respBeanEnum.getMessage(),obj);
    }
}
