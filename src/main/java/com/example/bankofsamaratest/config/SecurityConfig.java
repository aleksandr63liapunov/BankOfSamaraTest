package com.example.bankofsamaratest.config;


import com.example.bankofsamaratest.service.Impl.MyUserDetailService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractAuthenticationFilterConfigurer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    @Bean
    public UserDetailsService userDetailsService() {

        return new MyUserDetailService();
    }
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
      return http.csrf(AbstractHttpConfigurer::disable)
              .authorizeHttpRequests(auth -> auth
                      .requestMatchers( "/api/create/**").permitAll()
                      .requestMatchers("/api/contact/**").permitAll()
                      .requestMatchers("/api/transfer_money/start").permitAll()
                      .requestMatchers("/v3/api-docs/**").permitAll()
                      .requestMatchers("/swagger-ui/**").permitAll()
                      .requestMatchers("/swagger-ui.html").permitAll()
                      .requestMatchers("/api/users/**").authenticated())
              .formLogin(AbstractAuthenticationFilterConfigurer::permitAll)
              .build();
    }
    @Bean
    public AuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider provider=new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService());
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }
//    @Bean
//    public UserDetailsService userDetailsService() {
//
//        return new MyUserDetailService();
//     }
//
//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//      return http.csrf(AbstractHttpConfigurer::disable)
//              .authorizeHttpRequests(auth -> auth.requestMatchers( "/api/create/**","/api/users/**").permitAll()
//                      .requestMatchers("/api/contact/app/**").authenticated())
//              .formLogin(AbstractAuthenticationFilterConfigurer::permitAll)
//              .build();
//    }
//    @Bean
//    public AuthenticationProvider authenticationProvider(){
//        DaoAuthenticationProvider provider=new DaoAuthenticationProvider();
//        provider.setUserDetailsService(userDetailsService());
//        provider.setPasswordEncoder(passwordEncoder());
//        return provider;
//    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
