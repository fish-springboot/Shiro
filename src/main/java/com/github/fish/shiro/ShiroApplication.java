package com.github.fish.shiro;

import org.apache.shiro.realm.Realm;
import org.apache.shiro.realm.text.TextConfigurationRealm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ShiroApplication {

    public static void main(String[] args) {
    }

    /**
     * 配置 Realm bean.
     */
    @Bean
    public Realm realm() {
        TextConfigurationRealm realm = new TextConfigurationRealm();

        // 配置了两个用户
        realm.setUserDefinitions("Jon=password,user\n" +
                "Admin=password,admin");

        realm.setRoleDefinitions("admin=read,write\n" +
                "user=read");
        realm.setCachingEnabled(true);
        return realm;
    }
}
