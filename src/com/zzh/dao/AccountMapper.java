package com.zzh.dao;

import com.zzh.Account;
import org.apache.ibatis.annotations.Param;

public interface AccountMapper {
    /**
  * 
  * 根据条件统计数量
  * 
  * @author xxx
  * Date 2017-10-31 20:48:19
  */
    int countByExample(Account example);

    /**
  * 
  * 根据条件删除记录
  * 
  * @author xxx
  * Date 2017-10-31 20:48:19
  */
    int deleteByExample(Account example);

    /**
  * 
  * 插入记录
  * 
  * @author xxx
  * Date 2017-10-31 20:48:19
  */
    int insert(Account record);

    /**
  * 
  * 根据主键获取一条数据库记录
  * 
  * @author xxx
  * Date 2017-10-31 20:48:19
  */
    Account selectByPrimaryKey(Integer id);

    /**
  * 
  * 根据条件更新记录
  * 
  * @author xxx
  * Date 2017-10-31 20:48:19
  */
    int updateByExampleSelective(@Param("record") Account record);
}