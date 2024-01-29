package com.yi.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.Value;

import java.io.Serializable;
import java.time.Instant;

/**
 * DTO for {@link User}
 */
@Value
@Getter
@Setter
public class UserDto implements Serializable {
    Long id;
    String username;
    String password;
    String email;
    String role;
    String provider;
    String providerId;
    Instant createdAt;
    OauthProvider oAuthProvider;
}