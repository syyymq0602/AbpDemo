package securitydemo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import securitydemo.handle.CustomAccessDeniedHandler;

import javax.sql.DataSource;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final CustomAccessDeniedHandler customAccessDeniedHandler;
    private final DataSource dataSource;

    public SecurityConfig(
            CustomAccessDeniedHandler customAccessDeniedHandler,
            DataSource dataSource)
    {
        this.customAccessDeniedHandler = customAccessDeniedHandler;
        this.dataSource = dataSource;
    }

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
//                .antMatchers(HttpMethod.GET,"/roles").hasAnyRole("bc")
                .antMatchers("/ip").hasIpAddress("192.168.196.54")
                // 所有请求都必须被认证
                .anyRequest().authenticated();
                // 自定义请求权限认证
//        .anyRequest().access("@myServiceImpl.hasPermission(request,authentication)");

        // 关闭csrf防护
        http.csrf().disable();

        // 自定义拒绝访问处理器
        http.exceptionHandling()
                .accessDeniedHandler(customAccessDeniedHandler);

        http.rememberMe()
                // 定义Token过期时间(s)
                .tokenValiditySeconds(60)
                // 定义 记住 功能Token存储数据库表
                .tokenRepository(persistentTokenRepository());

        http.logout()
                // 自定义注销逻辑
                .logoutUrl("/user/logout")
                // 退出登录跳转页面
                .logoutSuccessUrl("/login.html");
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public PersistentTokenRepository persistentTokenRepository(){
        JdbcTokenRepositoryImpl jdbcTokenRepository = new JdbcTokenRepositoryImpl();
        jdbcTokenRepository.setDataSource(dataSource);
       // 自动建表，第一次启动时需要，第二启动注释掉
//        jdbcTokenRepository.setCreateTableOnStartup(true);
        return jdbcTokenRepository;
    }
}
