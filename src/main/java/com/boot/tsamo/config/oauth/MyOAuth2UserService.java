package com.boot.tsamo.config.oauth;

import com.boot.tsamo.entity.Users;
import com.boot.tsamo.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.Set;

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

        Map<String,Object> responseMap = (Map<String,Object>) oAuth2User.getAttributes().get("kakao_account");

        String userEmail ="kakao_"+responseMap.get("email");

        Users user = new Users(userId,userEmail);

        userRepository.save(user);


        // 기본 권한 설정
        Set<GrantedAuthority> authorities;

            authorities = Collections.singleton(new SimpleGrantedAuthority("ROLE_ADMIN"));

            //new DefaultOAuth2User(authorities, responseMap, userEmail);

        return  new DefaultOAuth2User(authorities, responseMap, userEmail);
    }
}
