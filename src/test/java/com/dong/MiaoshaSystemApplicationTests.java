package com.dong;

import com.dong.config.MailClient;
import com.dong.mapper.SkGoodsMapper;
import com.dong.mapper.SkOrderMapper;
import com.dong.mapper. SkUserMapper;
import com.dong.pojo.SkUser;
import com.sun.xml.internal.fastinfoset.util.ValueArrayResourceException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.cache.CacheProperties;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.boot.test.context.SpringBootTest;


import javax.validation.Valid;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
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
    @Autowired
    MailClient mailClient;


    @Test
    void contextLoads()  {
        String str= "\""+"recvuser"+"\""+":"+"\""+"email"+"\""+
                "\n"+"\""+"date"+"\""+":"+"\""+"s"+"\"";
        System.out.println(str);
    }
    @Test
    public void client() throws IOException {
        Socket socket = null;
        OutputStream os = null;
            //1.创建Socket对象，指明服务器端的ip和端口号
            InetAddress inet = InetAddress.getByName("43.138.37.7");
            socket = new Socket(inet, 13000);
            //2.获取一个输出流，用于输出数据
            os = socket.getOutputStream();
            //3.写出数据的操作

            os.write("str".getBytes());
    }
    @Test
    public void clients(){
        mailClient.sendMail("979336542@qq.com","44","hello");
    }
}
