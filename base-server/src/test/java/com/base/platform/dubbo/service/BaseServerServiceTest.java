package com.base.platform.dubbo.service;

import com.alibaba.fastjson.JSON;
import com.base.framework.CommonTest;
import com.base.framework.bean.page.Page;
import com.base.platform.dubbo.domain.BaseServerInfo;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * BaseServerTest
 * @author zhouyw
 * @date 2016.09.04
 */
public class BaseServerServiceTest extends CommonTest{


    @Autowired
    private IBaseServerService baseServerService;

    @Test
    public void baseServer() throws Exception {
        Object interfaceService = baseServerService.baseServer("1");
        logger.info("-->interfaceService={}", interfaceService);

    }

    @Test
    public void addServer() throws Exception {
        BaseServerInfo info = new BaseServerInfo();

        info.setServerName("test");
        info.setCreateTime(new Date());
        BaseServerInfo baseServerInfo = baseServerService.addServer(info);
        logger.info("-->baseServerInfo={}", JSON.toJSONString(baseServerInfo));
    }

    @Test
    public void countServer() throws Exception {
        long countServer = baseServerService.countServer();
        logger.info("-->countServer={}", countServer);
    }

    @Test
    public void getExtByMap() throws Exception {
        Map<String,Object> params = new HashMap<String,Object>();
        params.put("id","1");
        List<BaseServerInfo> list = baseServerService.getExtByMap(params);
        logger.info("-->list={}", JSON.toJSONString(list));
    }

    @Test
    public void getExtByPage() throws Exception {
        Map<String,Object> params = new HashMap<String,Object>();
//        params.put("id","1");
        Page<BaseServerInfo> page = new Page<BaseServerInfo>(2, 1);
        Page<BaseServerInfo> extByPage = baseServerService.getExtByPage(params, page);
        logger.info("-->extByPage={}", JSON.toJSONString(extByPage));
    }

    @Test
    public void testDealNeedMsgBusi() throws Exception {
            baseServerService.dealNeedMsgBusi();
            logger.info("-->testlog1");
            logger.info("-->testlog2");

            Thread.sleep(10*1000l);
    }

    @Test
    public void testSeeFirstMyBatisCache() throws Exception {
        baseServerService.seeFirstMyBatisCache();
    }
}