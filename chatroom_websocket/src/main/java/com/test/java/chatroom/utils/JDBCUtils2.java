package com.test.java.chatroom.utils;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.pool.DruidDataSourceFactory;
import com.alibaba.druid.pool.DruidPooledConnection;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

//基于DruidDataSource
public class JDBCUtils2 {
    private static DruidDataSource dataSource;
    static{
        Properties properties = CommUtils.
                loadProperties("datasource.properties");
        try {
            //1.数据源加载，相当于注册驱动
            dataSource = (DruidDataSource)
                    DruidDataSourceFactory.createDataSource(properties);
        } catch (Exception e) {
            System.err.println("连接资源失败！");
        }
    }
    //2.获取连接
    public static DruidPooledConnection getConnection(){
        try {
            return dataSource.getConnection();
        } catch (SQLException e) {
            System.err.println("获取连接失败！");
        }
        return null;
    }
    //3.关闭资源
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


}
