package com.base.test;

import com.base.framework.CommonTest;
import com.base.framework.context.SpringContextUtil;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.defaults.DefaultSqlSessionFactory;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import java.sql.Connection;
import java.sql.DatabaseMetaData;

/**
 * JDBCTest
 * @author zhouyw
 * @date 2016.09.06
 */
public class JDBCTest extends CommonTest {

    @Autowired
    private SpringContextUtil springContextUtil;

    @Test
    public void testJdbc() throws Exception {
        ApplicationContext applicationContext = springContextUtil.getApplicationContext();
        Object object = applicationContext.getBean("sqlSessionFactory");
        DefaultSqlSessionFactory sqlSessionFactory = (DefaultSqlSessionFactory)object;
        SqlSession sqlSession = sqlSessionFactory.openSession();
        Connection connection = sqlSession.getConnection();
        DatabaseMetaData metaData = connection.getMetaData();
        logger.info("-->metaData={}", metaData);
        logger.info("-->md.url={}", metaData.getURL());
        logger.info("-->md.username={}", metaData.getUserName());
    }
}
