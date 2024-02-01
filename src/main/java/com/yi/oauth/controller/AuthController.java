package com.yi.oauth.controller;

import com.yi.oauth.kakao.KakaoLoginParams;
import com.yi.oauth.naver.NaverLoginParams;
import com.yi.oauth.entity.AuthTokens;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.yi.oauth.service.OAuthLoginService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {
    private final OAuthLoginService oAuthLoginService;

    @PostMapping("/kakao")
    public ResponseEntity<AuthTokens> loginKakao(@RequestBody KakaoLoginParams params) {
        System.out.println("loginKakao1--->"+params.getAuthorizationCode());
        System.out.println("loginKakao2--->"+params.makeBody());
        return ResponseEntity.ok(oAuthLoginService.login(params));
    }

    @PostMapping("/naver")
    public ResponseEntity<AuthTokens> loginNaver(@RequestBody NaverLoginParams params) {
        return ResponseEntity.ok(oAuthLoginService.login(params));
    }
}
