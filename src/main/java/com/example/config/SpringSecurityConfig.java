package com.example.config;

import com.example.util.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
@Configuration
@EnableMethodSecurity(prePostEnabled = true)
public class SpringSecurityConfig {

    @Autowired
    private UserDetailsService userDetailsService;

//    @Bean
//    public AuthenticationProvider authenticationProvider() {
//        // authentication (login,password)
//        String password = UUID.randomUUID().toString();
//        System.out.println("User Password mazgi: " + password);
//
//        UserDetails user = User.builder()
//                .username("user")
//                .password("{bcrypt}$2a$10$1pFH3qmvYwdovvbXeUNrBuDExQBCEaHaiqYp1jTggEAfpV3nx1QLu")
//                .roles("USER")
//                .build();
//
//        UserDetails admin = User.builder()
//                .username("admin")
//                .password("{noop}12345")
//                .roles("ADMIN")
//                .build();
//        final DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
//        authenticationProvider.setUserDetailsService(new InMemoryUserDetailsManager(user, admin));
//        return authenticationProvider;
//    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        // authentication (login,password)
        final DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService);
//        authenticationProvider.setPasswordEncoder(NoOpPasswordEncoder.getInstance());
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;
    }

    private PasswordEncoder passwordEncoder() {
        return new PasswordEncoder() {
            @Override
            public String encode(CharSequence rawPassword) {
                return rawPassword.toString();
            }

            @Override
            public boolean matches(CharSequence rawPassword, String encodedPassword) {
                return MD5Util.encode(rawPassword.toString()).equals(encodedPassword);
            }
        };
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        // authorization (ROLE)
        http.authorizeHttpRequests((c) ->
               c.requestMatchers("/api/v1/auth/**").permitAll()
                .requestMatchers("/api/v1/news/**").permitAll()
                .requestMatchers(HttpMethod.GET, "/api/v1/region/language").permitAll()
                .requestMatchers(HttpMethod.GET, "/api/v1/tag/language").permitAll()
                .requestMatchers(HttpMethod.GET, "/api/v1/category/language").permitAll()
                .requestMatchers(HttpMethod.GET, "/api/v1/article_type/language").permitAll()
                .requestMatchers("/api/v1/email_history/open/**").permitAll()
                .requestMatchers("/api/v1/profile/open/**").permitAll()
                .requestMatchers("/api/v1/comment/open/**").permitAll()
                .requestMatchers("/api/v1/attach/open/**").permitAll()
                .requestMatchers("/api/v1/article/open/**").permitAll()
//                .requestMatchers("/api/v1/region/admin", "/api/v1/region/admin/**").hasRole("ROLE_ADMIN")
//                .requestMatchers( "/api/v1/category/admin/**").hasRole("ROLE_ADMIN")
//                .requestMatchers( "/api/v1/article_type/admin/**").hasRole("ROLE_ADMIN")
//                .requestMatchers( "/api/v1/article/moderator/**").hasRole("ROLE_MODERATOR")
//                .requestMatchers( "/api/v1/article/publisher/**").hasRole("PUBLISHER")
//                .requestMatchers("/api/v1/tag/admin", "/api/v1/tag/admin/**").hasRole("ROLE_ADMIN")
//                .requestMatchers("/api/v1/profile/admin", "/api/v1/profile/admin/**").hasRole("ROLE_ADMIN")
//                .requestMatchers("/api/v1/email_history/admin/**").hasRole("ROLE_ADMIN")
//                .requestMatchers("/api/v1/comment/admin/**").hasRole("ROLE_ADMIN")
//                .requestMatchers("/api/v1/attach/admin/**").hasRole("ROLE_ADMIN")
//                .requestMatchers("/api/v1/comment//admin/delete").hasAnyRole("ROLE_ADMIN", "ROLE_USER")
//                .requestMatchers("/api/v1/comment_like/admin/**").hasRole("ROLE_ADMIN")
                .anyRequest().authenticated()
                ).httpBasic(Customizer.withDefaults());

        http.csrf(AbstractHttpConfigurer::disable).cors(AbstractHttpConfigurer::disable);
        return http.build();

//                .and().httpBasic();
//        http.csrf().disable();

    }
}
