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
package org.mybatis.generator.codegen.mybatis3.xmlmapper.elements;

import static org.mybatis.generator.internal.util.StringUtility.stringHasValue;

import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.dom.OutputUtilities;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
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
public class SelectByPrimaryKeyElementGenerator extends
        AbstractXmlElementGenerator {

    public SelectByPrimaryKeyElementGenerator() {
        super();
    }

    @Override
    public void addElements(XmlElement parentElement) {
        XmlElement answer = new XmlElement("select"); //$NON-NLS-1$

        answer.addAttribute(new Attribute(
                "id", introspectedTable.getSelectStatementId())); //$NON-NLS-1$

        answer.addAttribute(new Attribute("resultType",
                new FullyQualifiedJavaType(introspectedTable.getBaseRecordType()).getFullyQualifiedName()));

        String parameterType;
        if (introspectedTable.getRules().generatePrimaryKeyClass()) {
            parameterType = introspectedTable.getPrimaryKeyType();
        } else {
            // PK fields are in the base class. If more than on PK
            // field, then they are coming in a map.
            if (introspectedTable.getPrimaryKeyColumns().size() > 1) {
                parameterType = introspectedTable.getBaseRecordType(); //$NON-NLS-1$
            } else {
                parameterType = introspectedTable.getPrimaryKeyColumns().get(0)
                        .getFullyQualifiedJavaType().toString();
            }
        }

        answer.addAttribute(new Attribute("parameterType", //$NON-NLS-1$
                parameterType));

        context.getCommentGenerator().addComment(answer);

        StringBuilder sb = new StringBuilder();
        sb.append("select "); //$NON-NLS-1$

        if (stringHasValue(introspectedTable
                .getSelectByPrimaryKeyQueryId())) {
            sb.append('\'');
            sb.append(introspectedTable.getSelectByPrimaryKeyQueryId());
            sb.append("' as QUERYID,"); //$NON-NLS-1$
        }
        answer.addElement(new TextElement(sb.toString()));

        StringBuilder selectColunm = new StringBuilder();
        for (int i = 0; i < introspectedTable.getAllColumns().size(); i++) {
            IntrospectedColumn introspectedColumn = introspectedTable.getAllColumns().get(i);

            String escColumnName = KeyWorldUtils.changeKeyWord(MyBatis3FormattingUtilities
                    .getEscapedColumnName(introspectedColumn));

            String javaProperty = KeyWorldUtils.changeKeyWord(introspectedColumn.getJavaProperty());

            selectColunm.append(escColumnName);
            Boolean ailasFlag = "0".equals(introspectedTable.getTableConfiguration().getFieldAlias())? false : true;

            if(ailasFlag){
                selectColunm.append(" as ")
                        .append(javaProperty);
            }

            if (i + 1 < introspectedTable.getAllColumns().size()) {
                if (!introspectedTable.getAllColumns().get(i + 1).isIdentity()) {
                    selectColunm.append(", ");
                }
            }

            if (selectColunm.length() > 80) {
                answer.addElement(new TextElement(selectColunm.toString()));
                selectColunm.setLength(0);
                OutputUtilities.xmlIndent(selectColunm, 1);
            }

        }

        answer.addElement(new TextElement(selectColunm.toString()));

        sb.setLength(0);
        sb.append("from "); //$NON-NLS-1$
        sb.append(introspectedTable
                .getAliasedFullyQualifiedTableNameAtRuntime());
        answer.addElement(new TextElement(sb.toString()));

        boolean and = false;
        for (IntrospectedColumn introspectedColumn : introspectedTable
                .getPrimaryKeyColumns()) {
            sb.setLength(0);
            if (and) {
                sb.append("  and "); //$NON-NLS-1$
            } else {
                sb.append("where "); //$NON-NLS-1$
                and = true;
            }

            sb.append(MyBatis3FormattingUtilities
                    .getAliasedEscapedColumnName(introspectedColumn));
            sb.append(" = "); //$NON-NLS-1$
            sb.append(MyBatis3FormattingUtilities
                    .getParameterClause(introspectedColumn));
            answer.addElement(new TextElement(sb.toString()));
        }

        if (context.getPlugins()
                .sqlMapSelectByPrimaryKeyElementGenerated(answer,
                        introspectedTable)) {
            //xml方法换行
            parentElement.addElement(new TextElement(OutputUtilities.newLine()));
            parentElement.addElement(answer);
        }
    }
}
