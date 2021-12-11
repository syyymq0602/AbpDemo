package securitydemo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // 表单提交
        http.formLogin()
                // 自定义表单提交的用户名
                .usernameParameter("user")
                // 自定义表单提交的密码名
                .passwordParameter("pass")
                // 发现是/login时认为时登陆，路径必须和表单一致
                .loginProcessingUrl("/login")
                // 自定义登陆页面
                .loginPage("/login.html")
                    // 登陆后跳转页面，必须为POST请求
                    .successForwardUrl("/toMain")
                        // 登录失败跳转请求 必须POST请求
                        .failureForwardUrl("/toError");

        // 授权认证
        http.authorizeRequests()
                // 放行login.html
                .antMatchers("/login.html","/error.html").permitAll()
                // 所有请求都必须被认证
                .anyRequest().authenticated();

        http.csrf().disable();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
