package com.test.java.chatroom.utils;

import com.test.java.chatroom.entity.User;
import org.junit.Assert;
import org.junit.Test;

import java.util.Properties;

import static org.junit.Assert.*;

public class CommUtilsTest {

    @Test
    public void loadProoerties() {
        String fileName = "db.properties";
        //加载配置
        Properties properties = CommUtils.loadProperties(fileName);
        //获取配置
        System.out.println(properties);
        String url = properties.getProperty("url");
        Assert.assertNotNull(url);
    }

    @Test
    //将对象变成Json对象
    public void gsonTest1(){
        User user = new User();
        user.setId(10);
        user.setUserName("test");
        user.setPassword("123");
        String jsonStr = CommUtils.Object2Json(user);
        System.out.println(jsonStr);

    }


    @Test
    public void gsonTest2(){
        String jsonStr = "{\"id\":10,\"userName\":\"test\",\"password\":\"123\"}";
        User user = (User) CommUtils.Json2Object(jsonStr,User.class);
        System.out.println(user);
    }
}