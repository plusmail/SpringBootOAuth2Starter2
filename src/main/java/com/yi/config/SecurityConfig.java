package com.yi.demo.config;

import com.yi.oauth.PrincipalOauth2UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true)
@RequiredArgsConstructor
public class SecurityConfig {

    private final PrincipalOauth2UserService oauth2UserService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http.csrf(configurer -> configurer.disable())
                .authorizeHttpRequests(registry ->
                        registry.requestMatchers("/user/**").authenticated()
                                .requestMatchers("/manager/**").hasAnyRole("MANAGER", "ADMIN") // prefix(ROLE_)이 자동으로 붙기 때문에 여기선 prefix를 제외한 문자열만 써주면 된다
                                // 단, DB에는 ROLE_을 붙여서 적어줘야 제대로 맵핑이 된다.
                                .requestMatchers("/admin/**").hasRole("ADMIN")
                                .anyRequest().permitAll()
                )
                .formLogin(configurer ->
                                configurer.loginPage("/login")
                                        .loginProcessingUrl("/loginProc")
                                        .defaultSuccessUrl("/")
                )
                .oauth2Login(configurer -> configurer
                        .loginPage("/login") // google login page와 로그인 페이지를 맵핑 시켜줍니다.
                        .userInfoEndpoint(config -> config.userService(oauth2UserService))
                );

        return http.build();
    }


}
