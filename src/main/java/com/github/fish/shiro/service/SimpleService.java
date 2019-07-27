package com.github.fish.shiro.service;

import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Component;

/**
 * 只有有权限的用户才可以调用先关的Service方法
 */
@Component
@Slf4j
public class SimpleService {

    /**
     * 要求用户具有write权限
     */
    @RequiresPermissions("write")
    public void writeRestrictedCall() {
        log.info("调用了 具有write权限 的方法");
    }

    /**
     * 要求用户具有read权限才能调用
     */
    @RequiresPermissions("read")
    public void readRestrictedCall() {
        log.info("调用了 要求用户具有read权限才能调用 的方法");
    }
}
