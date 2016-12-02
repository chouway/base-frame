package com.base.framework.context;

import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

/**
 * SpringService
 * @author zhouyw
 * @date 2016.09.09
 */
public abstract class BaseService {

    protected Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private SqlSessionTemplate sqlSessionTemplate;

    @Autowired
    private SpringContextUtil springContextUtil;

    public ApplicationContext getApplicationContext(){
        return springContextUtil.getApplicationContext();
    }

    public <T>  T  getDao(Class<T> clazz){
        return sqlSessionTemplate.getMapper(clazz);
    }

    public SqlSessionTemplate getSqlSessionTemplate(){
        return sqlSessionTemplate;
    }

    public <T> T getService(Class<T> clazz){
        if (!clazz.isInterface()) {
            throw new RuntimeException("is not interface:"+clazz.getCanonicalName());
        }
        return this.getBean(clazz);
    }

    public <T> T getBean(Class<T> clazz){
        return springContextUtil.getBean(clazz);
    }

    public Object getBean(String name){
        return springContextUtil.getBean(name);
    }
}
