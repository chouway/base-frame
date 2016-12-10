package com.base.simple.cache.redis;

import com.base.simple.common.CommonTest;
import org.junit.Before;
import org.junit.Test;
import redis.clients.jedis.Jedis;

/**
 * RedisTest
 * @author zhouyw
 * @date 2016.12.10
 */
public class RedisTest extends CommonTest {

    private Jedis jedis ;

    private Long MAX_EXPIRE = 60l;//EX 秒，PX毫秒

    @Before
    public void testBefore(){
        jedis = new Jedis("172.16.6.79",6379);
    }

    @Test
    public void testSetStr() {
        long start = System.currentTimeMillis();
        for (int i = 0; i < 2; i++) {
            String value = "tn" + i;
            String result = jedis.set("n" + i, value, "NX", "EX", MAX_EXPIRE);
//          String result = jedis.set("n" + i, value);
            logger.info("i={}-->result={}", i, result);
        }
        long end = System.currentTimeMillis();
        logger.info("Simple SET: {} seconds", (end - start)/1000.0);
        jedis.disconnect();
    }
}
