package com.github.fish.shiro.service;

import com.github.fish.shiro.ShiroApplicationTests;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.subject.Subject;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.*;

@Slf4j
public class SimpleServiceTest extends ShiroApplicationTests {
    @Autowired
    SecurityManager securityManager;

    @Autowired
    SimpleService simpleService;

    /**
     * 设置 SecurityManager Bean
     */
    @Before
    public void initSecurityManager(){
        SecurityUtils.setSecurityManager(securityManager);
    }

    /**
     * 未登录用户应当无法调用read方法
     */
    @Test(expected = AuthorizationException.class)
    public void readRestrictedCallNotLogin() {
        // 获得一个subject
        Subject subject = SecurityUtils.getSubject();

        if (subject.isAuthenticated()){
            subject.logout();
        }

        simpleService.readRestrictedCall();
    }

    /**
     * 登录后可以调用read方法
     */
    @Test
    public void readRestrictedCall() {
        // 获得一个subject
        Subject subject = SecurityUtils.getSubject();

        // 通过用户名密码登录
        UsernamePasswordToken token = new UsernamePasswordToken("Jon", "password");
        subject.login(token);

        simpleService.readRestrictedCall();
    }

    /**
     * Jon没有read权限，无法调用write方法
     */
    @Test(expected = AuthorizationException.class)
    public void readRestrictedCall2() {
        // 获得一个subject
        Subject subject = SecurityUtils.getSubject();

        // 通过用户名密码登录
        UsernamePasswordToken token = new UsernamePasswordToken("Jon", "password");
        subject.login(token);

        simpleService.writeRestrictedCall();

    }

    /**
     * Admin应当可以调用write方法
     */
    @Test
    public void writeRestrictedCall() {
        // 获得一个subject
        Subject subject = SecurityUtils.getSubject();

        // 当前subject应该是未登录的
        assertFalse(subject.isAuthenticated());

        // 通过用户名密码登录
        UsernamePasswordToken token = new UsernamePasswordToken("Admin", "password");
        subject.login(token);

        simpleService.writeRestrictedCall();
    }
}