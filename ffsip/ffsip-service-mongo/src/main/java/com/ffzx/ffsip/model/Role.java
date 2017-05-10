package com.ffzx.ffsip.model;

import com.ffzx.orm.common.BaseEntity;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;

/**
 * Created by Administrator on 2017/4/6.
 */

public class Role extends BaseEntity{

    private String id;
    private String code;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
