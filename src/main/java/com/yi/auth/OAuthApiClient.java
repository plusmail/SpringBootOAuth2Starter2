package com.yi.auth;

import com.yi.entity.OauthProvider;

public interface OAuthApiClient {
    OauthProvider oAuthProvider();
    String requestAccessToken(OauthLoginParams params);
    OauthInfoResponse requestOauthInfo(String accessToken);
}