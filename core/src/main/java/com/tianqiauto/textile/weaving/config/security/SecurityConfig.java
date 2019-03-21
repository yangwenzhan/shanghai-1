package com.tianqiauto.textile.weaving.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

/**
 * @ClassName SecurityConfig
 * @Description TODO
 * @Author xingxiaoshuai
 * @Date 2019-02-19 17:00
 * @Version 1.0
 **/
@Configuration
@EnableWebSecurity  //开启Spring Security的功能
@EnableGlobalMethodSecurity(prePostEnabled = true) //可以开启security的注解，我们可以在需要控制权限的方法上面使用@PreAuthorize，@PreFilter这些注解
public class SecurityConfig extends WebSecurityConfigurerAdapter
{

    @Autowired
    private CustomUserDetailsService userDetailsService;


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());  //加密UserDetails中包含的密码信息，并和数据库中
    }




    @Override
    protected void configure(HttpSecurity http) throws Exception {


//        http
//                    .authorizeRequests()
//                    .anyRequest().permitAll()
//                .and()
//                     .formLogin()
//                .and()
//                     .httpBasic()
//                .and()
//                    .csrf().disable();


        http.formLogin()
                    .loginPage("/login.html")
                    .loginProcessingUrl("/authentication/form")
                    .failureUrl("/login-error.html")
//                    .defaultSuccessUrl("/", true)
                .and()
                    .authorizeRequests()
                    .antMatchers("/login.html","/login-error.html","/layuiadmin/**","js/**","css/**").permitAll()  //不permit login.html页面会出现重定向次数过多错误
                    .anyRequest()
                    .authenticated()
                .and()
                    .csrf().disable();


        // 允许同源的iframe页面嵌套
        http.headers().frameOptions().sameOrigin();
    }




    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
//        return NoOpPasswordEncoder.getInstance();   //BCryptPasswordEncoder
    }
}
