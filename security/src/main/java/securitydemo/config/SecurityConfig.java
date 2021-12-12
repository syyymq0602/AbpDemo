package securitydemo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import securitydemo.handle.CustomAuthenticationFailureHandler;
import securitydemo.handle.CustomAuthenticationSuccessHandler;

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
                    //  自定义登录成功后处理器，不能和successForwardUrl共用
//                .successHandler(new CustomAuthenticationSuccessHandler("http://192.168.43.213:5500/"))
                        // 登录失败跳转请求 必须POST请求
                        .failureForwardUrl("/toError");
                //  自定义登录失败后处理器，不能和failureForwardUrl共用
//                .failureHandler(new CustomAuthenticationFailureHandler("http://www.baidu.com"));

        // 授权认证
        http.authorizeRequests()
                // 放行
                .antMatchers("/login.html","/error.html").permitAll()
                .antMatchers(HttpMethod.GET,"/demo").hasAnyAuthority("admin")
                .antMatchers(HttpMethod.GET,"/roles").hasAnyRole("bc")
                .antMatchers("/ip").hasIpAddress("192.168.196.54")
                // 所有请求都必须被认证
                .anyRequest().authenticated();

        http.csrf().disable();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
