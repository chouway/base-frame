package com.base.simple.test;

import java.io.File;
import java.io.FileWriter;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import com.base.simple.freemarker.FreeMarkerUtil;
import com.base.simple.freemarker.bean.User;
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.junit.Test;

/**
 * FreemarkerUtil
 * @author zhouyw
 * @date 2016.12.05
 */
public class FreemarkerUtilTest {

    @Test
    public void testftl3() throws Exception {
        FreeMarkerUtil util = new FreeMarkerUtil();
        Template template = util.getTemplate("ftl/01.ftl");
        Map<String, Object> map = new HashMap<String, Object>();
        User user = new User();
        user.setId(1);
        user.setName(" You123 ");
        user.setAge(10);
        map.put("user", user);
        template.process(map, new OutputStreamWriter(System.out));
    }

}
