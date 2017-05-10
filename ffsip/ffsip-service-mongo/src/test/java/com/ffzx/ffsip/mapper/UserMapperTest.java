package com.ffzx.ffsip.mapper;

import com.ffzx.ffsip.TestBase;
import com.ffzx.ffsip.model.User;
import com.ffzx.ffsip.util.JsonConverter;
import org.junit.Test;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by Administrator on 2017/2/23.
 */
public class UserMapperTest extends TestBase {

    @Resource
    private UserMapper userMapper;

    @Test
    public void testSelectByExample(){
        List<User> list= userMapper.selectByExample(null);
        logger.info(JsonConverter.toJson(list));
    }

    @Test
    public void testUpdate(){
        String id="c7718fb2e073430086ae1fd4763970ff";
        User user=userMapper.selectByPrimaryKey(id);
        user.setEmail("445052471@qq.com");
        userMapper.updateByPrimaryKey(user);
        logger.info(JsonConverter.toJson(user));
    }
}
