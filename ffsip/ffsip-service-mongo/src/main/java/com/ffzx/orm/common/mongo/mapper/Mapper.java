package com.ffzx.orm.common.mongo.mapper;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/4/6.
 */
public interface Mapper<T> {
    int insert(T entity);

    int deleteByPrimaryKey(String id);

    int updateByPrimaryKey(T entity);

    int updateByPrimaryKeySelective(T entity);

    T selectByPrimaryKey(String id);

    List<T> selectByExample(Map<String,Object> example);

    int updateByExampleSelective(T entity, Object example);

    int selectCountByExample(Object example);

    List<T> select(T entity);

    T findByCode(String code);

    T findOneByProperty(String property,String value);

    List<T> findByProperty(String property,String value);
}
