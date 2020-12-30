package com.example.user.center.config;

/**
 * @author shihao
 * @Title: CustomPermissionEvaluator
 * @ProjectName Second-order-center
 * @Description:
 * @date Created in
 * @Version: $
 */
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Component
public class CustomPermissionEvaluator implements PermissionEvaluator {

    @Override
    public boolean hasPermission(Authentication authentication, Object targetUrl, Object targetPermission) {
        // 获得loadUserByUsername()方法的结果
        MyUserDetails user = (MyUserDetails)authentication.getPrincipal();
        // 获得loadUserByUsername()中注入的角色
        String authoritie = user.getUsername();
    List<String> authorities = new ArrayList<>();
        authorities.add(authoritie);
        // 遍历用户所有角色
        System.out.println("访问的url{}"+targetUrl);
        System.out.println("权限{}"+targetPermission);
        for(String authority : authorities) {
            System.out.println("验证");
            // 得到角色所有的权限
            List<SysPermission> permissionList = new ArrayList<>();
            SysPermission permission = new SysPermission();
            String a = "r";
            String url = "/getcort";
            List<String> strings = new ArrayList<>();
            strings.add(a);
            permission.setPermissions(strings);
            permission.setUrl(url);
            permissionList.add(permission);
            // 遍历permissionList
            for(SysPermission sysPermission : permissionList) {
                // 获取权限集
                List permissions = sysPermission.getPermissions();
                System.out.println(permissions);
                System.out.println(sysPermission.getUrl());
                // 如果访问的Url和权限用户符合的话，返回true
                if(targetUrl.equals(sysPermission.getUrl())
                        && permissions.contains(targetPermission)) {
                    return true;
                }
            }

        }

        return false;
    }

    @Override
    public boolean hasPermission(Authentication authentication, Serializable serializable, String s, Object o) {
        return false;
    }
}

