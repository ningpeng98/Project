package com.test.java.chatroom.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

//封装基础工具方法，如加载配置文件，json序列化等
public class CommUtils {
    //json使用
    private static final Gson gson = new GsonBuilder().create();
    private CommUtils(){}
        /**
         * 根据指定的文件名加载配置文件
         * fileName:配置文件的名字
         * */
        public static Properties loadProperties(String fileName){
            //Properties的load()方法可将一个输入流的所有内容加载到当前对象中
            Properties properties = new Properties();
            //获取反射对象.获取类加载器.获取当前类加载器下所有和它同目录的文件
            //获取到当前配置文件夹下的文件输入流
            InputStream in = CommUtils.class.getClassLoader().getResourceAsStream(fileName);
            //加载配置文件中的所有内容
            try {
                properties.load(in);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return properties;
        }


        //对象转换成字符串
        public static String Object2Json(Object obj){

            return gson.toJson(obj);
        }
        public static Object Json2Object(String jsonStr,Class objClass){

            return gson.fromJson(jsonStr,objClass);
        }

        public static boolean strIsNull(String str){
            return str == null || str.equals("");
        }


}
