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

import java.util.Collection;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Users implements UserDetails {

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    @Override
    public String getUsername() {
        return "";
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
        return false;
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
        return user;
    }

    //카카오 계정 db저장
    public Users(String userId, String email){
        this.email = email;
        this.userId = userId;
        this.role = Role.ADMIN;
        this.password="chltest";

    }

}



