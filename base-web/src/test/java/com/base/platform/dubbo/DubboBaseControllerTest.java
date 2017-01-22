package com.base.platform.dubbo;

import com.alibaba.fastjson.JSON;
import com.base.common.CommonTest;
import com.base.common.WebCommonTest;
import com.base.framework.util.LogUtils;
import com.base.framework.util.UUIDUtils;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;
import java.util.UUID;

import static org.junit.Assert.*;

/**
 * DubboBaseControllerTest
 * @author zhouyw
 * @date 2016.12.11
 */
public class DubboBaseControllerTest extends WebCommonTest {

    @Test
    public void initServer()throws Exception{
        String username = "test";
        String logSerial = LogUtils.getSerial();
        MDC.put("username",username);
        MDC.put("logSerial", logSerial);
        logger.info("test-->username={},logSerial={}", username,logSerial);

        MvcResult result = getMockMvc().perform(MockMvcRequestBuilders.get("/dubbo/base/server.htm"))
                .andExpect(MockMvcResultMatchers.view().name("/dubbo/base/server.html"))
                .andReturn();
        logger.info("-->result={}", result);
        Map<String, Object> model = result.getModelAndView().getModelMap();
        logger.info("-->modelAndView.model={}", JSON.toJSONString(model));
    }

    @Test
    public void appInfo() throws Exception {
        MvcResult result = getMockMvc().perform(MockMvcRequestBuilders.get("/dubbo/base/appInfo.htm?appInfo=1"))
                .andExpect(MockMvcResultMatchers.view().name("/dubbo/base/server.html"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("appInfo"))
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
        logger.info("-->result={}", result);
        
    }

}