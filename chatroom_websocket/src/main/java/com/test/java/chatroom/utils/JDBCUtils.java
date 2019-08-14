package com.test.java.chatroom.utils;

import java.sql.*;
import java.util.Properties;

public class JDBCUtils {
    //加载一次的属性
    private static String driverName;
    private static String url;
    private static String userName;
    private static String password;

    static{
        Properties properties = CommUtils.loadProperties("db.properties");
        driverName = properties.getProperty("driverName");
        url = properties.getProperty("url");
        userName = properties.getProperty("userName");
        password = properties.getProperty("password");
        //参数加载完毕，开始流程
        //1.加载驱动
        try {
            Class.forName(driverName);
        } catch (ClassNotFoundException e) {
            System.err.println("加载数据库驱动出错！");
        }
    }
    //2.获取数据库连接
    public static Connection getConnection(){
        try {
            return DriverManager.getConnection(url,userName,password);
        } catch (SQLException e) {
            System.err.println("获取数据库连接出错！");
            e.printStackTrace();
        }
        return null;

    }

    //3.关闭数据库资源操作
    //针对数据库表的不同操作而使用不同的方法
    public static void closeResources(Connection connection,
                                      Statement statement,
                                      ResultSet resultSet){
        closeResources(connection,statement);

        if (resultSet != null) {
            try {
                resultSet.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }

    public static void closeResources(Connection connection,
                                     Statement statement){
        if(connection != null){
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (statement != null) {
            try {
                statement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }
}
