package com.example.forum.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static org.springframework.http.HttpMethod.GET;
import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserDetailsService userDetailsService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        // username password authority
//        auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder);
//    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        // Authentication identifies user
        // Authorization checks if a user can/can't do certain thing

        // Checktoken -> if null -> frontend send request to refresh accesstoken -> if no refresh -> login -> save refresh token in session storage

        // NEED REFRESH endpoint
        // Things to do:
        // allow controller api calls base on role
        // proper order
        // refresh endpoint in usercontroller
        // *** user needs to be able to reach refresh endpoint without being logged in ***
        http
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(STATELESS)
                .and()
                .addFilter(new CustomAuthenticationFilter(authenticationManager()))
                .addFilterBefore(new CustomAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class)
                .authorizeRequests()
//                .antMatchers("/login/**","/api/user/**").permitAll()
                .antMatchers(GET,"/api/user/**").permitAll()
                .anyRequest().authenticated();
    }
}
