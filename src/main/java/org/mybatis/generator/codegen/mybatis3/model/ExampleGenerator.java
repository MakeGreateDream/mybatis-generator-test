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
package org.mybatis.generator.codegen.mybatis3.model;

import org.mybatis.generator.api.FullyQualifiedTable;
import org.mybatis.generator.api.dom.java.*;
import org.mybatis.generator.codegen.AbstractJavaGenerator;
import org.mybatis.generator.codegen.mybatis3.javamapper.elements.*;
import org.mybatis.generator.config.PropertyRegistry;
import org.mybatis.generator.internal.DefaultCommentGenerator;

import java.util.ArrayList;
import java.util.List;

import static org.mybatis.generator.internal.util.StringUtility.stringHasValue;
import static org.mybatis.generator.internal.util.messages.Messages.getString;

/**
 *
 *
 * 生成Example类
 * @author Jeff Butler
 * 
 */
public class ExampleGenerator extends AbstractJavaGenerator {

    private boolean generateForJava5;

    public ExampleGenerator() {
        super();
    }


    /**
     *构建需要的类
     * TODO 增加方法
     *@author: Weiyf
     *Date: 2017/11/2 17:43
     */
    @Override
    public List<CompilationUnit> getCompilationUnits() {

        List<CompilationUnit> answer = new ArrayList<CompilationUnit>();

        FullyQualifiedTable table = introspectedTable.getFullyQualifiedTable();
        progressCallback.startTask(getString("Progress.6", table.toString()));

        /** DOC注释生成工具类**/
        DefaultCommentGenerator commentGenerator = new DefaultCommentGenerator();

        /** 生成service接口类**/
        Interface interfazeService = createService(table,commentGenerator);

        /** 生成serviceImpl类**/
        TopLevelClass topLevelClass = createServiceImpl(table,commentGenerator,interfazeService);

        if (context.getPlugins().modelExampleClassGenerated(
                topLevelClass, introspectedTable)) {
            answer.add(interfazeService);
            answer.add(topLevelClass);
        }

        return answer;
    }


    /**
     *构建service接口类
     *
     *@author: Weiyf
     *Date: 2017/11/2 17:44
     */
    private Interface createService(FullyQualifiedTable table,DefaultCommentGenerator commentGenerator){

        /** 生成service接口类**/
        FullyQualifiedJavaType serviceType = new FullyQualifiedJavaType(
                introspectedTable.getServiceType());
        Interface interfazeService = new Interface(serviceType);
        interfazeService.setVisibility(JavaVisibility.PUBLIC);
        interfazeService.addJavaDocLine(commentGenerator.addJavaFunctionLine(interfazeService.getType().getShortName()));

        /** 添加方法**/
        addSelectByPrimaryKeyMethod(interfazeService);
        addInsertMethod(interfazeService);
        addUpdateByExampleSelectiveMethod(interfazeService);
        addDeleteByExampleMethod(interfazeService);

        return interfazeService;
    }


    /**
     *构建service实现类
     *
     *@author: Weiyf
     *Date: 2017/11/2 17:50
     */
    private TopLevelClass createServiceImpl(FullyQualifiedTable table,
                                            DefaultCommentGenerator commentGenerator,
                                            Interface interfazeService){
        /** TODO mapper接口名**/
        char[] ch = new FullyQualifiedJavaType(introspectedTable.getMyBatis3JavaMapperType()).getShortName().toCharArray();
        ch[0] += 32;
        String mapperName = String.valueOf(ch);

        FullyQualifiedJavaType type = new FullyQualifiedJavaType(
                introspectedTable.getServiceImplType());
        TopLevelClass topLevelClass = new TopLevelClass(type);
        topLevelClass.addJavaDocLine(commentGenerator.addJavaFunctionLine(type.getShortName()));

        String rootInterfaceService = introspectedTable.getServiceType();
        if (!stringHasValue(rootInterfaceService)) {
            rootInterfaceService = context.getJavaClientGeneratorConfiguration().getProperty(PropertyRegistry.ANY_ROOT_INTERFACE);
        }

        if (stringHasValue(rootInterfaceService)) {
            FullyQualifiedJavaType fqjt = new FullyQualifiedJavaType(rootInterfaceService);
            topLevelClass.addSuperInterface(fqjt);
        }

        /** 引入jar包**/
        topLevelClass.addImportedType(introspectedTable.getServiceType());
        topLevelClass.addImportedType(introspectedTable.getMyBatis3JavaMapperType());
        topLevelClass.addImportedType(introspectedTable.getBaseRecordType());
        topLevelClass.addImportedType("org.springframework.beans.factory.annotation.Autowired");
        topLevelClass.addImportedType("org.springframework.stereotype.Service");

        /** 增加注解**/
        topLevelClass.addAnnotation("@Service");

        /** service方法作用域**/
        topLevelClass.setVisibility(JavaVisibility.PUBLIC);

        /** 引入mapper类**/
        Field field = new Field();
        field.addAnnotation("@Autowired");
        field.setVisibility(JavaVisibility.PRIVATE);
        field.setType(new FullyQualifiedJavaType(introspectedTable.getMyBatis3JavaMapperType()));

        /** TODO 设置mapper接口方法别名**/
        field.setName(mapperName);
        commentGenerator.addFieldComment(field, introspectedTable);
        topLevelClass.addField(field);

        /** 实现父类方法**/
        for(Method method : interfazeService.getMethods()){
            Method newMethod = new Method();
            newMethod.addAnnotation("@Override");
            newMethod.setVisibility(JavaVisibility.PUBLIC);
            newMethod.setName(method.getName());
            newMethod.setReturnType(method.getReturnType());

            String bodyLine = "return " + mapperName + "." + newMethod.getName();

            String paramStr = null;
            for(Parameter param : method.getParameters()){

                if(paramStr == null){
                    paramStr = param.getName();
                } else {
                    paramStr = paramStr + "," + param.getName();
                }

                /** 增加方法入参**/
                newMethod.addParameter(param);

            }

            newMethod.addBodyLine(bodyLine + "(" + paramStr + ");");
            commentGenerator.addGeneralMethodComment(newMethod,introspectedTable);

            topLevelClass.addMethod(newMethod);
        }

        return topLevelClass;
    }



    /** 生成方法类**/
    public void addDeleteByExampleMethod(Interface interfaze) {
        if (introspectedTable.getRules().generateDeleteByExample()) {
            AbstractJavaMapperMethodGenerator methodGenerator = new DeleteByPrimaryKeyMethodGenerator(false);
            initializeAndExecuteGenerator(methodGenerator, interfaze);
        }
    }

    public void addInsertMethod(Interface interfaze) {
        if (introspectedTable.getRules().generateInsert()) {
            AbstractJavaMapperMethodGenerator methodGenerator = new InsertMethodGenerator(false);
            initializeAndExecuteGenerator(methodGenerator, interfaze);
        }
    }

    public void addSelectByPrimaryKeyMethod(Interface interfaze) {
        if (introspectedTable.getRules().generateSelectByPrimaryKey()) {
            AbstractJavaMapperMethodGenerator methodGenerator = new SelectByPrimaryKeyMethodGenerator(false);
            initializeAndExecuteGenerator(methodGenerator, interfaze);
        }
    }

    public void addUpdateByExampleSelectiveMethod(Interface interfaze) {
        if (introspectedTable.getRules().generateUpdateByExampleSelective()) {
            AbstractJavaMapperMethodGenerator methodGenerator = new UpdateByExampleSelectiveMethodGenerator();
            initializeAndExecuteGenerator(methodGenerator, interfaze);
        }
    }

    protected void initializeAndExecuteGenerator(
            AbstractJavaMapperMethodGenerator methodGenerator,
            Interface interfaze) {
        methodGenerator.setContext(context);
        methodGenerator.setIntrospectedTable(introspectedTable);
        methodGenerator.setProgressCallback(progressCallback);
        methodGenerator.setWarnings(warnings);
        methodGenerator.addInterfaceElements(interfaze);
    }

    /**END**/


}
