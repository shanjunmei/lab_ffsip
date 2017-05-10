package com.ffzx.commerce.framework.utils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/4/7.
 */
public class EntityUtils {

    /**
     * 获取类所有字段，重名，子类优先
     * @param entityClass
     * @return
     */
    public static List<Field> getFields(Class<?> entityClass) {
        List<Field> fieldList = new ArrayList<>();
        List<String> fieldNameList = new ArrayList<>();
        while (entityClass != null) {//当父类为null的时候说明到达了最上层的父类(Object类).
            Field[] fields = entityClass.getDeclaredFields();
            if (fields != null) {
                for (Field f : fields) {
                    if (!fieldNameList.contains(f.getName())) {
                        fieldNameList.add(f.getName());
                        fieldList.add(f);
                    }
                }
            }
            entityClass = entityClass.getSuperclass(); //得到父类,然后赋给自己
        }
        return fieldList;
    }
}
