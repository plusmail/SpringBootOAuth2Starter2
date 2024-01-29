package com.yi.service;

import com.yi.entity.OauthProvider;
import com.yi.entity.User;
import com.yi.exception.InsufficientBalanceException;
import com.yi.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@Slf4j
@Transactional
@AllArgsConstructor
public class UserServiceImpl extends DefaultOAuth2UserService {

    private UserRepository userRepository;


    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

        Map<String, Object> attributes = super.loadUser(userRequest).getAttributes();
        log.info("ATTR INFO : {}", attributes.toString());

        String email = null;
        OauthProvider oauthType = OauthProvider.valueOf(userRequest.getClientRegistration().getRegistrationId());

        OAuth2User user2 = super.loadUser(userRequest);

        if("kakao".equalsIgnoreCase(String.valueOf(oauthType))) {
            email = ((Map<String, Object>) attributes.get("kakao_account")).get("email").toString();
        }
        else if("google".equalsIgnoreCase(String.valueOf(oauthType))) {
            email = attributes.get("email").toString();
        }
        else if("naver".equalsIgnoreCase(String.valueOf(oauthType))) {
            email = ((Map<String, Object>) attributes.get("response")).get("email").toString();
        }


        if(userRepository.findByEmailAndOauthProvider(email, oauthType).isEmpty()) {
            log.info("{}({}) NOT EXISTS. REGISTER", email, oauthType);
            User user = new User();
            user.setEmail(email);
            user.setOauthProvider(OauthProvider.valueOf(String.valueOf(oauthType)));
            userRepository.save(user);
        }

        return super.loadUser(userRequest);
    }

    ////////////////////
    public User createUser(User user) {
        return userRepository.save(user);
    }

    public Optional<User> getUserById(Long userId) {
        return userRepository.findById(Math.toIntExact(userId));
    }
    public Optional<User> getUserByEmailAndOAuthType(String email, OauthProvider oauthProvider) {
        return userRepository.findByEmailAndOauthProvider(email, oauthProvider);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Transactional(rollbackOn = {InsufficientBalanceException.class, Exception.class})
    public User updateUser(User user) throws InsufficientBalanceException {
        User existingUser = userRepository.findById(Math.toIntExact(user.getId())).get();
        existingUser.setUsername(user.getUsername());
        existingUser.setEmail(user.getEmail());
        User updatedUser = userRepository.save(existingUser);
        if(updatedUser.getId() == 1){
            throw new InsufficientBalanceException("잔액이 부족합니다.");
//            throw new RuntimeException("RuntimeException (Unchecked Exception)");
        }
        return updatedUser;
    }

    public void deleteUser(Long userId) {
        userRepository.deleteById(Math.toIntExact(userId));
    }
}