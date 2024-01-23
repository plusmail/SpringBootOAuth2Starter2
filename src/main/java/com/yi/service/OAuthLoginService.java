package com.yi.demo.service;

import com.yi.oauth.AuthTokens;
import com.yi.oauth.OAuthInfoResponse;
import com.yi.oauth.OAuthLoginParams;
import com.yi.oauth.RequestOAuthInfoService;
import com.yi.demo.entity.User;
import com.yi.demo.repository.UserRepository;
import com.yi.oauth.AuthTokensGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OAuthLoginService {
    private final UserRepository userRepository;
    private final AuthTokensGenerator authTokensGenerator;
    private final RequestOAuthInfoService requestOAuthInfoService;

    public AuthTokens login(OAuthLoginParams params) {
        OAuthInfoResponse oAuthInfoResponse = requestOAuthInfoService.request(params);
        Long memberId = findOrCreateMember(oAuthInfoResponse);
        return authTokensGenerator.generate(memberId);
    }

    private Long findOrCreateMember(OAuthInfoResponse oAuthInfoResponse) {
        return userRepository.findByEmail(oAuthInfoResponse.getEmail())
                .map(User::getId)
                .orElseGet(() -> newUser(oAuthInfoResponse));
    }

    private Long newUser(OAuthInfoResponse oAuthInfoResponse) {
        User user = User.builder()
                .email(oAuthInfoResponse.getEmail())
                .username(oAuthInfoResponse.getUsername())
                .oAuthProvider(oAuthInfoResponse.getOAuthProvider())
                .build();

        return (Long) userRepository.save(user).getId();
    }
}