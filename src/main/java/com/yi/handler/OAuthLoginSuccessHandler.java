package com.yi.handler;

import com.yi.entity.OauthProvider;
import com.yi.entity.User;
import com.yi.repository.UserRepository;
import com.yi.service.UserServiceImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Component;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;

import java.io.IOException;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Component
public class OAuthLoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    @Autowired
    UserServiceImpl userService;
    @Autowired
    UserRepository userRepository;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {

        // 토큰에서 email, oauthType 추출
        OAuth2AuthenticationToken token = (OAuth2AuthenticationToken) authentication;

        String email = null;
        OauthProvider oauthType = OauthProvider.valueOf(token.getAuthorizedClientRegistrationId());


        if("kakao".equalsIgnoreCase(String.valueOf(oauthType))) {
            email = ((Map<String, Object>) token.getPrincipal().getAttribute("kakao_account")).get("email").toString();
        }
        else if("google".equalsIgnoreCase(String.valueOf(oauthType))) {
            email = token.getPrincipal().getAttribute("email").toString();
        }
        else if("naver".equalsIgnoreCase(String.valueOf(oauthType))) {
            email = ((Map<String, Object>) token.getPrincipal().getAttribute("response")).get("email").toString();
        }

        log.info("LOGIN SUCCESS : {} FROM {}", email, oauthType);

        Optional<User> user = userService.getUserByEmailAndOAuthType(email, oauthType);

        // 세션에 user 저장
        log.info("USER SAVED IN SESSION");
        HttpSession session = request.getSession();
        session.setAttribute("user", user);

        super.onAuthenticationSuccess(request, response, authentication);
    }

}