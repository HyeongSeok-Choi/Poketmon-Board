package com.boot.tsamo.service;


import com.boot.tsamo.dto.addUserRequest;
import com.boot.tsamo.entity.Users;
import com.boot.tsamo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;


@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public Users save(addUserRequest dto) {
        Users user = new Users();

        user.builder()
                .userId(dto.getUserId())
                .password(bCryptPasswordEncoder.encode(dto.getPassword()))
                .email(dto.getEmail())
                .nickName(dto.getNickName())
                .role(dto.getRole())
                .build();

        Users saveduser = userRepository.save(user);

        return saveduser;

    }

}
