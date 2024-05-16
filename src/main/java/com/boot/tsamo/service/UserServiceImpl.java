package com.boot.tsamo.service;


import com.boot.tsamo.dto.UserFormDto;
import com.boot.tsamo.entity.Users;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class UserServiceImpl {

    @Override
    public Long updateUser(UserFormeDto userFormeDto){
        Users user = userRepository.findByUserId(UserFormDto.getUserId()).orElseThrow(()-> new UsernameNotFoundException("닉네임이 존재하지 않습니다."));

        user.updateUsername(username
        )
    }
}
