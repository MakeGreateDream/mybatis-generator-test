/**
 * Copyright 2006-2015 the original author or authors.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.mybatis.generator.internal;

import org.mybatis.generator.api.CommentGenerator;
import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.ShellRunner;
import org.mybatis.generator.api.dom.OutputUtilities;
import org.mybatis.generator.api.dom.java.*;
import org.mybatis.generator.api.dom.xml.TextElement;
import org.mybatis.generator.api.dom.xml.XmlElement;
import org.mybatis.generator.config.PropertyRegistry;
import org.mybatis.generator.internal.util.StringUtility;
import org.mybatis.generator.utils.DateTimeUtils;

import java.util.Properties;

import static org.mybatis.generator.internal.util.StringUtility.isTrue;

/**
 * The Class DefaultCommentGenerator.
 *
 * @author Jeff Butler
 */
public class DefaultCommentGenerator implements CommentGenerator {

    /**
     * The properties.
     */
    private Properties properties;

    /**
     * The suppress date.
     */
    private boolean suppressDate;

    /**
     * The suppress all comments.
     */
    private boolean suppressAllComments;

    /**
     * The addition of table remark's comments.
     * If suppressAllComments is true, this option is ignored
     */
    private boolean addRemarkComments;

    /**
     * Instantiates a new default comment generator.
     */
    public DefaultCommentGenerator() {
        super();
        properties = new Properties();
        suppressDate = false;
        suppressAllComments = false;
        addRemarkComments = false;
    }

    //java文件的注释，包上面的注释
    @Override
    public void addJavaFileComment(CompilationUnit compilationUnit) {
        return;
    }

    /**
     * Adds a suitable comment to warn users that the element was generated, and when it was generated.
     *
     * @param xmlElement the xml element
     */
    //xml注释
    @Override
    public void addComment(XmlElement xmlElement) {
        if (suppressAllComments) {
            return;
        }

//        xmlElement.addElement(new TextElement(addXmlLine()));

    }

    /** xml文件注释**/
    @Override
    public void addRootComment(XmlElement rootElement) {
        return ;
    }

    @Override
    public void addConfigurationProperties(Properties properties) {
        this.properties.putAll(properties);

        suppressDate = isTrue(properties
                .getProperty(PropertyRegistry.COMMENT_GENERATOR_SUPPRESS_DATE));

        suppressAllComments = isTrue(properties
                .getProperty(PropertyRegistry.COMMENT_GENERATOR_SUPPRESS_ALL_COMMENTS));

        addRemarkComments = isTrue(properties
                .getProperty(PropertyRegistry.COMMENT_GENERATOR_ADD_REMARK_COMMENTS));
    }

    /**
     * This method adds the custom javadoc tag for. You may do nothing if you do not wish to include the Javadoc tag -
     * however, if you do not include the Javadoc tag then the Java merge capability of the eclipse plugin will break.
     *
     * @param javaElement       the java element
     * @param markAsDoNotDelete the mark as do not delete
     */
    protected void addJavadocTag(JavaElement javaElement,
                                 boolean markAsDoNotDelete) {
        /*javaElement.addJavaDocLine(" *"); 
        StringBuilder sb = new StringBuilder();
        sb.append(" * "); 
        sb.append(MergeConstants.NEW_ELEMENT_TAG);
        if (markAsDoNotDelete) {
            sb.append(" do_not_delete_during_merge"); 
        }
        String s = getDateString();
        if (s != null) {
            sb.append(' ');
            sb.append(s);
        }
        javaElement.addJavaDocLine(sb.toString());*/
    }

    /**
     *Po类注释
     *
     *@author: Weiyf
     *Date: 2017/10/31 14:49
     */
    @Override
    public void addClassComment(InnerClass innerClass,
                                IntrospectedTable introspectedTable) {
        if (suppressAllComments) {
            return;
        }

        /** 导入包**/
        innerClass.addJavaDocLine("import io.swagger.annotations.ApiModelProperty;");
        innerClass.addJavaDocLine("import lombok.Getter;");
        innerClass.addJavaDocLine("import lombok.Setter;");
        innerClass.addJavaDocLine("import lombok.AllArgsConstructor;");
        innerClass.addJavaDocLine("import lombok.NoArgsConstructor;");
        innerClass.addJavaDocLine("import lombok.Builder;");

        innerClass.addJavaDocLine(addJavaFunctionLine(introspectedTable.getFullyQualifiedTable()));

        /** 增加类注解**/
        innerClass.addJavaDocLine("@Getter");
        innerClass.addJavaDocLine("@Setter");
        /*innerClass.addJavaDocLine("@AllArgsConstructor");
        innerClass.addJavaDocLine("@NoArgsConstructor");
        innerClass.addJavaDocLine("@Builder");*/

        addJavadocTag(innerClass, false);
    }

    @Override
    public void addModelClassComment(TopLevelClass topLevelClass,
                                     IntrospectedTable introspectedTable) {
        if (suppressAllComments || !addRemarkComments) {
            return;
        }

        topLevelClass.addJavaDocLine(addJavaDocLine(introspectedTable.getFullyQualifiedTable()+"2"));

        addJavadocTag(topLevelClass, true);

    }

    @Override
    public void addEnumComment(InnerEnum innerEnum,
                               IntrospectedTable introspectedTable) {
        if (suppressAllComments) {
            return;
        }
        innerEnum.addJavaDocLine(addJavaDocLine(introspectedTable.getFullyQualifiedTable()+"3"));

        addJavadocTag(innerEnum, false);
    }

    @Override
    public void addFieldComment(Field field,
            IntrospectedTable introspectedTable,
            IntrospectedColumn introspectedColumn) {
        if (suppressAllComments) {
            return;
        }
        field.addJavaDocLine("@ApiModelProperty( \"" + introspectedColumn.getRemarks() + "\")");

        addJavadocTag(field, false);

    }


    @Override
    public void addFieldComment(Field field, IntrospectedTable introspectedTable) {
        if (suppressAllComments) {
            return;
        }


        field.addJavaDocLine(addJavaDocLine(introspectedTable.getFullyQualifiedTable()+"5"));

        addJavadocTag(field, false);

    }

    /** 生成mapper对应方法和Example类方法注释**/
    @Override
    public void addGeneralMethodComment(Method method,
                                        IntrospectedTable introspectedTable) {
        if (suppressAllComments) {
            return;
        }
        String method_name = method.getName();
        String desc = "";
        if ("deleteByPrimaryKey".equals(method_name)) {
            desc = "根据主键删除数据库的记录";
        } else if ("insert".equals(method_name)) {
            desc = "插入记录";
        } else if ("selectByPrimaryKey".equals(method_name)) {
            desc = "根据主键获取一条数据库记录";
        } else if ("updateByPrimaryKey".equals(method_name)) {
            desc = "根据主键来更新数据库记录";
        } else if ("selectAll".equals(method_name)) {
            desc = "获取全部数据库记录";
        } else if("countByExample".equals(method_name)){
            desc = "根据条件统计数量";
        } else if("deleteByExample".equals(method_name)){
            desc = "根据条件删除记录";
        } else if("insertSelective".equals(method_name)){
            desc = "插入记录";
        } else if("selectByExample".equals(method_name)){
            desc = "根据条件查询记录";
        } else if("updateByExampleSelective".equals(method_name)
                || "updateByExample".equals(method_name)
                || "updateByPrimaryKeySelective".equals(method_name)){
            desc = "根据条件更新记录";
        }

        method.addJavaDocLine(addJavaDocLine(desc));

        addJavadocTag(method, false);
    }

    @Override
    public void addGetterComment(Method method,
                                 IntrospectedTable introspectedTable,
                                 IntrospectedColumn introspectedColumn) {
        if (suppressAllComments) {
            return;
        }
    }

    @Override
    public void addSetterComment(Method method,
                                 IntrospectedTable introspectedTable,
                                 IntrospectedColumn introspectedColumn) {
        if (suppressAllComments) {
            return;
        }
    }

    @Override
    public void addClassComment(InnerClass innerClass,
                                IntrospectedTable introspectedTable, boolean markAsDoNotDelete) {
        if (suppressAllComments) {
            return;
        }

        innerClass.addJavaDocLine(addJavaDocLine(introspectedTable.getFullyQualifiedTable()+"7"));
        addJavadocTag(innerClass, markAsDoNotDelete);
    }


    public String addJavaDocLine(Object str){
        String nextLine = System.getProperty("line.separator");
        StringBuilder sb = new StringBuilder();
        sb.append(nextLine + "    /**" + nextLine);
        sb.append("     * @author " + ShellRunner.AUTHOR + nextLine);
        sb.append("     * @Date " + DateTimeUtils.dateTimeSty() + nextLine);
        sb.append("     */");
        return sb.toString();
    }

    public String addJavaFunctionLine(Object str){
        String nextLine = System.getProperty("line.separator");
        StringBuilder sb = new StringBuilder();
        sb.append(nextLine + "/**" + nextLine);
        sb.append(" * @author " + ShellRunner.AUTHOR + nextLine);
        sb.append(" * @Date " + DateTimeUtils.dateTimeSty() + nextLine);
        sb.append(" */");
        return sb.toString();
    }

    public String addXmlLine(){
        String nextLine = System.getProperty("line.separator");

        StringBuilder sb = new StringBuilder();
        sb.append(nextLine + "");
        sb.append("   <!--");
        sb.append(" @author " + ShellRunner.AUTHOR );
        sb.append("  " + DateTimeUtils.dateTimeSty());
        sb.append("  -->");
        return sb.toString();
    }

}
