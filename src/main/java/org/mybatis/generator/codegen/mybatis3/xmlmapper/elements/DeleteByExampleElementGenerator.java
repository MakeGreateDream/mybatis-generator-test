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

import org.mybatis.generator.api.dom.OutputUtilities;
import org.mybatis.generator.api.dom.xml.Attribute;
import org.mybatis.generator.api.dom.xml.TextElement;
import org.mybatis.generator.api.dom.xml.XmlElement;
import org.mybatis.generator.codegen.mybatis3.MyBatis3FormattingUtilities;

/**
 * 
 * @author Jeff Butler
 * 
 */
public class DeleteByExampleElementGenerator extends
        AbstractXmlElementGenerator {

    public DeleteByExampleElementGenerator() {
        super();
    }

    @Override
    public void addElements(XmlElement parentElement) {
        XmlElement answer = new XmlElement("delete");

        String fqjt = introspectedTable.getBaseRecordType();

        answer.addAttribute(new Attribute(
                "id", introspectedTable.getDeleteStatementId()));
        answer.addAttribute(new Attribute("parameterType", fqjt));

        context.getCommentGenerator().addComment(answer);

        StringBuilder sb = new StringBuilder();
        sb.append("delete from ");
        sb.append(introspectedTable
                .getAliasedFullyQualifiedTableNameAtRuntime());

        sb.append(OutputUtilities.newLine());
        OutputUtilities.xmlIndent(sb,2);
        sb.append(" where ");
        sb.append(MyBatis3FormattingUtilities.getAliasedEscapedColumnName(introspectedTable.getAllColumns().get(0)));
        sb.append(" = ");
        sb.append(MyBatis3FormattingUtilities.getParameterClause(introspectedTable.getAllColumns().get(0)));
        answer.addElement(new TextElement(sb.toString()));


        if (context.getPlugins().sqlMapDeleteByExampleElementGenerated(
                answer, introspectedTable)) {
            //xml方法换行
            parentElement.addElement(new TextElement(OutputUtilities.newLine()));
            parentElement.addElement(answer);
        }
    }
}
