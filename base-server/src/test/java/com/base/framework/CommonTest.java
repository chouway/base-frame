package com.base.framework;

import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * CommonTest
 * @author zhouyw
 * @date 2016.09.04
 */
@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(locations = {"classpath:/META-INF/spring/*.xml"})
@ContextConfiguration(locations = {
        "classpath:/META-INF/spring/appCtx-aop.xml",
        "classpath:/META-INF/spring/appCtx-aop-tx.xml",
        "classpath:/META-INF/spring/appCtx-base.xml",
        "classpath:/META-INF/spring/appCtx-dao.xml",
        "classpath:/META-INF/spring/appCtx-dubbo.xml",
        "classpath:/META-INF/spring/appCtx-datasource.xml",
        "classpath:/META-INF/spring/appCtx-message.xml"
})
public class CommonTest {
    protected Logger logger = LoggerFactory.getLogger(getClass());
}
