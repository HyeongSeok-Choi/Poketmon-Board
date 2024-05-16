package com.boot.tsamo.controller;

import com.boot.tsamo.dto.UserFormDto;
import com.boot.tsamo.entity.Users;
import com.boot.tsamo.service.UserService;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;


@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@TestPropertySource(locations="classpath:application-test.properties")
class UserControllerTest {

    @Autowired
    private UserService userService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public Users createUser(String userId, String password) {
        UserFormDto userFormDto = new UserFormDto();
        userFormDto.setUserId(userId);
        userFormDto.setPassword(password);
        userFormDto.setNickName("홍길동");
        userFormDto.setEmail("test@email.com");
        Users user = Users.createUser(userFormDto, passwordEncoder);
        return userService.saveUser(user);
    }
    @Test
    @DisplayName("로그인 성공 테스트")
    public void loginSuccessTest() throws Exception {
        String userId = "김길동";
        String password = "12341234";
        this.createUser(userId, password);

        mockMvc.perform(formLogin().userParameter("userId")
                        .loginProcessingUrl("/user/login")
                        .user(userId).password(password))
                .andExpect(SecurityMockMvcResultMatchers.authenticated());
    }

    @Test
    @DisplayName("로그인 실패 테스트")
    public void loginFailTest() throws Exception{
        String userId = "김길동";
        String password = "12341234";
        this.createUser(userId, password);
        mockMvc.perform(formLogin().userParameter("userId")
                        .loginProcessingUrl("/user/login")
                        .user(userId).password("12345"))
                .andExpect(SecurityMockMvcResultMatchers.unauthenticated());
    }

}