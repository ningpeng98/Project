package com.test;

import com.test.annotations.Benchmark;
import com.test.annotations.Measurement;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

class CaseRunner{
    //默认配置
    private final int DEFAULT_ITERATIONS = 10;
    private final int DEFAULT_GROUP = 5;

    private final List<Case> caseList;
    public CaseRunner(List<Case> caseList) {
        this.caseList = caseList;
    }

    //TODO:没有把WarmUp预热考虑进来
    //每组实验前都应该预热
    public void run() throws InvocationTargetException, IllegalAccessException {
        for(Case bCase : caseList){
            int iterations = DEFAULT_ITERATIONS;
            int group = DEFAULT_GROUP;

            //先获取类级别的配置
            Measurement classMeasurement = bCase.getClass().getAnnotation(Measurement.class);
            if(classMeasurement != null){
                //若已被配置，直接赋值
                iterations = classMeasurement.iterations();
                group = classMeasurement.group();
            }
            //找到哪些方法是需要测试的方法
            Method[] methods = bCase.getClass().getMethods();
            for(Method method : methods){
                //取到每一个方法的注解，找出需要测试的方法
                Benchmark benchmark = method.getAnnotation(Benchmark.class);
                if(benchmark == null){
                    continue;
                }

                //获取需要测试方法的配置
                Measurement methodMeasurement = method.getAnnotation(Measurement.class);
                if(methodMeasurement != null){
                    iterations = methodMeasurement.iterations();
                    group = methodMeasurement.group();
                }

                //调用对象的实例测试方法测试
                runCase(bCase,method,iterations,group);

            }
        }

    }


    ////实例测试方法测试
    private void runCase(Case bCase,Method method,int iterations,int group) throws InvocationTargetException, IllegalAccessException {
        System.out.println(method.getName());
        for (int i = 0; i < group; i++) {
            System.out.printf("第%d次实验:",i);
            long t1 = System.nanoTime();
            for (int j = 0; j < iterations; j++) {
                //通过 Method.invoke()调用被注解的方法，即bCase
                method.invoke(bCase);
            }
            long t2 = System.nanoTime();
            System.out.printf("耗时：%d 纳秒%n",t2-t1);
        }
    }
}




//待测试Case加载
public class CaseLoader {
    public CaseRunner load() throws IOException, ClassNotFoundException, IllegalAccessException, InstantiationException {
        String pkgDot = "com.test.cases";
        String pkg = "com/test/cases";

        List<String> classNameList = new ArrayList<String>();
        /**
         * 1.根据一个固定类，找到类加载器
         * 2.根据类加载器找到类文件所在的路径
         * 3.扫描路径的所有类文件
         * */
        ClassLoader classLoader = this.getClass().getClassLoader();
        //获取"pkg"文件夹下的文件
        Enumeration<URL> urls = classLoader.getResources(pkg);
        while(urls.hasMoreElements()){
            //路径
            URL url = urls.nextElement();
            if(!url.getProtocol().equals("file")){
                //如果不是*.class文件，暂时不支持
                continue;
            }
            String dirname = URLDecoder.decode(url.getPath(),"UTF-8");
            File dir = new File(dirname);
            if(!dir.isDirectory()){
                //不是目录直接返回
                continue;
            }
            //扫描该目录下的所有*.class文件，作为所有的类文件
            File[] files = dir.listFiles();
            if(files == null){
                continue;
            }
            for(File file : files){
                String filename = file.getName();
                String className = filename.substring(0,filename.length()-6);
                classNameList.add(className);
            }
        }


        List<Case> caseList = new ArrayList<Case>();
        for(String className : classNameList){
            Class<?> cls = Class.forName(pkgDot+"."+className);
            if(hasInterface(cls,Case.class)){
                caseList.add((Case)cls.newInstance());
            }
        }
        return new CaseRunner(caseList);
    }


    private boolean hasInterface(Class<?> cls,Class<?> intf){
        //利用Case接口，找出需要的*.class文件
        Class<?>[] intfs = cls.getInterfaces();
        for (Class<?> i : intfs){
            if(i == intf){
                return true;
            }

        }
        return false;
    }

}
