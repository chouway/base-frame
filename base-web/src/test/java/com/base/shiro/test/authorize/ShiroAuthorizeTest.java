package com.base.shiro.test.authorize;

import com.alibaba.fastjson.JSON;
import com.base.common.CommonTest;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.UnauthorizedException;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.Factory;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;

/**
 * ShiroAuthorizeTest 授权
 * @author zhouyw
 * @date 2016.11.03
 */
public class ShiroAuthorizeTest extends CommonTest{

    private final String preFixed = "classpath:shiro/authorize/";

    //Shiro 支持三种方式的授权:编程式 ; 注解式 ; JSP/GSP 标签;
    /**
     编程式;
     Subject subject = SecurityUtils.getSubject();
     if(subject.hasRole(“admin”)) {
     //有权限
     } else {
     //无权限
     }

     注解式;
     @RequiresRoles("admin")
     public void hello() {
     //有权限
     }

     JSP/GSP 标签;
     <shiro:hasRole name="admin">
     <!— 有权限 —>
     </shiro:hasRole>
     */

    /**
     * 基于角色 的 访问 控制 （隐 式)
     */
    @Test
    public void testHasRole() {
        login("shiro-role.ini", "zhang", "123");
        //判断拥有角色：role1
        Assert.assertTrue(subject().hasRole("role1"));
        //判断拥有角色：role1 and role2
        Assert.assertTrue(subject().hasAllRoles(Arrays.asList("role1", "role2")));
        //判断拥有角色：role1 and role2 and !role3
        boolean[] result = subject().hasRoles(Arrays.asList("role1", "role2", "role3"));
        Assert.assertEquals(true, result[0]);
        Assert.assertEquals(true, result[1]);
        Assert.assertEquals(false, result[2]);
    }

    @Test(expected = UnauthorizedException.class)
    public void testCheckRole() {
        login("shiro-role.ini", "zhang", "123");
        //断言拥有角色：role1
        subject().checkRole("role1");
        //断言拥有角色：role1 and role3 失败抛出异常
        subject().checkRoles("role1", "role3");
    }



    /* -----private method spilt----- */

    private void login(String iniResourcePath,String username,String password) {
        //1、获取 SecurityManager 工厂，此处使用 Ini 配置文件初始化 SecurityManager
        Factory<SecurityManager> factory =
                new IniSecurityManagerFactory(this.preFixed + iniResourcePath);
        //2、得到 SecurityManager 实例 并绑定给 SecurityUtils
        SecurityManager securityManager = factory.getInstance();
        SecurityUtils.setSecurityManager(securityManager);
        //3、得到 Subject 及创建用户名/密码身份验证 Token（即用户身份/凭证）
        Subject subject = subject();
        UsernamePasswordToken token = new UsernamePasswordToken(username, password);
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

    private Subject subject(){
        return SecurityUtils.getSubject();
    }
}
