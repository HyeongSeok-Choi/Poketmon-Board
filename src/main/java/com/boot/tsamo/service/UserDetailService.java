package com.boot.tsamo.service;

import com.boot.tsamo.entity.Users;
import com.boot.tsamo.repository.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;


@RequiredArgsConstructor
@Service
public class UserDetailService implements UserDetailsService {

    private final UsersRepository userRepository;

    @Override
    public Users loadUserByUsername(String userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException((userId)));
    }
}
