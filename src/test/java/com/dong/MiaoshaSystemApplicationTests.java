package com.dong;

import com.dong.mapper.SkGoodsMapper;
import com.dong.mapper.SkOrderMapper;
import com.dong.mapper. SkUserMapper;
import com.dong.pojo.SkUser;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.cache.CacheProperties;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.boot.test.context.SpringBootTest;


import java.sql.Wrapper;
import java.util.List;

@SpringBootTest
class MiaoshaSystemApplicationTests {

    @Autowired
    SkUserMapper UserMapper;
    @Autowired
    SkGoodsMapper skGoodsMapper;
    @Autowired
    SkOrderMapper skOrderMapper;
    @Test
    void contextLoads() {

    }

}
