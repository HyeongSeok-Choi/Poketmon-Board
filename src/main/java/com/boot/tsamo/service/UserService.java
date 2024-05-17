package com.boot.tsamo.service;


import com.boot.tsamo.dto.UserFormDto;
import com.boot.tsamo.entity.Users;
import com.boot.tsamo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import jakarta.persistence.EntityNotFoundException;


@Service
@Transactional
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;


    public Users saveUser(Users user) {

        System.out.println(user.getUserId()+"dsjfak");
        System.out.println(user.getPassword()+"dsjfak");
        System.out.println(user.getRole()+"dsjfak");
        System.out.println(user.getEmail()+"dsjfak");
        System.out.println(user.getNickName()+"dsjfak");
        validateDuplicateUser(user);
        return userRepository.save(user);
    }

    private void validateDuplicateUser(Users user) {
        Users findUser = userRepository.findByUserIdContaining(user.getUserId());
        if (findUser != null) {
            throw new UsernameNotFoundException("이미 가입된 회원입니다.");
        }
    }

    @Override
    public UserDetails loadUserByUsername(String userId) throws
            UsernameNotFoundException {
        Users user = userRepository.findByUserIdContaining(userId);

        if (user == null) {
            throw new UsernameNotFoundException(userId);
        }

        return User.builder()
                .username(user.getUserId())
                .password(user.getPassword())
                .roles(user.getRole().toString())
                .build();
    }

    @Transactional(readOnly = true)
    public UserFormDto getUserDetails(String userId) {
        // userId를 기반으로 사용자 정보를 조회합니다.

        Users user = userRepository.findById(userId).orElseThrow(EntityNotFoundException::new);

        // 사용자 정보를 UserFormDto로 변환합니다.
        UserFormDto userFormDto = UserFormDto.of(user);

        // 변환된 UserFormDto를 반환합니다.
        return userFormDto;
    }

    public String updateUser(UserFormDto userFormDto) {
        Users user = userRepository.findById(userFormDto.getUserId()).orElseThrow(EntityNotFoundException::new);
        user.updateNickName(userFormDto.getNickName());
        user.updatePassword(userFormDto.getPassword());

        // 회원 비밀번호 수정을 위한 패스워드 암호화
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String encodePw = encoder.encode(userFormDto.getPassword());
        user.updatePassword(encodePw);

        userRepository.save(user);

        return user.getUserId();
    }

}

