package com.base.framework.context;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * SpringContextUtil
 * @author zhouyw
 * @date 2016.09.05
 */
@Component
public class SpringContextUtil implements ApplicationContextAware {

    private Logger logger = LoggerFactory.getLogger(SpringContextUtil.class);

    private ApplicationContext applicationContext; // Spring应用上下文环境

    /*
     *实现了ApplicationContextAware 接口，必须实现该方法；

     *通过传递applicationContext参数初始化成员变量applicationContext
     */
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) {
        synchronized (SpringContextUtil.class) {
            logger.debug("setApplicationContext, notifyAll");
            this.applicationContext = applicationContext;
            SpringContextUtil.class.notifyAll();
        }
    }

    /*
     * 取得存储在静态变量中的ApplicationContext.
     */
    public ApplicationContext getApplicationContext() {
        return applicationContext;
    }


    public <T> T getBean(String name, Class<T> clazz) throws BeansException {
        checkAp();
        return this.applicationContext.getBean(name, clazz);
    }




    public <T> T getBean(Class<T> clazz) throws BeansException {
        return this.applicationContext.getBean(clazz);
    }

    public Object getBean(String name) throws BeansException {
        return this.applicationContext.getBean(name);
    }


    private void checkAp() {
        if (applicationContext == null) {
            throw new IllegalStateException("applicaitonContext is null,check config");
        }
    }


}