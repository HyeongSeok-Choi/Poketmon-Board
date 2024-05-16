package com.boot.tsamo.service;


import com.boot.tsamo.entity.Users;
import com.boot.tsamo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;

    public Users saveUser(Users user) {
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
}

