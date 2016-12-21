package com.base.test;

import com.alibaba.fastjson.JSON;
import com.base.framework.CommonTest;
import com.base.framework.exception.BusinessException;
import com.base.platform.dubbo.domain.BaseServerInfo;
import com.base.platform.dubbo.service.IBaseServerService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.types.RedisClientInfo;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.io.UnsupportedEncodingException;
import java.util.List;

/**
 * CacheTest
 * @author zhouyw
 * @date 2016.12.21
 */
public class CacheTest extends CommonTest {

    @Autowired
    private IBaseServerService baseServerService;
    
    @Test
    public void testGet(){
        BaseServerInfo datat_0 = baseServerService.getDatatWithRedisCache("0");
        logger.info("-->datat_0={}", JSON.toJSONString(datat_0));

        BaseServerInfo datat_0_0 = baseServerService.getDatatWithRedisCache("0");
        logger.info("-->datat_0_0={}", JSON.toJSONString(datat_0_0));

        BaseServerInfo data_1 = baseServerService.getDatatWithRedisCache("1");
            logger.info("-->data_1={}", data_1);
        if(data_1==null){
            this.testAdd();
            data_1 = baseServerService.getDatatWithRedisCache("1");
            logger.info("af add-->data_1={}", JSON.toJSONString(data_1));
        }

    }

    @Test
    public void testAdd(){
        BaseServerInfo baseServerInfo  = new BaseServerInfo();
        int i = 1;
        baseServerInfo.setId("" + i);
        baseServerInfo.setServerName("test_" + i);
        baseServerService.addDataWithRedisCache(baseServerInfo);

    }


    @Autowired
        private RedisTemplate redisTemplate;

    @Test
    public void serialize() throws Exception {
        RedisSerializer hashValueSerializer = redisTemplate.getHashValueSerializer();

        StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
        BaseServerInfo baseServerInfo = new BaseServerInfo();
        baseServerInfo.setId("1");
        baseServerInfo.setId("test_1");

        byte[] serialize = hashValueSerializer.serialize(baseServerInfo);
        logger.info("-->serialize={}", JSON.toJSONString(serialize));

        Object deserialize = hashValueSerializer.deserialize(serialize);
        logger.info("-->deserialize={}", JSON.toJSONString(deserialize));

        List<RedisClientInfo> clientList = redisTemplate.getClientList();
        if(clientList==null||clientList.size()<1){
            throw new BusinessException("链接为空");
        }
        RedisClientInfo redisClientInfo = clientList.get(0);
        String value = redisClientInfo.get("key_0");
        logger.info("-->value={}", value);
    }
}
