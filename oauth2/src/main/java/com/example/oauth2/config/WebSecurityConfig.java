package com.example.oauth2.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // 表单提交
        http.formLogin().permitAll();

        // 授权认证
        http.authorizeRequests()
                // 放行
                .antMatchers("/login/**","/logout/**","/oauth/**").permitAll()
                .antMatchers(HttpMethod.GET,"/demo").hasAnyAuthority("admin")
//                .antMatchers(HttpMethod.GET,"/roles").hasAnyRole("bc")
                .antMatchers("/ip").hasIpAddress("192.168.196.54")
                // 所有请求都必须被认证
                .anyRequest().authenticated();
        // 自定义请求权限认证
//        .anyRequest().access("@myServiceImpl.hasPermission(request,authentication)");

        // 关闭csrf防护
        http.csrf().disable();


//        http.logout()
//                // 自定义注销逻辑
//                .logoutUrl("/user/logout")
//                // 退出登录跳转页面
//                .logoutSuccessUrl("/login.html");
    }

}
