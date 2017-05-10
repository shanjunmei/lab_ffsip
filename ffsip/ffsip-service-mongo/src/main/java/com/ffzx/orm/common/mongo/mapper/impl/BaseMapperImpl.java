package com.ffzx.orm.common.mongo.mapper.impl;

import com.ffzx.commerce.framework.utils.EntityUtils;
import com.ffzx.orm.common.mongo.mapper.Mapper;
import com.mongodb.WriteResult;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Key;
import org.mongodb.morphia.query.Query;
import org.mongodb.morphia.query.UpdateOperations;
import org.mongodb.morphia.query.UpdateResults;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/4/6.
 */
public abstract class BaseMapperImpl<T> implements Mapper<T> {

    protected Logger logger = LoggerFactory.getLogger(getClass());

    private Class<T> entityClass;

    private List<Field> fields;

    private Field key;

    public BaseMapperImpl() {
        entityClass = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        fields = EntityUtils.getFields(entityClass);
        key = getKeyField();
        logger.debug(getClass() + " init");
    }

    protected Field getKeyField() {
        if (key == null) {
            for (Field field : fields) {
                if ("id".equals(field.getName())) {
                    key = field;
                }
            }
        }
        return key;
    }

    protected Object getKey(Object o) {
        Field field = getKeyField();
        boolean accessible = field.isAccessible();
        Object val = null;
        try {
            field.setAccessible(true);
            val = field.get(o);
            field.setAccessible(accessible);
        } catch (Exception e) {
            //IGNORE
        }
        return val;
    }

    public abstract Datastore getDatastore();

    @Override
    public int insert(T entity) {
        Key<T> key = getDatastore().save(entity);
        return 1;
    }

    @Override
    public int deleteByPrimaryKey(String id) {
        Query<T> deleteQuery = getDatastore().createQuery(entityClass);
        deleteQuery.filter("id", id);
        WriteResult result = getDatastore().delete(deleteQuery);
        return result.getN();
    }

    @Override
    public int updateByPrimaryKey(T entity) {
        Query<T> updateQuery = getDatastore().createQuery(entityClass);
        updateQuery.filter("id", getKey(entity));

        UpdateOperations<T> updateOperations = getDatastore().createUpdateOperations(entityClass);
        for (Field field : fields) {
            boolean accessible = field.isAccessible();
            field.setAccessible(true);
            try {
                Object val = field.get(entity);
                if (val != null) {
                    updateOperations.set(field.getName(), val);
                } else {
                    updateOperations.unset(field.getName());
                }
            } catch (IllegalAccessException e) {
                //IGNORE
            }
            field.setAccessible(accessible);
        }
        UpdateResults result = getDatastore().update(updateQuery, updateOperations);
        return result.getUpdatedCount();
    }

    @Override
    public int updateByPrimaryKeySelective(T entity) {
        Query<T> updateQuery = getDatastore().createQuery(entityClass);
        updateQuery.filter("id", getKey(entity));

        UpdateOperations<T> updateOperations = getDatastore().createUpdateOperations(entityClass);
        for (Field field : fields) {
            boolean accessible = field.isAccessible();
            field.setAccessible(true);
            try {
                Object val = field.get(entity);
                if (val != null) {
                    updateOperations.set(field.getName(), val);
                }
            } catch (IllegalAccessException e) {
                //IGNORE
            }
            field.setAccessible(accessible);
        }
        UpdateResults result = getDatastore().update(updateQuery, updateOperations);
        return result.getUpdatedCount();
    }

    @Override
    public T selectByPrimaryKey(String id) {
        return findOneByProperty("id",id);
    }

    @Override
    public List<T> selectByExample(Map<String,Object> example) {
        List<T> list = null;
        if (example == null) {
            Query<T> query = getDatastore().createQuery(entityClass);
            list = query.asList();
        } else {
            Query<T> query = getDatastore().createQuery(entityClass);
            for (Map.Entry<String,Object> e:example.entrySet()){
                query.filter(e.getKey(),e.getValue());
            }
            list = query.asList();
        }

        return list;
    }

    @Override
    public int updateByExampleSelective(T entity, Object example) {
        //  UpdateOperations<T> updateOperations = getDatastore().createUpdateOperations(entityClass);

        return 0;
    }

    @Override
    public int selectCountByExample(Object example) {
        return 0;
    }

    @Override
    public List<T> select(T entity) {
        List<T> list = null;
        if (entity == null) {
            Query<T> query = getDatastore().createQuery(entityClass);
            list = query.asList();
        } else {
            Query<T> query = getDatastore().queryByExample(entity);
            list = query.asList();
        }

        return list;
    }

    @Override
    public T findByCode(String code) {
        return findOneByProperty("code",code);
    }

    @Override
    public T findOneByProperty(String property, String value) {
        Query<T> query = getDatastore().createQuery(entityClass);
        query.filter(property, value);
        return query.get();
    }

    @Override
    public List<T> findByProperty(String property, String value) {
        Query<T> query = getDatastore().createQuery(entityClass);
        query.filter(property, value);
        return query.asList();
    }
}
