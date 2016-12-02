package com.base.platform.dubbo;

import com.alibaba.fastjson.JSON;
import com.base.platform.dubbo.domain.BaseServerInfo;
import com.base.platform.dubbo.service.IBaseServerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

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
    public String init(Model model){

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
}
