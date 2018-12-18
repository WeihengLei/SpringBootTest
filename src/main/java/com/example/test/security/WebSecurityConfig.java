package com.example.test.security;

import com.example.test.service.LightSwordUserDetailService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true,securedEnabled = true,jsr250Enabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    @Bean
    public UserDetailsService userDetailsService() { //覆盖写userDetailsService方法 (1)
        return new LightSwordUserDetailService();

    }

    @Override
    protected  void configure(HttpSecurity http) throws Exception{
        http.csrf().disable();

        http.authorizeRequests()
                .antMatchers("/").permitAll()//任何时候可以访问
                .antMatchers("/amchart/**",
                        "/bootstrap/**",
                        "/build/**",
                        "/css/**",
                        "/dist/**",
                        "/documentation/**",
                        "/fonts/**",
                        "/js/**",
                        "/pages/**",
                        "/plugins/**"
                ).permitAll() //默认不拦截静态资源的url pattern （2）
                .antMatchers("/cms/**").hasRole("ADMIN")//任何ADMIN用户可以访问/api/**
                .anyRequest().authenticated()
                .and()
                .formLogin().loginPage("/login")// 登录url请求路径 (3)
                .failureUrl("/login?error")
                .permitAll() //登录页面用户任意访问 //.defaultSuccessUrl("/api/test/haha ha").permitAll() // 登录成功跳转路径url(4)
                .and()
                .logout().permitAll();

        http.logout().logoutSuccessUrl("/login"); // 退出默认跳转页面 (5)
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception{
        /*auth
                .inMemoryAuthentication()
                    .withUser("root")
                    .password("root")
                    .roles("USER")
                .and()
                    .withUser("admin").password("admin")
                    .roles("ADMIN", "USER")
                .and()
                    .withUser("user").password("user")
                    .roles("USER");*/

        //AuthenticationManager使用我们的 lightSwordUserDetailService 来获取用户信息
        auth.userDetailsService(userDetailsService()); // （6）

    }

    //默认不拦截静态资源的url pattern。我们也可以用下面的WebSecurity这个方式跳过静态资源的认证
    public void configure(WebSecurity web) throws Exception {
        web
                .ignoring()
                .antMatchers("/static/**");
    }
}
