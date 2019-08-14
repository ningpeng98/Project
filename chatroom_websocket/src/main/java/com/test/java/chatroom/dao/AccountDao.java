package com.test.java.chatroom.dao;

import com.test.java.chatroom.entity.User;
import org.apache.commons.codec.digest.DigestUtils;

import java.sql.*;

/**
 * 关于用户模块的Dao
 * 1.用户登录 select
 *
 * 2.用户注册 insert
 * */
public class AccountDao extends BaseDao {
    //1.用户登录 select
    public User userLogin(String userName,String password){
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        User user = null;
        try {
            connection = getConnection();
            String sql = "select * from user where username = ? and "+
                    "password = ?";
            statement = connection.prepareStatement(sql);
            statement.setString(1,userName);
            //密码加密
            statement.setString(2,DigestUtils.md5Hex(password));
            resultSet = statement.executeQuery();
            //如果resultSet不为空，将结果存入user
            if (resultSet.next()) {
                user = gerUserInfo(resultSet);
            }

        }catch (Exception e){
            System.err.println("查询用户信息出错！");
            e.printStackTrace();

        }finally {
            closeResources(connection,statement,resultSet);

        }
        return user;
    }

    //2.用户注册 insert
    public boolean userRegister(User user){
        String userName = user.getUserName();
        String password = user.getPassword();
        Connection connection = null;
        PreparedStatement statement = null;
        boolean isSuccess = false;
        try {
            connection = getConnection();
            String sql = "insert into user(username,password)"+"values (?,?)";
            //数据库执行sql后返回的行数
            statement = connection.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
            statement.setString(1,userName);
            statement.setString(2,DigestUtils.md5Hex(password));
            isSuccess = (statement.executeUpdate() == 1);
        }catch (Exception e){
            System.err.println("用户注册失败！");
            e.printStackTrace();
        }finally {
            closeResources(connection,statement);
        }
        return isSuccess;

    }

    //将数据表信息封装到User类中
    public User gerUserInfo(ResultSet resultSet) throws SQLException {
        User user = new User();
        user.setId(resultSet.getInt("id"));
        user.setUserName(resultSet.getString("username"));
        user.setPassword(resultSet.getString("password"));

        return user;
    }

}
