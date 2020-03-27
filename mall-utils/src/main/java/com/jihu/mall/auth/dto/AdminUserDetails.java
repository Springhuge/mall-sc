package com.jihu.mall.auth.dto;

import com.jihu.mall.ums.model.UmsAdmin;
import com.jihu.mall.ums.model.UmsPermission;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class AdminUserDetails implements UserDetails {


    private UmsAdmin admin;

    private List<UmsPermission> permissionList;

    public AdminUserDetails (UmsAdmin admin, List<UmsPermission> permissions){
        this.admin = admin;
        this.permissionList = permissions;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        //当前用户的权限
        return permissionList.stream()
                .filter(permission -> permission.getValue() != null)
                .map(permission -> new SimpleGrantedAuthority(permission.getValue()))
                .collect(Collectors.toList());
    }

    @Override
    public String getPassword() {
        return admin.getPassword();
    }

    @Override
    public String getUsername() {
        return admin.getUsername();
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
        return admin.getStatus().equals("1");
    }
}
