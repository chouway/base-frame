package com.base.shiro.test.authenticate.bean;

import org.apache.shiro.authc.*;
import org.apache.shiro.realm.Realm;

/**
 * MySingleRealm
 * @author zhouyw
 * @date 2016.11.02
 */
public class MySingleRealm1 implements Realm {
    @Override
    public String getName() {
        return "mySingleRealm1";
    }
    @Override
    public boolean supports(AuthenticationToken token) {
        //仅支持 UsernamePasswordToken 类型的 Token
        return token instanceof UsernamePasswordToken;
    }
    @Override
    public AuthenticationInfo getAuthenticationInfo(AuthenticationToken token) throws
            AuthenticationException {
        String username = (String)token.getPrincipal(); //得到用户名
        String password = new String((char[])token.getCredentials()); //得到密码
        if(!"zhang".equals(username)) {
            throw new UnknownAccountException(); //如果用户名错误
        }
        if(!"123".equals(password)) {
            throw new IncorrectCredentialsException(); //如果密码错误
        }
        //如果身份认证验证成功，返回一个 AuthenticationInfo 实现；
        return new SimpleAuthenticationInfo(username, password, getName());
    }
}