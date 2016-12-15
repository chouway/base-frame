package com.base.simple.spring;

import com.base.simple.common.CommonTest;
import com.base.simple.freemarker.bean.User;
import org.junit.Test;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Type;

/**
 * SpringTest
 * @author zhouyw
 * @date 2016.12.14
 */
public class SpringTest extends CommonTest {
    @Test
    public void reflectField(){
        User user = new User();
        Field field = ReflectionUtils.findField(User.class, "name");
        ReflectionUtils.makeAccessible(field);//私有字段时 须要设该值 才能访问
        ReflectionUtils.setField(field,user,"123");
        String value = (String) ReflectionUtils.getField(field, user);
        logger.info("field-->value={}", value);
    }
    
    @Test
    public void reflectMethod(){
        User user = new User();
        user.setName("123");
        Method method = ReflectionUtils.findMethod(User.class, "getName");
        ReflectionUtils.makeAccessible(method);;//私有方法时 须要设该值 才能访问
        String value =(String) ReflectionUtils.invokeMethod(method,user);
        logger.info("method-->value={}", value);
    }
}
