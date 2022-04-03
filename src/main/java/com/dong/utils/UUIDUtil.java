package com.dong.utils;

import java.util.UUID;

public class UUIDUtil {
    /**
     * Created by jiangyunxiong on 2018/5/22.
     * <p>
     * 唯一id生成类
     */


        public static String uuid() {
            return UUID.randomUUID().toString().replace("-", "");
        }
    }


