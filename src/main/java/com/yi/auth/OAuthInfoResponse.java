package com.yi.auth;

import com.yi.entity.OAuthProvider;

public interface OAuthInfoResponse {
    String getEmail();
    String getUsername();
    OAuthProvider getOAuthProvider();
}