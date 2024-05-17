package com.boot.tsamo.dto;

import com.boot.tsamo.constant.Role;
import com.boot.tsamo.entity.Users;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

@Getter
@Setter
public class UserFormDto {


    @NotBlank(message = "아이디는 필수 입력 값입니다.")
    private String userId;

    @NotBlank(message = "비밀번호는 필수 입력 값입니다.")
    @Length(min = 5, max = 25, message = "비밀번호는 5자 이상, 25자 이하로 입력해주세요.")
    private String password;

    @NotBlank(message = "이메일은 필수 입력 값입니다.")
    @Email(message = "이메일 형식으로 입력해주세요.")
    private String email;

    @NotBlank(message = "닉네임은 필수 입력 값입니다.")
    private String nickName;


    // Users 엔티티를 UserFormDto로 변환하는 정적 메서드
    public static UserFormDto of(Users user) {
        UserFormDto userFormDto = new UserFormDto();
        userFormDto.setUserId(user.getUserId());
        userFormDto.setPassword(user.getPassword());
        userFormDto.setEmail(user.getEmail());
        userFormDto.setNickName(user.getNickName());
        return userFormDto;

    }
}
