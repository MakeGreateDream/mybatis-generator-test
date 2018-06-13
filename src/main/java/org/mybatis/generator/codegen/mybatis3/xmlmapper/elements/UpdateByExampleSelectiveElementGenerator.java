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
package org.mybatis.generator.codegen.mybatis3.xmlmapper.elements;

import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.dom.OutputUtilities;
import org.mybatis.generator.api.dom.xml.Attribute;
import org.mybatis.generator.api.dom.xml.TextElement;
import org.mybatis.generator.api.dom.xml.XmlElement;
import org.mybatis.generator.codegen.mybatis3.MyBatis3FormattingUtilities;
import org.mybatis.generator.utils.KeyWorldUtils;

/**
 *
 * @author Jeff Butler
 *
 */
public class UpdateByExampleSelectiveElementGenerator extends
        AbstractXmlElementGenerator {

    public UpdateByExampleSelectiveElementGenerator() {
        super();
    }

    /** TODO mapper内容**/
    @Override
    public void addElements(XmlElement parentElement) {
        XmlElement answer = new XmlElement("update"); //$NON-NLS-1$

        answer.addAttribute(new Attribute(
                        "id", introspectedTable.getUpdateStatementId())); //$NON-NLS-1$

        answer.addAttribute(new Attribute("parameterType", introspectedTable.getBaseRecordType())); //$NON-NLS-1$ //$NON-NLS-2$

        context.getCommentGenerator().addComment(answer);

        StringBuilder sb = new StringBuilder();
        sb.append("update "); //$NON-NLS-1$
        sb.append(introspectedTable
                .getAliasedFullyQualifiedTableNameAtRuntime());
        answer.addElement(new TextElement(sb.toString()));

        XmlElement dynamicElement = new XmlElement("set"); //$NON-NLS-1$
        answer.addElement(dynamicElement);

        //有无修改时间
        boolean flag = false;
        for (IntrospectedColumn introspectedColumn : introspectedTable
                .getAllColumns()) {

            if("update_time".equals(
                    MyBatis3FormattingUtilities.getAliasedEscapedColumnName(introspectedColumn))){
                flag = true;
                continue;
            }

            XmlElement isNotNullElement = new XmlElement("if"); //$NON-NLS-1$
            sb.setLength(0);
            sb.append(introspectedColumn.getJavaProperty());
            sb.append(" != null");

            //字符串类型增加不为空字符串判断
            if(introspectedColumn.getJdbcTypeName().contains("CHAR")){
                sb.append(" and ");
                sb.append(introspectedColumn.getJavaProperty());
                sb.append(" != '' ");
            }

            isNotNullElement.addAttribute(new Attribute("test", sb.toString())); //$NON-NLS-1$
            dynamicElement.addElement(isNotNullElement);

            sb.setLength(0);

            //TODO update关键字转换
            String escColumnName = KeyWorldUtils.changeKeyWord(MyBatis3FormattingUtilities
                    .getAliasedEscapedColumnName(introspectedColumn));
            sb.append(escColumnName);
            sb.append(" = "); //$NON-NLS-1$
            sb.append(MyBatis3FormattingUtilities.getParameterClause(introspectedColumn)); //$NON-NLS-1$
            sb.append(',');

            isNotNullElement.addElement(new TextElement(sb.toString()));
        }

        /** 如果有更改时间字段，则放在set末尾**/
        if(flag){
            String endStr = "update_time = now()";
            TextElement endElement = new TextElement(endStr);
            dynamicElement.addElement(endElement);
        }

        StringBuilder whereSb = new StringBuilder();
        whereSb.append(" where ");
        whereSb.append(MyBatis3FormattingUtilities.getEscapedColumnName(introspectedTable.getAllColumns().get(0)));
        whereSb.append(" = ");
        whereSb.append(MyBatis3FormattingUtilities.getParameterClause(introspectedTable.getAllColumns().get(0)));

        answer.addElement(new TextElement(whereSb.toString()));

        if (context.getPlugins()
                .sqlMapUpdateByExampleSelectiveElementGenerated(answer,
                        introspectedTable)) {
            //xml方法换行
            parentElement.addElement(new TextElement(OutputUtilities.newLine()));
            parentElement.addElement(answer);
        }
    }
}
