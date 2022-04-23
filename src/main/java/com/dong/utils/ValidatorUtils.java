package com.dong.utils;

import org.thymeleaf.util.StringUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 手机号码校验
 */
public class ValidatorUtils {
    //正则表达式校验
    /**
     * Pattern类用于创建一个正则表达式，也可以说是创建一个匹配模式，可以通过两个静态方法创建：
     * compile(String regex)和compile(String regex,int flags)，
     * 其中regex是正则表达式，flags为可选模式(如：Pattern.CASE_INSENSITIVE 忽略大小写)
     */
    private static final Pattern mobile_pattern = Pattern.compile("^1[3|4|5|7|8][0-9]{9}$");

    public static boolean isMobile(String mobile) {
        //判断传进来的账号是否为空 空的话返回false
        if (StringUtils.isEmpty(mobile)) {
            return false;
        }
        //matches 匹配   传入的mobile  进行校验  递归
        /**
         * Matcher类了，Pattern类中的matcher(CharSequence input)会返回一个Matcher对象。
         * Matcher类提供了对正则表达式的分组支持,以及对正则表达式的多次匹配支持，
         * 要想得到更丰富的正则匹配操作,那就需要将Pattern与Matcher联合使用。
         * Pattern pattern = Pattern.compile("Java");
         * String test1 = "Java";
         * String test2 = "Java1234";
         * String test3 = "1234Java"
         * Matcher matcher = pattern.matcher(test1);
         * System.out.println(matcher.matches());//返回true
         */
            Matcher matcher = mobile_pattern.matcher(mobile);

        return matcher.matches();
    }
}
