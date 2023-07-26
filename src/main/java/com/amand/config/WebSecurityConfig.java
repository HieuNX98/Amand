package com.amand.config;

import com.amand.Security.CustomSuccessHandler;
import com.amand.service.impl.UserDetailServiceimpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailServiceimpl userDetailService;

    @Autowired
    private CustomSuccessHandler customSuccessHandler;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
       auth.userDetailsService(userDetailService).passwordEncoder(passwordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .cors().and()
                .csrf().ignoringAntMatchers("/j_spring_security_check") // Ignore CSRF for login processing URL
                .and()
                .authorizeRequests()
                .antMatchers("/dang-nhap").permitAll()
                .antMatchers("/dang-ky").permitAll()
                .antMatchers("/").permitAll()
                .antMatchers("/admin/**").hasRole("ADMIN")
                .and()
                .exceptionHandling().accessDeniedPage("/403")
                .and()
                .formLogin()
                .loginProcessingUrl("/j_spring_security_check")
                .loginPage("/dang-nhap")
                .usernameParameter("username")
                .passwordParameter("password")
                .failureUrl("/dang-nhap")
                .successHandler(customSuccessHandler)
                .and()
                .logout() // Add logout configuration here
                .logoutUrl("/perform-logout")
                .logoutSuccessUrl("/dang-nhap");
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
