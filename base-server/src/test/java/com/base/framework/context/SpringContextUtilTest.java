package com.base.framework.context;

import com.base.framework.CommonTest;
import com.base.framework.context.SpringContextUtil;
import com.base.platform.dubbo.service.IBaseServerService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * SpringContextUtilTest
 * @author zhouyw
 * @date 2016.09.05
 */
public class SpringContextUtilTest extends CommonTest {

    @Autowired
    private SpringContextUtil springContextUtil;

    @Test
    public void getBean() throws Exception {

        IBaseServerService baseServerService_0 = springContextUtil.getBean("baseServerService",IBaseServerService.class);
        logger.info("-->baseServerService_0={}", baseServerService_0);

        IBaseServerService baseServerService_1 = springContextUtil.getBean(IBaseServerService.class);
        logger.info("-->baseServerService_1={}", baseServerService_1);
    }

}