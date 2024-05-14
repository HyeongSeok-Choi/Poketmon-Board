package com.boot.tsamo.dto;

import com.boot.tsamo.constant.Role;
import lombok.Data;

@Data
public class addUserRequest {
    private String userId;
    private String email;
    private String password;
    private String nickName;
    private Role role;
}
