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
package org.mybatis.generator.config;

import org.mybatis.generator.api.dom.xml.Attribute;
import org.mybatis.generator.api.dom.xml.Document;
import org.mybatis.generator.api.dom.xml.XmlElement;
import org.mybatis.generator.codegen.XmlConstants;
import org.mybatis.generator.exception.InvalidConfigurationException;

import java.util.ArrayList;
import java.util.List;

import static org.mybatis.generator.internal.util.StringUtility.stringHasValue;
import static org.mybatis.generator.internal.util.messages.Messages.getString;

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

    /** 表别名,多个表用英文半角逗号分隔**/
    private String tableNickName;

    /** model包名 只定义基础包名即可，其他包名会自动生成**/
    private String modelTargetPackage;

    /** mapper包名**/
    private String mapperTargetPackage;

    /** xml包名**/
    private String xmlTargetPackage;

    /** 生成文件的目标项目路径（绝对路径）**/
    private String targetProject;

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

    public String getTableNickName() {
        return tableNickName;
    }

    public void setTableNickName(String tableNickName) {
        this.tableNickName = tableNickName;
    }

    public String getModelTargetPackage() {
        return modelTargetPackage;
    }

    public void setModelTargetPackage(String modelTargetPackage) {
        this.modelTargetPackage = modelTargetPackage;
    }

    public String getMapperTargetPackage() {
        return mapperTargetPackage;
    }

    public void setMapperTargetPackage(String mapperTargetPackage) {
        this.mapperTargetPackage = mapperTargetPackage;
    }

    public String getXmlTargetPackage() {
        return xmlTargetPackage;
    }

    public void setXmlTargetPackage(String xmlTargetPackage) {
        this.xmlTargetPackage = xmlTargetPackage;
    }

    public String getTargetProject() {
        return targetProject;
    }

    public void setTargetProject(String targetProject) {
        this.targetProject = targetProject;
    }
}
