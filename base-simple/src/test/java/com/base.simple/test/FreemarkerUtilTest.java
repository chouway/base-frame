package com.base.simple.test;

import java.io.File;
import java.io.FileWriter;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import com.base.simple.test.bean.ftl.User;
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
        FreemarkerUtilTest util = new FreemarkerUtilTest();
        Template template = util.getTemplate("ftl/01.ftl");
        Map<String, Object> map = new HashMap<String, Object>();
        User user = new User();
        user.setId(1);
        user.setName(" You123 ");
        user.setAge(10);
        map.put("user", user);
        template.process(map, new OutputStreamWriter(System.out));
    }



    /**
     * 根据给定的ftl（freemarker template language）来获得一个用于操作的模板
     * @param name
     * @return
     */
    public Template getTemplate(String name) {
        try {
            // 通过Freemark而的Configuration读取到相应的模板ftl
            Configuration cfg = new Configuration();
            // 设定去哪里读取相关的模板FTL文件
            cfg.setClassForTemplateLoading(this.getClass(), "/");
            // 在模板文件目录中找到名为name的文件
            Template template = cfg.getTemplate(name);
            return template != null ? template : null;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 通过控制台输出文件信息
     *
     * @param name
     * @param root
     */
    public void print(String name, Map<String, Object> root) {
        try {
            // 通过Template可以将模板文件输出到相应的流
            Template template = this.getTemplate(name);
            template.process(root, new PrintWriter(System.out));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 输出为HTML文件
     *
     * @param name
     * @param root
     * @param outfile
     */
    public void htmlprint(String name, Map<String, Object> root, String outfile) {
        FileWriter writer = null;
        try {
            // 使用一个路径实现将文件的输出
            writer = new FileWriter(new File("src/"+ outfile));
            Template template = this.getTemplate(name);
            template.process(root, writer);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

}
