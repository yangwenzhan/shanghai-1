package com.tianqiauto.textile.weaving.config.security;

import com.tianqiauto.textile.weaving.model.base.Permission;
import com.tianqiauto.textile.weaving.model.base.Role;
import com.tianqiauto.textile.weaving.model.base.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @ClassName CustomUserDetails
 * @Description TODO
 * @Author xingxiaoshuai
 * @Date 2019-02-20 09:10
 * @Version 1.0
 **/

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomUserDetails implements UserDetails {

    private User user;

    /**
     * 在Security中，角色和权限共用GrantedAuthority接口，唯一的不同角色就是多了个前缀"ROLE_"，
     * 而且它没有Shiro的那种从属关系，即一个角色包含哪些权限等等。在Security看来角色和权限时一样的，
     * 它认证的时候，把所有权限（角色、权限）都取出来，而不是分开验证。
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        List<GrantedAuthority> list = new ArrayList<>();

        for(Role role : user.getRoles()){
            list.add(new SimpleGrantedAuthority("ROLE_"+role.getName()));
            for(Permission permission: role.getPermissions()){
                SimpleGrantedAuthority grantedAuthority =  new SimpleGrantedAuthority(permission.getPermissionCode());
                if(!list.contains(grantedAuthority)){
                    list.add(grantedAuthority);
                }
            }
        }


        return list;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
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
}
