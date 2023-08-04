package com.example.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import java.util.UUID;

@EnableWebSecurity
@Configuration
public class SpringSecurityConfig {
    @Bean
    public AuthenticationProvider authenticationProvider() {
        // authentication (login,password)
        String password = UUID.randomUUID().toString();
        System.out.println("User Password mazgi: " + password);

        UserDetails user = User.builder()
                .username("user")
                .password("{bcrypt}$2a$10$1pFH3qmvYwdovvbXeUNrBuDExQBCEaHaiqYp1jTggEAfpV3nx1QLu")
                .roles("USER")
                .build();

        UserDetails admin = User.builder()
                .username("admin")
                .password("{noop}12345")
                .roles("ADMIN")
                .build();
        final DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(new InMemoryUserDetailsManager(user, admin));
        return authenticationProvider;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        // authorization (ROLE)
        http.authorizeHttpRequests()
                .requestMatchers("/api/v1/auth/**").permitAll()
                .requestMatchers("/api/v1/news/**").permitAll()
                .requestMatchers(HttpMethod.GET, "/api/v1/region/language").permitAll()
                .requestMatchers(HttpMethod.GET, "/api/v1/tag/language").permitAll()
                .requestMatchers(HttpMethod.GET, "/api/v1/category/language").permitAll()
                .requestMatchers(HttpMethod.GET, "/api/v1/article_type/language").permitAll()
                .requestMatchers("/api/v1/region/admin", "/api/v1/region/admin/**").hasRole("ADMIN")
                .requestMatchers( "/api/v1/category/admin/**").hasRole("ADMIN")
                .requestMatchers( "/api/v1/article_type/admin/**").hasRole("ADMIN")
                .requestMatchers( "/api/v1/article/moderator/**").hasRole("MODERATOR")
                .requestMatchers( "/api/v1/article/publisher/**").hasRole("PUBLISHER")
                .requestMatchers("/api/v1/tag/admin", "/api/v1/tag/admin/**").hasRole("ADMIN")
                .requestMatchers("/api/v1/profile/admin", "/api/v1/profile/admin/**").hasRole("ADMIN")
                .requestMatchers("/api/v1/email_history/admin/**").hasRole("ADMIN")
                .requestMatchers("/api/v1/comment/admin/**").hasRole("ADMIN")
                .requestMatchers("/api/v1/attch/admin/**").hasRole("ADMIN")
                .requestMatchers("/api/v1/comment//admin/delete").hasAnyRole("ADMIN", "USER")
                .requestMatchers("/api/v1/comment_like/admin/**").hasRole("ADMIN")
                .requestMatchers("/api/v1/email_history/open/**").permitAll()
                .requestMatchers("/api/v1/comment_like/open/**").permitAll()
                .requestMatchers("/api/v1/profile/open/**").permitAll()
                .requestMatchers("/api/v1/comment/open/**").permitAll()
                .requestMatchers("/api/v1/attach/open/**").permitAll()
                .requestMatchers("/api/v1/article/open/**").permitAll()
                .anyRequest().authenticated()
                .and().httpBasic();
        http.csrf().disable();
        return http.build();
    }
}
