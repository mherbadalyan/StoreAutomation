package com.example.store_automation.config;

import com.example.store_automation.security.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers(HttpMethod.GET, "/api/**").permitAll()
                .antMatchers(HttpMethod.POST,"/api/auth/signin/**","/swagger-ui/**").permitAll()
                .antMatchers("/store-automation/category/**").hasAuthority("GENERAL")
                .antMatchers("/store-automation/incomeCalc/**").hasAuthority("GENERAL")
                .antMatchers("/store-automation/product/**").hasAuthority("GENERAL")
                .antMatchers(HttpMethod.PUT,"/store-automation/productInBranch/{branchId}/{productInBranchId}/{quantity}/**").hasAuthority("BRANCH")
                .antMatchers(HttpMethod.PUT,"/store-automation/branch/{name}/**").hasAuthority("GENERAL")
                .antMatchers(HttpMethod.PUT,"/store-automation/sales/{id}/{quantity}/**").hasAuthority("BRANCH")
                .antMatchers(HttpMethod.DELETE,"/store-automation/branch/{name}/**").hasAuthority("GENERAL")
                .antMatchers(HttpMethod.PUT,"/store-automation/sales/{id}/{quantity}/**").hasAuthority("BRANCH")
                .antMatchers(HttpMethod.POST,"/store-automation/productInBranch/{branchId}/{productId}/{quantity}/{price}/**").hasAuthority("GENERAL")
                .antMatchers(HttpMethod.POST,"/api/auth/signup/**","/swagger-ui/**").hasAuthority("GENERAL")
                .anyRequest()
                .authenticated()
                .and()
                .httpBasic();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder());
    }

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

}