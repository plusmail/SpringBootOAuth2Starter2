package com.yi.auth;

import com.yi.entity.OauthProvider;

public interface OauthInfoResponse {
    String getEmail();
    String getUsername();
    OauthProvider getOauthProvider();
}