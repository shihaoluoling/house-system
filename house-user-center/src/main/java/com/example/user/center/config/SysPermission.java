package com.example.user.center.config;

import java.util.List;

/**
 * @author shihao
 * @Title: SysPermission
 * @ProjectName Second-order-center
 * @Description:
 * @date Created in
 * @Version: $
 */
public class SysPermission {
    private List<String> permissions;
    private String url;

    public List<String> getPermissions() {
        return permissions;
    }

    public void setPermissions(List<String> permissions) {
        this.permissions = permissions;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
