package com.boot.tsamo.entity;


import com.boot.tsamo.constant.Role;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Users {

    @Id
    @Column(name = "user_id")
    private String userId;

    private String password;

    private String email;

    private String nickName;

    @Enumerated(EnumType.STRING)
    private Role role;

}
