package com.yi.oauth.controller;

import com.yi.oauth.kakao.KakaoLoginParams;
import com.yi.oauth.naver.NaverLoginParams;
import com.yi.oauth.entity.AuthTokens;
import lombok.RequiredArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.yi.oauth.service.OAuthLoginService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {
    private final OAuthLoginService oAuthLoginService;

    @GetMapping("/kakao")
    public ResponseEntity<AuthTokens> loginKakao(@Param("code") String code) {

        KakaoLoginParams params = new KakaoLoginParams();
        params.setAuthorizationCode(code);
        System.out.println("loginKakao1--->"+params.getAuthorizationCode());
        System.out.println("loginKakao2--->"+params.makeBody());
        return ResponseEntity.ok(oAuthLoginService.login(params));
    }

    @GetMapping("/naver")
    public ResponseEntity<AuthTokens> loginNaver(@Param("code") String code) {
        NaverLoginParams params = new NaverLoginParams();
        params.setAuthorizationCode(code);
        System.out.println("loginNaver1--->"+params.getAuthorizationCode());
        System.out.println("loginNaver1--->"+params.makeBody());
        return ResponseEntity.ok(oAuthLoginService.login(params));
    }
}
