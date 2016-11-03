package com.base.shiro.test.ini;

import com.alibaba.druid.pool.DruidDataSource;
import com.base.common.CommonTest;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.pam.AtLeastOneSuccessfulStrategy;
import org.apache.shiro.authc.pam.ModularRealmAuthenticator;
import org.apache.shiro.authz.ModularRealmAuthorizer;
import org.apache.shiro.authz.permission.WildcardPermissionResolver;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.realm.jdbc.JdbcRealm;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.Factory;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;

/**
 * ShiroIniTest
 * @author zhouyw
 * @date 2016.11.03
 */
public class ShiroIniTest extends CommonTest {

    private final String preFixed = "classpath:shiro/ini/";

    @Test
    public void testNonConfigurationCreate() {
        DefaultSecurityManager securityManager = new DefaultSecurityManager();
        //设置 authenticator
        ModularRealmAuthenticator authenticator = new ModularRealmAuthenticator();
        authenticator.setAuthenticationStrategy(new AtLeastOneSuccessfulStrategy());
        securityManager.setAuthenticator(authenticator);
        //设置 authorizer
        ModularRealmAuthorizer authorizer = new ModularRealmAuthorizer();
        authorizer.setPermissionResolver(new WildcardPermissionResolver());
        securityManager.setAuthorizer(authorizer);
        //设置 Realm
        DruidDataSource ds = new DruidDataSource();
        ds.setDriverClassName("org.postgresql.Driver");
        ds.setUrl("jdbc:postgresql://127.0.0.1:5432/base_shiro");
        ds.setUsername("test");
        ds.setPassword("test");
        JdbcRealm jdbcRealm = new JdbcRealm();
        jdbcRealm.setDataSource(ds);
        jdbcRealm.setPermissionsLookupEnabled(true);
        securityManager.setRealms(Arrays.asList((Realm) jdbcRealm));
        //将 SecurityManager 设置到 SecurityUtils 方便全局使用
        SecurityUtils.setSecurityManager(securityManager);
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken("zhang", "123");
        subject.login(token);
        Assert.assertTrue(subject.isAuthenticated());
    }

    @Test
    public void testConfigurationCreate(){
        Factory<SecurityManager> factory =
                new IniSecurityManagerFactory(this.preFixed + "shiro-config.ini");
        org.apache.shiro.mgt.SecurityManager securityManager = factory.getInstance();
        //将 SecurityManager 设置到 SecurityUtils 方便全局使用
        SecurityUtils.setSecurityManager(securityManager);
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken("zhang", "123");
        subject.login(token);
        Assert.assertTrue(subject.isAuthenticated());
    }
}
