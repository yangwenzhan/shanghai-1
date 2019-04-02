package com.tianqiauto.textile.weaving.config.audit;

import com.tianqiauto.textile.weaving.model.base.User;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

/**
 * @ClassName UserAuditorAware
 * @Description TODO
 * @Author xingxiaoshuai
 * @Date 2019-03-27 17:24
 * @Version 1.0
 **/
public class UserAuditorAware implements AuditorAware<String> {
    @Override
    public Optional<String> getCurrentAuditor() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            return null;
        }

        UserDetails userDetails =  (UserDetails)authentication.getPrincipal();
        return Optional.of(userDetails.getUsername());

    }
}
