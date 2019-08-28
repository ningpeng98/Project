package com.test.java.chatroom.service;
/**
 * 业务实现：
 * 用户注册及登录
 * */
import com.test.java.chatroom.dao.AccountDao;
import com.test.java.chatroom.entity.User;

public class AccountService {
    private AccountDao accountdao = new AccountDao();
    //1.用户登录
    public boolean userLogin(String userName,String password){
        User user = accountdao.userLogin(userName,password);
        if(user == null){
            return false;
        }
        return true;
    }
    //2.用户注册
    public boolean userRegister(String userName,String password){
        User user = new User();
        user.setUserName(userName);
        user.setPassword(password);
        return accountdao.userRegister(user);
    }
}
