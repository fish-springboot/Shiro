package com.github.fish.shiro.service;

import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Component;

/**
 * Simple Service with methods protected with annotations.
 *
 * 只有有权限的用户才可以调用先关的Service方法
 */
@Component
@Slf4j
public class SimpleService {

    @RequiresPermissions("write")
    public void writeRestrictedCall() {
        log.info("executing method that requires the 'write' permission");
    }

    @RequiresPermissions("read")
    public void readRestrictedCall() {
        log.info("executing method that requires the 'read' permission");
    }
}
