package com.yi.oauth;

import com.yi.auth.PrincipalDetails;
import com.yi.demo.entity.User;
import com.yi.demo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class PrincipalOauth2UserService extends DefaultOAuth2UserService {

    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);
        String username = userRequest.getClientRegistration().getClientId() + oAuth2User.getName();
        Optional<User> find = repository.findByUsername(username);

        if (find.isPresent()) {
            return new PrincipalDetails(find.get(), oAuth2User.getAttributes());
        } else {
            User user = new User()
                    .setUsername(username)
                    .setPassword(passwordEncoder.encode("getInThere"))
                    .setRole("ROLE_USER")
                    .setProvider(userRequest.getClientRegistration().getClientId())
                    .setProviderId(oAuth2User.getAttribute("sub"))
//                    .setOAuthProvider(oAuth2User.getAttribute("sub"))
                    .setEmail(oAuth2User.getAttribute("email"));

            User persist = repository.save(user);
            return new PrincipalDetails(persist, oAuth2User.getAttributes());
        }
    }
}