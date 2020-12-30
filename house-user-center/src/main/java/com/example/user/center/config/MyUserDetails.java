package com.example.user.center.config;

/**
 * @author shihao
 * @Title: MyUserDetails
 * @ProjectName Second-order-center
 * @Description:
 * @date Created in
 * @Version: $
 */
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

/**
 * 实现了UserDetails接口，只留必需的属性，也可添加自己需要的属性
 * @author 程就人生
 *
 */
@Component
public class MyUserDetails implements UserDetails {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    //登录用户名
    private String username;
    //登录密码
    private String password;

    private String token;

    private Collection<? extends GrantedAuthority> authorities;

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setAuthorities(Collection<? extends GrantedAuthority> authorities) {
        this.authorities = authorities;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
