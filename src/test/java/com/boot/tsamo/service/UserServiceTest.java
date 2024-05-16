package com.boot.tsamo.service;

import com.boot.tsamo.dto.UserFormDto;
import com.boot.tsamo.entity.Users;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.TestPropertySource;
import static org.junit.jupiter.api.Assertions.assertThrows;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@TestPropertySource(locations="classpath:application-test.properties")
class UserServiceTest {

    @Autowired
    UserService userService;

    @Autowired
    PasswordEncoder passwordEncoder;

    public Users createUser(){
        UserFormDto userFormDto = new UserFormDto();
        userFormDto.setUserId("test02");
        userFormDto.setPassword(passwordEncoder.encode("12341234"));
        userFormDto.setEmail("test02@gmail.com");
        userFormDto.setNickName("test02");
        return Users.createUser(userFormDto, passwordEncoder);
    }

    @Test
    @DisplayName("회원가입 테스트")
    public void saveUserTest(){
        Users user = createUser();
        Users savedUser = userService.saveUser(user);

        assertEquals(user.getUserId(), savedUser.getUserId());
        assertEquals(user.getEmail(), savedUser.getEmail());
        assertEquals(user.getNickName(), savedUser.getNickName());
        assertEquals(user.getPassword(), savedUser.getPassword());
        assertEquals(user.getRole(), savedUser.getRole());
    }

    @Test
    @DisplayName("중복 회원 가입 테스트")
    public void saveDupliateUserTest(){
        Users user1 = createUser();
        Users user2 = createUser();
        userService.saveUser(user1);

        Throwable e = assertThrows(UsernameNotFoundException.class, () -> {
            userService.saveUser(user2);});
        assertEquals("이미 가입된 회원입니다.", e.getMessage());
    }

}