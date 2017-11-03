#修改笔记

##新增生成类 
    ExampleGenerator类中增加相应方法

##修改方法名称
    IntrospectedTable中增加相应常量，增加get&set方法;
    IntrospectedTable.calculateModelAttributes方法中添加需要追加的文件名；
    IntrospectedTable.calculateXmlAttributes方法中添加/修改xml中方法名称；
    
##删除jdbcType
    MyBatis3FormattingUtilities.getParameterClause