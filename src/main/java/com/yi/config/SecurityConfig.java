package com.yi.config;

import com.yi.auth.PrincipalOauth2UserService;
import com.yi.handler.OAuthLoginFailureHandler;
import com.yi.handler.OAuthLoginSuccessHandler;
import com.yi.service.UserServiceImpl;
import de.codecentric.boot.admin.server.config.AdminServerProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;

import java.util.Arrays;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true)
@RequiredArgsConstructor
public class SecurityConfig {

    private final PrincipalOauth2UserService oauth2UserService;
    private final AdminServerProperties adminServer;
    @Autowired
    OAuthLoginSuccessHandler oAuthLoginSuccessHandler;
    @Autowired
    OAuthLoginFailureHandler oAuthLoginFailureHandler;
    @Autowired
    UserServiceImpl userService;
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("https://example.com"));
        configuration.setAllowedMethods(Arrays.asList("GET","POST"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
                .cors(configurer -> configurer.configurationSource(corsConfigurationSource()))
                .authorizeHttpRequests(registry ->
                        registry.requestMatchers("/user/**").authenticated()
                                .requestMatchers("/manager/**").hasAnyRole("MANAGER", "ADMIN") // prefix(ROLE_)이 자동으로 붙기 때문에 여기선 prefix를 제외한 문자열만 써주면 된다
                                // 단, DB에는 ROLE_을 붙여서 적어줘야 제대로 맵핑이 된다.
                                .requestMatchers("/admin/**").hasRole("ADMIN")
                                .anyRequest().permitAll()

                )
                .csrf(AbstractHttpConfigurer::disable)
                .formLogin(configurer ->
                                configurer.loginPage("/login")
                                        .loginProcessingUrl("/loginProc")
                                        .defaultSuccessUrl("/")
                )
                .rememberMe(Customizer.withDefaults())
                .oauth2Login(configurer -> configurer
                        .loginPage("/login") // google login page와 로그인 페이지를 맵핑 시켜줍니다.
                        .userInfoEndpoint(config -> config.userService(oauth2UserService))
                        .successHandler(oAuthLoginSuccessHandler)
                        .failureHandler(oAuthLoginFailureHandler)
                );

        return http.build();
    }


}
