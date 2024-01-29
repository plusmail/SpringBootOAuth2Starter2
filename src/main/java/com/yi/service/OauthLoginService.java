package com.yi.service;

import com.yi.auth.*;
import com.yi.auth.OauthLoginParams;
import com.yi.entity.User;
import com.yi.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OauthLoginService {
    private final UserRepository userRepository;
    private final AuthTokensGenerator authTokensGenerator;
    private final RequestOAuthInfoService requestOAuthInfoService;

    public AuthTokens login(OauthLoginParams params) {
        OauthInfoResponse oAuthInfoResponse = requestOAuthInfoService.request(params);
        Long memberId = findOrCreateMember(oAuthInfoResponse);
        return authTokensGenerator.generate(memberId);
    }

    private Long findOrCreateMember(OauthInfoResponse oauthInfoResponse) {
        return userRepository.findByEmail(oauthInfoResponse.getEmail())
                .map(User::getId)
                .orElseGet(() -> newUser(oauthInfoResponse));
    }

    private Long newUser(OauthInfoResponse oAuthInfoResponse) {
        User user = User.builder()
                .email(oAuthInfoResponse.getEmail())
                .username(oAuthInfoResponse.getUsername())
                .oauthProvider(oAuthInfoResponse.getOauthProvider())
                .build();

        return (Long) userRepository.save(user).getId();
    }
}