package com.yi.auth;

import com.yi.entity.OauthProvider;
import org.springframework.util.MultiValueMap;

public interface OauthLoginParams {
    OauthProvider oAuthProvider();
    MultiValueMap<String, String> makeBody();
}