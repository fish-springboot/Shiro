package com.github.fish.shiro.service;

import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.subject.Subject;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.*;

@Slf4j
public class SimpleServiceTest {
    @Autowired
    SecurityManager securityManager;

    @Autowired
    SimpleService simpleService;

    @Before
    public void set(){
        SecurityUtils.setSecurityManager(securityManager);
    }

    @Test
    public void writeRestrictedCall() {
        SecurityUtils.setSecurityManager(securityManager);
        // 获得一个subject
        Subject subject = SecurityUtils.getSubject();

        // 当前subject应该是未登录的
        assertFalse(subject.isAuthenticated());

        // 通过用户名密码登录
        UsernamePasswordToken token = new UsernamePasswordToken("joe.coder", "password");
        subject.login(token);

        // joe.coder has the "user" role
        subject.checkRole("user");

        // joe.coder does NOT have the admin role
        assertFalse(subject.hasRole("admin"));

        // joe.coder has the "read" permission
        subject.checkPermission("read");

        try {
            subject.checkPermission("write");
        } catch (AuthorizationException e){
            log.info("当前用户没有write权限");
        }

        // current user is allowed to execute this method.
        simpleService.readRestrictedCall();

        try {
            // but not this one!
            simpleService.writeRestrictedCall();
        }
        catch (AuthorizationException e) {
            log.info("当前用户没有权限 'writeRestrictedCall'");
        }

        // logout
        subject.logout();
        assertFalse(subject.isAuthenticated());
    }

    @Test
    public void readRestrictedCall() {
    }
}