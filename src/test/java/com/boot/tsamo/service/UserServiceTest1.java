package com.boot.tsamo.service;

import com.boot.tsamo.constant.Role;
import com.boot.tsamo.entity.Users;
import com.boot.tsamo.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest1 {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    private Users testUser;

    @BeforeEach
    void setUp() {
        testUser = new Users();
        testUser.setUserId("testUser");
        testUser.setPassword("testPassword");
        testUser.setEmail("test@example.com");
        testUser.setNickName("Test User");
        testUser.setRole(Role.USER);
    }

    @Test
    @DisplayName("사용자 저장 - 성공")
    void saveUser_Success() {
        when(userRepository.findByUserIdAndIsDeletedFalse(anyString())).thenReturn(null);
        when(userRepository.save(any(Users.class))).thenReturn(testUser);

        Users savedUser = userService.saveUser(testUser);

        assertNotNull(savedUser);
        assertEquals(testUser.getUserId(), savedUser.getUserId());
        verify(userRepository, times(1)).save(testUser);
    }

    @Test
    @DisplayName("사용자 저장 - 실패 (이미 가입된 사용자)")
    void saveUser_Failure_AlreadyExists() {
        when(userRepository.findByUserIdAndIsDeletedFalse(anyString())).thenReturn(testUser);

        assertThrows(UsernameNotFoundException.class, () -> userService.saveUser(testUser));

        verify(userRepository, never()).save(any());
    }

    @Test
    @DisplayName("사용자 조회 - 성공")
    void loadUserByUsername_Success() {
        when(userRepository.findByUserIdAndIsDeletedFalse(anyString())).thenReturn(testUser);

        UserDetails userDetails = userService.loadUserByUsername(testUser.getUserId());

        assertNotNull(userDetails);
        assertEquals(testUser.getUserId(), userDetails.getUsername());
        assertEquals(testUser.getPassword(), userDetails.getPassword());
        verify(userRepository, times(1)).findByUserIdAndIsDeletedFalse(testUser.getUserId());
    }

    @Test
    @DisplayName("사용자 조회 - 실패 (사용자가 존재하지 않음)")
    void loadUserByUsername_Failure_UserNotFound() {
        when(userRepository.findByUserIdAndIsDeletedFalse(anyString())).thenReturn(null);

        assertThrows(UsernameNotFoundException.class, () -> userService.loadUserByUsername(testUser.getUserId()));

        verify(userRepository, times(1)).findByUserIdAndIsDeletedFalse(testUser.getUserId());
    }

    // 나머지 테스트 케이스도 작성해주세요.
}
