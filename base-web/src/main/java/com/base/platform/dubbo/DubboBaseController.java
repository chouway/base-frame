package com.base.platform.dubbo;

import com.alibaba.fastjson.JSON;
import com.base.platform.dubbo.domain.BaseServerInfo;
import com.base.platform.dubbo.domain.BaseServerInfoCondition;
import com.base.platform.dubbo.service.IBaseServerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.*;

/**
 * BaseController
 * @author zhouyw
 * @date 2016.12.01
 */
@Controller
@RequestMapping("/dubbo/base")
public class DubboBaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());


    @Autowired
    private IBaseServerService baseServerService;

    @RequestMapping(value = "/server.htm")
    public String initServer(Model model){
        BaseServerInfoCondition baseServerInfoCondition = new BaseServerInfoCondition();
        baseServerInfoCondition.createCriteria();
        long count = baseServerService.countServer();
        logger.info("-->count={}", count);


        BaseServerInfo baseServerInfo = new BaseServerInfo();
        baseServerInfo.setId("test_web_input");
        baseServerInfo.setServerName("abc");
        baseServerInfo.setCreateUser("cba");
        baseServerInfo.setCreateTime(new Date());
        model.addAttribute("baseServerInfo",baseServerInfo);

        model.addAttribute("filed_str","str");

        Map<String,Object> tempMap = new HashMap<String,Object>();
        tempMap.put("map_field_key","map_field_val");
        model.addAllAttributes(tempMap);

        List<String> list = new ArrayList<String>();
        list.add("item_0");
        list.add("item_1");
        list.add("item_2");


        Map<String,Object> map = new HashMap<String,Object>();
        map.put("key_0","val_0");
        map.put("key_1","val_1");
        map.put("key_2","val_2");


        model.addAttribute("map",map);
        model.addAttribute("list",list);

        Map<String, Object> modelMap = model.asMap();
        logger.info("-->modelMap={}", JSON.toJSONString(modelMap));
        return "/dubbo/base/server.html";
    }

    //http://localhost:8090/base-web/dubbo/base/appInfo.htm?appInfo=%7B%22abc%22:1%7D
    //appInfo={"abc":1}
    //%7B%22abc%22:1%7D
    @RequestMapping(value = "/appInfo.htm")
    public String appInfo(Model model,String appInfo){
        logger.info("-->appInfo={}", appInfo);
        model.addAttribute("appInfo",appInfo);
        return "/dubbo/base/server.html";
    }
}
