package org.mybatis.generator.config;

import java.io.File;

/**
 *
 * 自定义配置类
 */
public class SimplConfiguration {

    /** 作者，必填**/
    private String author;

    /** 数据库连接地址**/
    private String connectionURL;

    /** 用户名**/
    private String userId;

    /** 密码**/
    private String password;

    /** 表名,多个表用英文半角逗号分隔**/
    private String tableName;

    /** 模块名，与表名数量相同，多个表用英文半角逗号分隔**/
    private String modelName;

    /** 是否创建service文件，默认创建**/
    private Integer makeServiceFile;

    /** 生成的sql是否使用别名**/
    private String useFieldAlias;

    /** model包名 只定义基础包名即可，其他包名会自动生成**/
    private String modelTargetPackage;

    /** mapper包名,设置model包名时自动生成**/
    private String mapperTargetPackage;

    /** xml包名,设置model包名时自动生成**/
    private String xmlTargetPackage;

    /** 生成文件的目标项目路径（绝对路径）默认加上 \src\main\java **/
    private String targetProject;

    /** 斜杠**/
    private static final String slash = File.separator;

    /** 代码生成目录**/
    private static final String srcMain = slash +"src" + slash + "main" + slash + "java";


    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getConnectionURL() {
        return connectionURL;
    }

    public void setConnectionURL(String connectionURL) {
        this.connectionURL = connectionURL;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getModelTargetPackage() {
        return modelTargetPackage;
    }

    public void setModelTargetPackage(String modelTargetPackage) {
        this.modelTargetPackage = modelTargetPackage;
        this.mapperTargetPackage = modelTargetPackage + ".dao";
        this.xmlTargetPackage = modelTargetPackage + ".dao";
    }

    public String getMapperTargetPackage() {
        return mapperTargetPackage;
    }

    public String getXmlTargetPackage() {
        return xmlTargetPackage;
    }

    public String getTargetProject() {
        return targetProject;
    }

    public void setTargetProject(String targetProject) {
        this.targetProject = targetProject + srcMain;
    }

    public Integer getMakeServiceFile() {
        return makeServiceFile;
    }

    public void setMakeServiceFile(Integer makeServiceFile) {
        this.makeServiceFile = makeServiceFile;
    }

    public String getModelName() {
        return modelName;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }

    public String getUseFieldAlias() {
        return useFieldAlias;
    }

    public void setUseFieldAlias(String useFieldAlias) {
        this.useFieldAlias = useFieldAlias;
    }
}
