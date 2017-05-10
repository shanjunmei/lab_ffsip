package com.ffzx.ffsip;


import com.ffzx.commerce.framework.utils.EntityUtils;
import com.ffzx.ffsip.model.Role;
import com.ffzx.ffsip.model.User;

import java.lang.reflect.Field;
import java.util.List;

/**
 * Created by Administrator on 2017/4/7.
 */
public class EntityTest {
    public static void main(String args[]) {
       List<Field> fields= EntityUtils.getFields(Role.class);
       System.out.println(fields);
    }
}
