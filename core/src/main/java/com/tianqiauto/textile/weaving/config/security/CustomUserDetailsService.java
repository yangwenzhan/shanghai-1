package com.tianqiauto.textile.weaving.config.security;

import com.tianqiauto.textile.weaving.model.base.Role;
import com.tianqiauto.textile.weaving.model.base.User;
import com.tianqiauto.textile.weaving.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

/**
 * @ClassName UserDetailsServiceImpl
 * @Description TODO
 * @Author xingxiaoshuai
 * @Date 2019-02-19 16:33
 * @Version 1.0
 **/
@Component
public class CustomUserDetailsService implements UserDetailsService
{
    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = userRepository.findByUsername(username);
        CustomUserDetails customUserDetails = null;

        if(user != null){
            customUserDetails = new CustomUserDetails();
            customUserDetails.setUser(user);
        }else {
            throw new UsernameNotFoundException("用户名"+username+"不存在，请检查");
        }
        return  customUserDetails;
    }
}
