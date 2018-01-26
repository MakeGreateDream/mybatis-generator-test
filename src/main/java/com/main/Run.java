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
        simplConfiguration.setConnectionURL("xxx");
        simplConfiguration.setUserId("xxx");
        simplConfiguration.setPassword("xxx");
        simplConfiguration.setModelTargetPackage("com.test.cms");

        /** 是否创建service文件，0：否；1：创建**/
        simplConfiguration.setMakeServiceFile(1);

        /** 项目绝对路径**/
        simplConfiguration.setTargetProject("D:\\project\\mybatis-generator-custom");

        /** 表名，多个用英文半角逗号隔开**/
        simplConfiguration.setTableName("xxx,xxx");

        /** 模块名，1-n(n=tableName的个数),name支持a、a.b(a模块下的b模块) **/
        simplConfiguration.setModelName("xxx,xxx.xx");

        /** 生成的Sql是否使用别名，0:否，1：使用。支持1-n,n=tableName的个数**/
        simplConfiguration.setUseFieldAlias("0,1");

        ShellRunner.run(simplConfiguration);
    }

}
