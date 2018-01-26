/**
 *    Copyright 2006-2015 the original author or authors.
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */
package org.mybatis.generator.api;

import com.mysql.jdbc.StringUtils;
import org.mybatis.generator.config.*;
import org.mybatis.generator.exception.InvalidConfigurationException;
import org.mybatis.generator.internal.DefaultShellCallback;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.mybatis.generator.internal.util.messages.Messages.getString;

/**
 * TODO 启动方法
 * 
 * @author Jeff Butler
 */
public class ShellRunner {

    /**是否覆盖已存在的文件**/
    private static final boolean OVERWRITE = true;

    private static final boolean VERBOSE = false ;

    /** 作者**/
    public static String AUTHOR = "xxx";

    /** 是否生成service文件**/
    public static Integer Create_Service_File = 1;

    /** jdbcDriver**/
    private static final String JDBC_CLASS = "com.mysql.jdbc.Driver";

    /** 默认表名分隔符，用于生成表别名**/
    private static final String DELIMITER = "_";

    /** 启动方法**/
    public static void run(SimplConfiguration simplConfiguration) {

        List<String> warnings = new ArrayList<>();

        try {

            /** 赋值**/
            AUTHOR = simplConfiguration.getAuthor();

            Create_Service_File = simplConfiguration.getMakeServiceFile();

            /** 第一个context里存放的是配置相关内容**/
            Context context = new Context(ModelType.CONDITIONAL);

            /** 1.数据库配置**/
            JDBCConnectionConfiguration jdbcConfig = new JDBCConnectionConfiguration();
            jdbcConfig.setDriverClass(JDBC_CLASS);
            jdbcConfig.setConnectionURL(simplConfiguration.getConnectionURL());
            jdbcConfig.setUserId(simplConfiguration.getUserId());
            jdbcConfig.setPassword(simplConfiguration.getPassword());
            context.setJdbcConnectionConfiguration(jdbcConfig);

            /** 2.表配置**/
            List<TableConfiguration> tables = new ArrayList<>();
            String[] tableNames = simplConfiguration.getTableName().split(",");
            String[] modelNames = simplConfiguration.getModelName().split(",");
            String[] fieldAlias = simplConfiguration.getUseFieldAlias().split(",");

            for(int i=0; i<tableNames.length; i++){
                String tableName = tableNames[i];

                /** 只有一个值，生成的文件放在同一个模块下**/
                String modelName = modelNames.length == 1 ? modelNames[0] : modelNames[i];

                /** 只有一个值，所有文件查询sql都按照第一个规则来**/
                String userFieldAlias = fieldAlias.length == 1 ? fieldAlias[0] : fieldAlias[i];


                /** 2.1设置表昵称**/
                String nickName = "";
                for(String nick : tableName.split(DELIMITER)){
                    if(StringUtils.isNullOrEmpty(nick)){
                        continue;
                    }
                    char[] nickChar = nick.toCharArray();
                    char firstWord = nickChar[0];
                    if((int)'a' <= firstWord && firstWord <= (int)'z' ){
                        nickChar[0] -= 32;
                    }
                    nickName += String.valueOf(nickChar);
                }
                TableConfiguration tableConfiguration = new TableConfiguration(new Context(ModelType.CONDITIONAL));

                tableConfiguration.setTableName(tableName);
                tableConfiguration.setDomainObjectName(nickName);
                tableConfiguration.setModelName(modelName);
                tableConfiguration.setFieldAlias(userFieldAlias);
                tables.add(tableConfiguration);
            }
            context.setTableConfiguration(tables);

            /** 3.Mapper文件生成路径配置**/
            SqlMapGeneratorConfiguration sqlMapConfig = new SqlMapGeneratorConfiguration();
            sqlMapConfig.setTargetPackage(simplConfiguration.getMapperTargetPackage());
            sqlMapConfig.setTargetProject(simplConfiguration.getTargetProject());
            sqlMapConfig.addProperty("enableSubPackages","true");

            context.setSqlMapGeneratorConfiguration(sqlMapConfig);

            /** 4.javaModel文件生成路径配置**/
            JavaModelGeneratorConfiguration javaModelConfig = new JavaModelGeneratorConfiguration();
            javaModelConfig.setTargetPackage(simplConfiguration.getModelTargetPackage());
            javaModelConfig.setTargetProject(simplConfiguration.getTargetProject());
            javaModelConfig.addProperty("trimStrings","true");
            javaModelConfig.addProperty("enableSubPackages","true");

            context.setJavaModelGeneratorConfiguration(javaModelConfig);

            /** 5.xml文件生成路径配置**/
            JavaClientGeneratorConfiguration javaClientConfig = new JavaClientGeneratorConfiguration();
            javaClientConfig.setTargetPackage(simplConfiguration.getXmlTargetPackage());
            javaClientConfig.setTargetProject(simplConfiguration.getTargetProject());
            javaClientConfig.setConfigurationType("XMLMAPPER");
            javaClientConfig.addProperty("enableSubPackages","true");
            context.setJavaClientGeneratorConfiguration(javaClientConfig);

            /** 6.java类解析器配置**/
            JavaTypeResolverConfiguration javaTypeConfig = new JavaTypeResolverConfiguration();
            javaTypeConfig.addProperty("forceBigDecimals","false");
            context.setJavaTypeResolverConfiguration(javaTypeConfig);

            /** 7.其他项配置**/
            context.setId("DB2Tables");
            context.setBeginningDelimiter("\"");
            context.setEndingDelimiter("\"");
            context.setTargetRuntime("Mybatis3");


            /** 初始化配置对象**/
            Configuration config = new Configuration();
            config.addContext(context);


            DefaultShellCallback shellCallback = new DefaultShellCallback(OVERWRITE);

            MyBatisGenerator myBatisGenerator = new MyBatisGenerator(config, shellCallback, warnings);

            ProgressCallback progressCallback = VERBOSE ? new VerboseProgressCallback() : null;

            myBatisGenerator.generate(progressCallback, null, null);

        } catch (SQLException e) {
            writeLine("SQL异常，e" + e);
            return;
        } catch (IOException e) {
            writeLine("IO异常，e" + e);
            return;
        } catch (InvalidConfigurationException e) {
            writeLine(getString("Progress.16")); 
            for (String error : e.getErrors()) {
                writeLine(error);
            }
            return;
        } catch (InterruptedException e) {
            writeLine(e.getMessage());
        } catch (RuntimeException e){
            writeLine(e.getMessage());
        }

        for (String warning : warnings) {
            writeLine(warning);
        }

        if (warnings.size() == 0) {
            writeLine(getString("Progress.4")); 
        } else {
            writeLine();
            writeLine(getString("Progress.5")); 
        }
    }

    private static void usage() {
        String lines = getString("Usage.Lines"); 
        int iLines = Integer.parseInt(lines);
        for (int i = 0; i < iLines; i++) {
            String key = "Usage." + i; 
            writeLine(getString(key));
        }
    }

    private static void writeLine(String message) {
        System.out.println(message);
    }

    private static void writeLine() {
        System.out.println();
    }

    /**
     *
     *检验string类型值不为空
     *@author: Weiyf
     *Date: 2017/10/31 11:12
     */
    public static boolean isBlank(String var){
        return var == null || var == "";
    }
}
