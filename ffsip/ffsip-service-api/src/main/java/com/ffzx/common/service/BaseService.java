package com.ffzx.common.service;

import java.util.List;
import java.util.Map;

public interface BaseService<T, PK> {

    public int add(T entity);

    public int deleteById(String id);

    public int update(T entity);

    public int updateSelective(T entity);

    public T findById(String id);

    public List<T> selectByExample(Object example);

    public int updateByExample(T entity, Object example);

    public int countByExample(Object example);

    public T findByCode(String code);

    public List<T> selectByEntity(T entity);

}
