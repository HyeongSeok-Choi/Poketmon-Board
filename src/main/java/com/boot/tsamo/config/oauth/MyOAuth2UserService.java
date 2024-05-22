package com.boot.tsamo.config.oauth;

import com.boot.tsamo.entity.Users;
import com.boot.tsamo.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class MyOAuth2UserService extends DefaultOAuth2UserService {

   private final UserRepository userRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);
        try {
            System.out.println(new ObjectMapper().writeValueAsString(oAuth2User.getAttributes()));

        }catch (Exception e) {
            e.printStackTrace();
        }


        String userId ="kakao_"+ oAuth2User.getAttributes().get("id");

        Map<String,String> responseMap = (Map<String,String>) oAuth2User.getAttributes().get("kakao_account");

        String userEmail ="kakao_"+responseMap.get("email");

        Users user = new Users(userId,userEmail);

        userRepository.save(user);

        return user;
    }
}
