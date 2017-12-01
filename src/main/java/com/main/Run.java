package com.main;

import org.mybatis.generator.api.ShellRunner;
import org.mybatis.generator.config.SimplConfiguration;

/**
 *运行方法
 *
 *@author: Weiyf
 *@Date: 2017/11/27 10:23
 */
public class Run {

    public static void main(String[] args) throws InterruptedException {
        long startTime = System.currentTimeMillis();

        shellRun();

        long endTime = System.currentTimeMillis();
        System.out.println("执行时长===》" + (endTime-startTime) / 1000 + "s");
    }

    /**
     *调用shellRunner方法
     *
     *@author: Weiyf
     *@Date: 2017/11/27 10:31
     */
    private static void shellRun(){
        SimplConfiguration simplConfiguration = new SimplConfiguration();
        simplConfiguration.setAuthor("xxx");
        simplConfiguration.setConnectionURL("jdbc:mysql://localhost:3306/test");
        simplConfiguration.setUserId("root");
        simplConfiguration.setPassword("1234");
        simplConfiguration.setTableName("t_user");

        simplConfiguration.setModelTargetPackage("com.test.cms");
        simplConfiguration.setMakeServiceFile(0);

        /** 多个项目在同一组时，只到组的路径**/
        simplConfiguration.setTargetProject("D:\\project\\mybatis-generator-custom");

        ShellRunner.run(simplConfiguration);
    }

}
