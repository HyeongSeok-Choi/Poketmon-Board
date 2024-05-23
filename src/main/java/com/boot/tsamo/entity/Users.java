package com.boot.tsamo.entity;


import com.boot.tsamo.constant.Role;
import com.boot.tsamo.dto.UserFormDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Users implements UserDetails, OAuth2User {
    @Override
    public Map<String, Object> getAttributes() {
        return Map.of();
    }

    @Override
    public String getName() {
        return userId;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> collect = new ArrayList<>();
        collect.add(new GrantedAuthority() {
            private Users user;
            @Override
            public String getAuthority() {
                return user.getRole().toString();
            }
        });
        return null;
    }

    @Override
    public String getUsername() {
        return userId;
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return !this.isEnabled;
    }

    @Id
    @Column(name = "user_id")
    private String userId;

    private String password;

    private String email;

    private String nickName;

    @Enumerated(EnumType.STRING)
    private Role role;

    //일반 유저 생성
    public static Users createUser(UserFormDto userFormDto,
                                   PasswordEncoder passwordEncoder) {
        Users user = new Users();
        user.setUserId(userFormDto.getUserId());
        user.setEmail(userFormDto.getEmail());
        String password = passwordEncoder.encode(userFormDto.getPassword());
        user.setPassword(password);
        user.setNickName(userFormDto.getNickName());
        user.setRole(Role.USER);
        user.setDeleted(false); // 계정 생성 시 삭제되지 않음으로 설정
        user.setEnabled(true); // 계정 생성 시 활성화 상태로 설정
        return user;
    }

    //카카오 계정 db저장
    public Users(String userId, String email) {
        this.email = email;
        this.userId = userId;
        this.role = Role.ADMIN;
        this.isDeleted = false; // 계정 생성 시 삭제되지 않음으로 설정
        this.isEnabled = true; // 계정 생성 시 활성화 상태로 설정


    }

    public void updateEmail(String email) {
        this.email = email;
    }

    public void updateNickName(String nickName) {
        this.nickName = nickName;
    }

    private boolean isDeleted; // 추가된 필드
    private boolean isEnabled; // 추가된 필드

    public void deleteUser() {
        this.isDeleted = true; // 계정 삭제 시 isDeleted 플래그 설정
        this.isEnabled = false; // 계정 삭제 시 비활성화
    }
}



