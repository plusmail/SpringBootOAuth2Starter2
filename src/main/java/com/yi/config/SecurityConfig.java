package com.yi.config;

import com.yi.oauth.handler.OAuthLoginFailureHandler;
import com.yi.oauth.handler.OAuthLoginSuccessHandler;
import com.yi.oauth.service.OAuthLoginService;
import jakarta.servlet.DispatcherType;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
@Configuration
@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true)
@RequiredArgsConstructor
public class SecurityConfig {

    private final OAuthLoginService oAuthLoginService;
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
                        .userInfoEndpoint(config -> config.userService(oAuthLoginService))
                );

        return http.build();
    }


}