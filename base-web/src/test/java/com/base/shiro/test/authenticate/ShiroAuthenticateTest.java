package com.base.shiro.test.authenticate;

import com.alibaba.fastjson.JSON;
import com.base.common.CommonTest;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.Factory;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

/**
 * ShiroTest  身份认证
 * @author zhouyw
 * @date 2016.11.02
 */
public class ShiroAuthenticateTest extends CommonTest {
    
    private final String preFixed = "classpath:shiro/authenticate/";
    /**
     * 登录/登出
     */
    @Test
    public void testLoginLogout() {
        String iniResourcePath = "ini.properties";
        login(iniResourcePath);
        Subject subject = SecurityUtils.getSubject();
        if(subject.isAuthenticated()){
            subject.logout();//退出
            Assert.assertEquals(false, subject.isAuthenticated());//断言已登出
        }

    }

    @Test
    public void testLoginLogoutSingle(){
        String iniResourcePath = "shiro-realm-single.ini";
        login(iniResourcePath);
    }


    @Test
    public void testLoginLogoutMany(){
        String iniResourcePath = "shiro-realm-many.ini";
        login(iniResourcePath);
    }
    @Test
    public void testLoginLogoutJdbc(){
        String iniResourcePath = "shiro-jdbc-realm.ini";
        login(iniResourcePath);
    }

    @Test
    public void testAllSuccessfulStrategyWithSuccess() {
        login("shiro-authenticator-all-success.ini");
        Subject subject = SecurityUtils.getSubject();
        //得到一个身份集合，其包含了 Realm 验证成功的身份信息
        PrincipalCollection principalCollection = subject.getPrincipals();
        List list = principalCollection.asList();
        logger.info("-->list={}", JSON.toJSONString(list));//-->list=["zhang","zhang@163.com"]
        Assert.assertEquals(2, list.size());
    }

    @Test(expected = UnknownAccountException.class)
    public void testAllSuccessfulStrategyWithFail() {
        login("shiro-authenticator-all-fail.ini");
    }

    @Test
    public void testAtLeastOneSuccessStrategy() {
        login("shiro-authenticator-at-least-one-success.ini");
    }

    @Test
    public void testFirstSuccessfulStrategy(){
        login("shiro-authenticator-first-success.ini");
    }

    /* -----private method spilt----- */
    private void login(String iniResourcePath) {
        //1、获取 SecurityManager 工厂，此处使用 Ini 配置文件初始化 SecurityManager
        Factory<SecurityManager> factory =
                new IniSecurityManagerFactory(this.preFixed + iniResourcePath);
        //2、得到 SecurityManager 实例 并绑定给 SecurityUtils
        SecurityManager securityManager = factory.getInstance();
        SecurityUtils.setSecurityManager(securityManager);
        //3、得到 Subject 及创建用户名/密码身份验证 Token（即用户身份/凭证）
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken("zhang", "123");
        try {
        //4、登录，即身份验证
            subject.login(token);
        } catch (AuthenticationException e) {
            logger.error("error:-->e={}", e,e);
            throw e;
        //5、身份验证失败
        }
        Assert.assertEquals(true, subject.isAuthenticated()); //断言用户已经登录
        logger.info("-->subject={}", JSON.toJSONString(subject));
    }
}
