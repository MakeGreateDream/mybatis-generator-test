#修改笔记
 
##新增生成类

    ExampleGenerator类中增加相应方法

##修改方法名称

    IntrospectedTable中增加相应常量，增加get&set方法;
    IntrospectedTable.calculateModelAttributes方法中添加需要追加的文件名；
    IntrospectedTable.calculateXmlAttributes方法中添加/修改xml中方法名称；
    
##删除jdbcType

    MyBatis3FormattingUtilities.getParameterClause
    
##修改xml文件

    XMLMapperGenerator
    
    
##启动方法

    Run方法
    
##生成的代码格式

    -com
      -test
        -dao
          -xxx
            xxxMapper.java
            xxxMapper.xml
            
        -model
          -po
            -xxx
              -xxxPo.java
              
        -service
          -xxx
            -impl
              xxxServiceImpl.java
            xxxService.java

##2018/06/13 更新日志
    
    增加生成mapper中update方法参数为空判断

##2018/01/26 更新日志
    
    新增模块名、SQL是否使用别名字段
 
##2017/11/27 更新日志

    新增：Run方法，运行项目
    修改：驼峰格式字段名格式化方式