package ru.chia2k.vnp.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import ru.chia2k.security.client.filter.JwtFilter;
import ru.chia2k.security.client.service.CommonPrincipalProvider;
import ru.chia2k.security.client.service.JwtValidator;
import ru.chia2k.security.client.service.JwtValidatorImpl;
import ru.chia2k.security.client.service.SecurityContextPrincipalProvider;


@Configuration
public class SecurityConfig {
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http, JwtFilter filter) throws Exception {
        return http
                .httpBasic().disable()
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeHttpRequests(
                        auth -> auth
                                .requestMatchers("/actuator/**").permitAll()
                                .requestMatchers("/v3/api-docs/**", "/swagger-ui/**", "/swagger-ui.html").permitAll()
                                .anyRequest().authenticated()
                                .and()
                                .addFilterAfter(filter, UsernamePasswordAuthenticationFilter.class)
                )
                .build();
    }

    @Bean
    public JwtValidator jwtValidator(@Value("${application.jwt.secret.access}") String jwtAccessSecret) {
        return new JwtValidatorImpl(jwtAccessSecret);
    }

    @Bean
    public JwtFilter jwtFilter(JwtValidator jwtValidator) {
        return new JwtFilter(jwtValidator);
    }

    @Bean
    public CommonPrincipalProvider principalProvider() {
        return new SecurityContextPrincipalProvider();
    }
}
