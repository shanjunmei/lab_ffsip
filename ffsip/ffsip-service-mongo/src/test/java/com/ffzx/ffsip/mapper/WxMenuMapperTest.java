package com.ffzx.ffsip.mapper;

import com.ffzx.ffsip.TestBase;
import com.ffzx.ffsip.model.User;
import com.ffzx.ffsip.model.WxMenu;
import com.ffzx.ffsip.util.JsonConverter;
import org.junit.Test;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by Administrator on 2017/2/23.
 */
public class WxMenuMapperTest extends TestBase {

    @Resource
    private WxMenuMapper userMapper;

    @Test
    public void testSelectByExample(){
        List<WxMenu> list= userMapper.selectByExample(null);
        logger.info(JsonConverter.toJson(list));
    }


}
