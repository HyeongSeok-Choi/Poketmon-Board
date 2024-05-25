package com.boot.tsamo.config.oauth;

import com.boot.tsamo.entity.Users;
import com.boot.tsamo.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.actuate.autoconfigure.observation.ObservationProperties;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
@Data
public class MyOAuth2UserService extends DefaultOAuth2UserService {


    public static boolean kakaoLogin = false;

   private final UserRepository userRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

        System.out.println("여기로 안올수도 있나?");
        //카카오 로그인 온
        kakaoLogin = true;

        OAuth2User oAuth2User = super.loadUser(userRequest);

        //카카오 고유 아이디
        String userId =oAuth2User.getAttributes().get("id").toString();


        //카카오 계정 정보
        Map<String,Object> responseMap = (Map<String,Object>) oAuth2User.getAttributes().get("kakao_account");

        //카카오 전체 속성
        Map<String, Object> attributes = new HashMap<>();

        attributes.put("kakao_id", "kakao_"+userId);

        attributes.putAll(oAuth2User.getAttributes());




        String userEmail ="kakao_"+responseMap.get("email");

        //존재하지 않는다면
        if(!userRepository.findById(userId).isPresent()){

            Users user = new Users("kakao_"+userId, userEmail);


            userRepository.save(user);
        }
        // 기본 권한 설정
        Set<GrantedAuthority> authorities;



        authorities = Collections.singleton(new SimpleGrantedAuthority("ROLE_ADMIN"));


        return  new DefaultOAuth2User(authorities, attributes, "kakao_id");
    }
}
