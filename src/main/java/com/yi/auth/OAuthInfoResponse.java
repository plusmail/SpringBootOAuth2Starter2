package com.yi.auth;

import com.yi.entity.OauthProvider;

public interface OAuthInfoResponse {
    String getEmail();
    String getUsername();
    OauthProvider getOAuthProvider();
}