package com.ffzx.common.service.impl;

import com.ffzx.common.service.BaseService;
import com.ffzx.ffsip.util.CodeGenerator;
import com.ffzx.orm.common.BaseEntity;

import com.ffzx.orm.common.mongo.mapper.Mapper;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.lang.reflect.ParameterizedType;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public abstract class BaseServiceImpl<T, PK> implements BaseService<T, PK> {

    protected Logger logger = LoggerFactory.getLogger(getClass());

    private Class<T> entityClass;

    public Class<T> getEntityClass() {
        return entityClass;
    }

    public BaseServiceImpl() {
        entityClass = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        logger.debug(getClass() + " init");
    }
    // @Resource
    // Mapper<T> mapper;

    public abstract Mapper<T> getMapper();/* {
        throw new RuntimeException(
				"type[" + getClass() + "] unsupported  getMapper() method");
	}*/

    @Override
    public int add(T entity) {
        String id = CodeGenerator.getUUID();

        ((BaseEntity) entity).setId(id);
        if (StringUtils.isBlank(((BaseEntity) entity).getCode())) {
            String code = CodeGenerator.code();
            ((BaseEntity) entity).setCode(code);
        }
        Date date = new Date();
        ((BaseEntity) entity).setCreateDate(date);
        ((BaseEntity) entity).setLastUpdateDate(date);
        return getMapper().insert(entity);
    }

    @Override
    public int deleteById(String id) {
        return getMapper().deleteByPrimaryKey(id);
    }

    @Override
    public int update(T entity) {
        if(entity instanceof  BaseEntity){
            ((BaseEntity) entity).setLastUpdateDate(new Date());
        }
        return getMapper().updateByPrimaryKey(entity);
    }

    @Override
    public int updateSelective(T entity) {
        if(entity instanceof  BaseEntity){
            ((BaseEntity) entity).setLastUpdateDate(new Date());
        }
        return getMapper().updateByPrimaryKeySelective(entity);
    }

    @Override
    public T findById(String id) {
        return (T) getMapper().selectByPrimaryKey(id);
    }

    @Override
    public  List<T> selectByExample(Object example) {
        return (List<T>) getMapper().selectByExample((Map<String,Object>)example);
    }

    @Override
    public  int updateByExample(T record, Object example) {
        return getMapper().updateByExampleSelective(record, example);
    }

    @Override
    public int countByExample(Object example) {
        return getMapper().selectCountByExample(example);

    }

    @Override
    public T findByCode(String code) {
      return getMapper().findByCode(code);
    }

    @Override
    public List<T> selectByEntity(T entity) {
        return getMapper().select(entity);
    }
}
