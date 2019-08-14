package com.test.java.chatroom.dao;

import com.test.java.chatroom.entity.User;
import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

public class AccountDaoTest {
    private AccountDao accountDao = new AccountDao();

    @Test
    public void userLogin() {
        User user = accountDao.userLogin("test","123");
        System.out.println(user);
        Assert.assertNotNull(user);
    }

    @Test
    public void userRegister() {
        User user = new User();
        user.setUserName("test2");
        user.setPassword("1234");
        boolean isSuccess = accountDao.userRegister(user);
        Assert.assertEquals(true,isSuccess);
    }

    @Test
    public void gerUserInfo() {
    }
}