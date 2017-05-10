package com.ffzx.ffsip.service;

import com.ffzx.ffsip.TestBase;
import com.ffzx.ffsip.model.User;
import com.ffzx.ffsip.util.CodeGenerator;
import com.ffzx.ffsip.util.JsonConverter;

import org.junit.Test;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by Administrator on 2017/2/23.
 */
public class UserServiceTest extends TestBase{

    @Resource
    private UserService userService;

    @Test
    public void testSelectByExample(){
       List<User> list= userService.selectByExample(null);
        logger.info(JsonConverter.toJson(list));
    }

    @Test
    public void testInsert(){
        User entity=new User();
       // entity.setId(CodeGenerator.getUUID());

        int ret=userService.add(entity);
        logger.info(ret+"");
    }

    @Test
    public void testFindByKey(){
        User entity=new User();
        // entity.setId(CodeGenerator.getUUID());
        String id="ad70f7eddd244d3dbc16fdfbde216714";
        User ret=userService.findById(id);
        logger.info(JsonConverter.toJson(ret));
    }

    @Test
    public void testUpdate(){
        User entity=new User();
        // entity.setId(CodeGenerator.getUUID());
        String id="c7718fb2e073430086ae1fd4763970ff";
        User user=userService.findById(id);
        user.setPassword("123456");
        user.setPassword(null);
        int ret=  userService.update(user);
        logger.info("update result :{}",ret);
        logger.info(JsonConverter.toJson(user));
    }

    //



    @Test
    public void testFindByCode(){
        User entity=new User();
        // entity.setId(CodeGenerator.getUUID());
        String code="97WEq6puDuvVDrRret94kp";
        User user=userService.findByCode(code);

        logger.info(JsonConverter.toJson(user));
    }
}
