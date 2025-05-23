package com.ysc.security;

import com.ysc.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.servlet.util.matcher.MvcRequestMatcher;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Autowired
    private JwtAuthFilter jwtAuthenticationFilter;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http, HandlerMappingIntrospector introspector) throws Exception {
        MvcRequestMatcher.Builder mvcMatcherBuilder = new MvcRequestMatcher.Builder(introspector).servletPath("/");
        http.csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth ->
//                        auth.requestMatchers(
//                                        /*
//                                        "/error/**",
//                                        "/api/register",             //用戶註冊
//                                        "/api/register/check",       //用戶註冊確認
//                                        "/api/auth",                 //用戶登入
//                                        "/api/password/forgot",      //忘記密碼
//                                        "/api/verification/send",    //發送驗證碼
//                                        "/api/verification/check"   //檢查驗證碼
//                                         */
//                                        "/home",
//                                        "/act",
//                                        "/site",
//                                        "/user/login",
//                                        "/user/register",
//                                        "/user/check",
//                                        "/user/logout"
//                                ).permitAll()

                        auth.anyRequest().permitAll()
                                // 其他路徑需要認證
//                                .anyRequest().authenticated()
                )
                .sessionManagement(sessionManagement -> {
                    sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS); // 無狀態
                })
                //.authenticationProvider(authenticationProvider) // 認證提供者
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
            throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}