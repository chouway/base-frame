package com.base.test;

import com.base.framework.CommonTest;
import com.base.platform.dubbo.domain.BaseServerInfo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Calendar;
import java.util.Date;

/**
 * JavaTest
 * @author zhouyw
 * @date 2016.12.26
 */
public class JavaTest{
    protected Logger logger = LoggerFactory.getLogger(getClass());

    @Test
    public void getTableName(){
        Class<BaseServerInfo> baseServerInfoClass = BaseServerInfo.class;
        logger.info("-->baseServerInfoClass={}", baseServerInfoClass);
        
    }

    @Test
    public void getDate(){
        Date date = new Date();
        int  add = 1;
        Date addAf = this.addDay(date, 1);
        String patter = "yyyyMMdd";
        logger.info("-->addAf={}", addAf);

    }

    public static final long ONE_DAY_OF_MS = 24*60*60*1000L;

    public Date addDay(Date date, int add){
        if(date==null){
            return date;
        }

        if(add==0){
            return date;
        }
        long addTime = add  * ONE_DAY_OF_MS;
        return new Date(date.getTime() + addTime);
    }

}
