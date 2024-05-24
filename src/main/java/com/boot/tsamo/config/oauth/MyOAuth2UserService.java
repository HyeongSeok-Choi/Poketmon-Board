package com.boot.tsamo.config.oauth;

import com.boot.tsamo.entity.Users;
import com.boot.tsamo.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpSession;
import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.net.http.HttpHeaders;
import java.util.*;

@Service
@RequiredArgsConstructor
@Data
public class MyOAuth2UserService extends DefaultOAuth2UserService {


    public boolean kakaoLogin = false;

   private final UserRepository userRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

        kakaoLogin = true;

        OAuth2User oAuth2User = super.loadUser(userRequest);
        try {
            System.out.println(new ObjectMapper().writeValueAsString(oAuth2User.getAttributes()));

        }catch (Exception e) {
            e.printStackTrace();
        }


        String userId =oAuth2User.getAttributes().get("id").toString();



        Map<String,Object> responseMap = (Map<String,Object>) oAuth2User.getAttributes().get("kakao_account");

        Map<String,Object> responseMap1 = new HashMap<>();
        responseMap1=  oAuth2User.getAttributes();




        System.out.println(responseMap1.get("id")+"뭐라뜨노");



        String userEmail ="kakao_"+responseMap.get("email");

        Users user = new Users(userId,userEmail);

        userRepository.save(user);

        // 기본 권한 설정
        Set<GrantedAuthority> authorities;

            authorities = Collections.singleton(new SimpleGrantedAuthority("ROLE_ADMIN"));

        return  new DefaultOAuth2User(authorities, responseMap1, "id");
    }
}
