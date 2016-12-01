package com.base.platform.dubbo;

import com.base.platform.dubbo.service.IBaseServerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * BaseController
 * @author zhouyw
 * @date 2016.12.01
 */
@Controller
@RequestMapping("/dubbo/base")
public class DubboBaseController {

    @Autowired
    private IBaseServerService baseServerService;

    @RequestMapping(value = "/server.htm")
    public String init(Model model){
        return "/dubbo/base/server.html";
    }
}
