package com.yi.oauth;

import com.yi.demo.entity.OAuthProvider;

public interface OAuthInfoResponse {
    String getEmail();
    String getUsername();
    OAuthProvider getOAuthProvider();
}