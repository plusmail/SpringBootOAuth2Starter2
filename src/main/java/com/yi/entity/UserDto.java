package com.yi.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.Value;

import java.io.Serializable;
import java.time.Instant;

/**
 * DTO for {@link User}
 */
@Getter
@Setter
@ToString
@Value
public class UserDto implements Serializable {
    int id;
    String username;
    String password;
    String email;
    String role;
    String provider;
    String providerId;
    Instant createdAt;
}