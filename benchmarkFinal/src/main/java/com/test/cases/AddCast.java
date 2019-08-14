package com.test.cases;


import com.test.Case;
import com.test.annotations.Benchmark;
import com.test.annotations.Measurement;

/**
 *
 *  String的"+"操作与StringBuilder的append()方法对比
 * */
@Measurement(iterations = 10,group = 3)
public class AddCast  {
    private static String StringAdd(){
        String s = "";
        for (int i = 0; i < 10; i++) {
            s+=i;
        }
        return s;
    }
    private static String StringBuilderAdd(){
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 10; i++) {
            sb.append(i);
        }
        return sb.toString();
    }

    @Benchmark
    public void testStringAdd(){
        StringAdd();

    }


    @Benchmark
    public void testStringBuilderAdd(){
        StringBuilderAdd();
    }



}
