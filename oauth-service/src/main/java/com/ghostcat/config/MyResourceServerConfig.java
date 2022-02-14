package com.ghostcat.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;

/**
 * 资源服务器
 */
//@Configuration
//@EnableResourceServer
public class MyResourceServerConfig extends ResourceServerConfigurerAdapter {

    @Override
    public void configure(HttpSecurity http) throws Exception {
        //表单登录 方式
        http
//                .formLogin()
//                .loginPage("/authentication/require")
//                //登录需要经过的url请求
//                .loginProcessingUrl("/authentication/form")
//                .successHandler(myAuthenticationSuccessHandler)
//                .failureHandler(myAuthenticationFailHandler);
                .authorizeRequests()
                //只认证user开头的请求
                .antMatchers("/user/*")
                .authenticated()
                .antMatchers("/oauth/token").permitAll()
                .anyRequest()
                .permitAll()
                .and()
                //允许跨域
                .csrf().disable();
    }
}