package com.yi.service;

import com.yi.auth.AuthTokens;
import com.yi.auth.OAuthInfoResponse;
import com.yi.auth.OAuthLoginParams;
import com.yi.auth.RequestOAuthInfoService;
import com.yi.entity.User;
import com.yi.repository.UserRepository;
import com.yi.auth.AuthTokensGenerator;
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
                .oauthProvider(oAuthInfoResponse.getOAuthProvider())
                .build();

        return (Long) userRepository.save(user).getId();
    }
}