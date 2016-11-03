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
     * 基于角色的访问控制 （隐式)
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
    /**
     * 基于角色的访问控制 （显式)
     */
    @Test
    public void testIsPermitted() {
        login("shiro-permission.ini", "zhang", "123");
        //判断拥有权限：user:create
        Assert.assertTrue(subject().isPermitted("user:create"));
        //判断拥有权限：user:update and user:delete
        Assert.assertTrue(subject().isPermittedAll("user:update", "user:delete"));
        //判断没有权限：user:view
        Assert.assertFalse(subject().isPermitted("user:view"));
    }

    @Test(expected = UnauthorizedException.class)
    public void testCheckPermission () {
        login("shiro-permission.ini", "zhang", "123");
        //断言拥有权限：user:create
        subject().checkPermission("user:create");
        //断言拥有权限：user:delete and user:update
        subject().checkPermissions("user:delete", "user:update");
        //断言拥有权限：user:view 失败抛出异常
        subject().checkPermissions("user:view");
    }

    /*
        字符串通配符权限:  规则： “资源标识符：操作：对象实例 ID” 即对哪个资源的哪个实例可以进行什么操作。
                        其默认支持通配符权限字符串，“:”表示资源/操作/实例的分割；“,”表示操作的分割；“*”表示任意资源/操作/实例。
     */
    @Test
    public void testCheckCommonSinglSUI(){// 单个资源单个权限
        login("shiro-permission.ini", "zhang3", "123");
        subject().checkPermissions("system:user:update");
    }

    @Test
    public void testCheckCommonMany1SUI(){// 单个资源多个权限
        login("shiro-permission.ini", "zhang41", "123");
        subject().checkPermissions("system:user:update");
        subject().checkPermissions("system:user:delete");
        subject().checkPermissions("system:user:update", "system:user:delete");
//      subject().checkPermissions("system:user:update,delete");//UnauthorizedException: Subject does not have permission [system:user:update,delete]
    }

    @Test
    public void testCheckCommonMany2SUI(){// 单个资源多个权限
        login("shiro-permission.ini", "zhang42", "123");
        subject().checkPermissions("system:user:update");
        subject().checkPermissions("system:user:delete");
        subject().checkPermissions("system:user:update", "system:user:delete");
        subject().checkPermissions("system:user:update,delete");
    }


    @Test
    public void testCheckAll51Pemission(){//单个资源全部权限
        login("shiro-permission.ini", "zhang51", "123");
        subject().checkPermissions("system:user:create,delete,update:view");
    }

    @Test
    public void testCheckAll52Pemission(){//单个资源全部权限
        login("shiro-permission.ini", "zhang52", "123");
        subject().checkPermissions("system:user:*");
        subject().checkPermissions("system:user");
    }

    @Test
    public void testCheckAll53Pemission(){//单个资源全部权限
        login("shiro-permission.ini", "zhang53", "123");
        subject().checkPermissions("system:user:*");
        subject().checkPermissions("system:user");
    }


    @Test
    public void testCheckAll61Pemission(){//全部资源全部权限
        login("shiro-permission.ini", "zhang61", "123");
        subject().checkPermissions("user:view");
//      subject().checkPermissions("system:user:view");//UnauthorizedException: Subject does not have permission [system:user:view]
    }

    @Test
    public void testCheckAll62Pemission(){//全部资源全部权限
        login("shiro-permission.ini", "zhang62", "123");
        subject().checkPermissions("system:user:view");
    }


    @Test
    public void testCheckInstance71Pemission(){//单个实例单个权限
        login("shiro-permission.ini", "zhang71", "123");
        subject().checkPermissions("user:view:1");
    }

    @Test
    public void testCheckInstance72Pemission(){//单个实例多个权限
        login("shiro-permission.ini", "zhang72", "123");
        subject().checkPermissions("user:update,delete:1");
        subject().checkPermissions("user:update:1", "user:delete:1");
    }
    @Test
    public void testCheckInstance73Pemission(){//单个实例所有权限
        login("shiro-permission.ini", "zhang73", "123");
        subject().checkPermissions("user:update:1", "user:delete:1", "user:view:1");
    }
    @Test
    public void testCheckInstance74Pemission(){//所有实例单个权限
        login("shiro-permission.ini", "zhang74", "123");
        subject().checkPermissions("user:auth:1", "user:auth:2");
    }
    @Test
    public void testCheckInstance75Pemission(){//所有实例所有权限
        login("shiro-permission.ini", "zhang75", "123");
        subject().checkPermissions("user:view:1", "user:auth:2");
    }

    @Test
    public void testCustomIsPermitted() {
        login("shiro-authorizer.ini", "zhang", "123");
        //判断拥有权限：user:create
        Assert.assertTrue(subject().isPermitted("user1:update"));
        Assert.assertTrue(subject().isPermitted("user2:update"));
        //通过二进制位的方式表示权限
        Assert.assertTrue(subject().isPermitted("+user1+2"));//新增权限
        Assert.assertTrue(subject().isPermitted("+user1+8"));//查看权限
        Assert.assertTrue(subject().isPermitted("+user2+10"));//新增及查看
        Assert.assertFalse(subject().isPermitted("+user1+4"));//没有删除权限
        Assert.assertTrue(subject().isPermitted("menu:view"));// 通过MyRolePermissionResolver 解析得到的权限
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
