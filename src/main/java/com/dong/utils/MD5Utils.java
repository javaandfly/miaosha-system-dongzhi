package com.dong.utils;


import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.stereotype.Component;


/**
 * MD5工具类
 * 加密
 * 2022/2/22
 */
@Component
public class MD5Utils {
    public static String md5(String src){
        return DigestUtils.md5Hex(src);
    }
    //掩码 这个salt是前端到后端
    private static final String salt="1a2b3c4d";
    //前端接受的数据进行一次加工
    public static String inputPassFromPASS(String inputPass){
        String str =""+ salt.charAt(0)+salt.charAt(2)+inputPass+salt.charAt(2)+salt.charAt(0);
        return md5(str);
    }
    //这个里面的salt是数据库里面的，二次加工，这个时候就可以和数据库中的数据进行比较
    public static String fromPassToDbPass(String fromPass,String salt){
        String str=""+salt.charAt(0)+salt.charAt(2)+fromPass+salt.charAt(2)+salt.charAt(0);
        return md5(str);
    }
    //调用上面的两个方法 返回的就是两次加密后的掩码
    public static String inputPassToDbPass(String inputPass,String salt){
        String fromPass = inputPassFromPASS(inputPass);
        String dbPass = fromPassToDbPass(fromPass, salt);
        return dbPass;

    }
        //测试
    public static void main(String[] args) {
//      86265dfdfb65c9b4a0bb301e8ecce027
        System.out.println(inputPassFromPASS("123456"));
        //3179da7eff2fc7a63428055b4cf175f4
        System.out.println(fromPassToDbPass("86265dfdfb65c9b4a0bb301e8ecce027", "1a2b3c4d"));
        //3179da7eff2fc7a63428055b4cf175f4
        System.out.println(inputPassToDbPass("123456", "1a2b3c4d"));


    }
}
