package com.example.usermanagement.config;

import com.example.usermanagement.domain.service.CustomUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final CustomUserDetailsService customUserDetailsService;
    private final JwtFilter jwtFilter;
    private static final String ADMIN = "ADMIN";
    private static final String STAFF = "STAFF";
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("api/v1/system/**").hasRole(ADMIN)
//                                .requestMatchers("api/v1/admin/**").hasAnyRole(STAFF, ADMIN)
                                .requestMatchers("api/v1/users/**").hasAnyRole(ADMIN, STAFF)
                                .requestMatchers("api/v1/roles/**").hasAnyRole(ADMIN, STAFF)
                                .requestMatchers("api/v1/suppliers/**").hasAnyRole(ADMIN, STAFF)
                                .requestMatchers("api/v1/categories/**").hasAnyRole(ADMIN, STAFF)
                                .requestMatchers("api/v1/customers/**").hasAnyRole(ADMIN, STAFF)
                                .requestMatchers("api/v1/products/**").hasAnyRole(ADMIN, STAFF)
                                .requestMatchers("api/v1/carts/**").hasAnyRole(ADMIN, STAFF)
                                .requestMatchers("api/v1/users/createAdmin").hasAnyRole(ADMIN)
                                .requestMatchers("api/v1/register").permitAll()
                                .requestMatchers("api/v1/login").permitAll()
                                .anyRequest().authenticated()
//                        .requestMatchers("api/v1/send-mail").permitAll()
//                        .requestMatchers("api/v1/reset-pw").permitAll()
                        // Config if you need
                )
                .authenticationManager(authenticationManager())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider())
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return httpSecurity.build();
    }
    @Bean
    AuthenticationManager authenticationManager() {
        return new ProviderManager(authenticationProvider());
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(customUserDetailsService);
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {

        return new BCryptPasswordEncoder();
    }

}
