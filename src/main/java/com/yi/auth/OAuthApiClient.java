package com.yi.auth;

import com.yi.entity.OauthProvider;

public interface OAuthApiClient {
    OauthProvider oAuthProvider();
    String requestAccessToken(OAuthLoginParams params);
    OAuthInfoResponse requestOauthInfo(String accessToken);
}