package com.ffzx.common.controller;

import com.ffzx.commerce.framework.model.ServiceException;
import com.ffzx.common.utils.ResultVo;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.persistence.Column;
import java.lang.reflect.Field;
import java.util.*;


public abstract class BaseController<T, PK> extends BaseEditController<T, PK> {


    public BaseController() {
        super();


    }

    //throw new RuntimeException(getClass() + " unsuppoted getService() method");


    @SuppressWarnings("unchecked")
    @RequestMapping("queryData")
    @ResponseBody
    public ResultVo query(T entity) {

        Map<String, Object> params = getQueryEntity(entity);
        // EX example = getExample(params);
        ResultVo resultVo = new ResultVo();
        String indexStr = getParameter("pageIndex");
        String sizeStr = getParameter("pageSize");
        Page<T> page = null;
        int total = 0;
        PageHelper.orderBy("last_update_date desc");
        if (StringUtils.isNotBlank(indexStr)) {

            page = PageHelper.startPage(Integer.valueOf(indexStr), Integer.valueOf(sizeStr));
        }


        List<T> dataList = getService().selectByExample(params);
        if (page != null) {
            total = (int) page.getTotal();
        }
        resultVo.setRecordsTotal(total);
        resultVo.setInfoData(dataList);
        return resultVo;
    }

    /**
     * 查询实体对象转换，移除空值
     *
     * @param entity
     * @return
     */
    private Map<String, Object> getQueryEntity(T entity) {
        Class<?> cls = entity.getClass();
        Map<String, Object> params = new HashMap<>();

        try {
            Object queryEntity = cls.newInstance();

            List<Field> fields = new ArrayList<>();
            while (cls != null) {
                //  Field[] fields= cls.getFields();
                fields.addAll(Arrays.asList(cls.getDeclaredFields()));
                cls = cls.getSuperclass();
            }
            for (Field f : fields) {
                boolean accessible = f.isAccessible();
                f.setAccessible(true);
                String key = f.getName();
                Column c = f.getAnnotation(Column.class);
                if (c != null) {
                    if (StringUtils.isNotBlank(c.name())) {
                        key = c.name();
                    }
                }
                Object val = f.get(entity);


                if (val instanceof String) {
                    if (StringUtils.isNotBlank((String) val)) {
                        params.put(key, val);
                    }
                } else {
                    if (val != null) {
                        params.put(key, val);
                    }
                }
                f.setAccessible(accessible);
            }
            return params;
        } catch (Exception e) {
            // logger.info("query enity convert fail ",e);
            throw new ServiceException("query entity convert fail");
        }


    }


}
