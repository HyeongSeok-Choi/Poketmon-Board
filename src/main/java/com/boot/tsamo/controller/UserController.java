package com.boot.tsamo.controller;

import com.boot.tsamo.config.oauth.MyOAuth2UserService;
import com.boot.tsamo.dto.UserFormDto;
import com.boot.tsamo.entity.Users;
import com.boot.tsamo.repository.UserRepository;
import com.boot.tsamo.service.UserService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


import java.security.Principal;

@RequestMapping("/user")
@Controller
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    @GetMapping(value = "/new")
    public String userForm(Model model) {
        model.addAttribute("userFormDto", new UserFormDto());
        return "user/userForm";
    }

    @PostMapping(value = "/new")
    public String newUser(@Valid UserFormDto userFormDto,
                          BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "user/userForm";
        }
        if (!userFormDto.getPassword().equals(userFormDto.getCheckPassword())) {
            bindingResult.rejectValue("checkPassword", "passwordInCorrect",
                    "2개의 패스워드가 일치하지 않습니다.");
            return "user/userForm";
        }
        try {
            Users user = Users.createUser(userFormDto, passwordEncoder);
            userService.saveUser(user);
        } catch (UsernameNotFoundException e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "user/userForm";
        }

        return "redirect:/user/login";

    }

/*    @PostMapping(value = "/new")
    public String userIdcheck(@Valid UserFormDto userFormDto,
                          BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "/user/userForm";
        }
        try {
            Users user = Users.createUser(userFormDto, passwordEncoder);
            userService.saveUser(user);
        } catch (UsernameNotFoundException e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "/user/userForm";
        }
        return "/user/userForm";
    }*/

    @GetMapping(value = "/login")
    public String loginUser() {
        return "user/userLoginForm";
    }

    @GetMapping(value = "/login/error")
    public String loginError(Model model) {
        model.addAttribute("loginErrorMsg", "아이디 또는 비밀번호를 확인해주세요");
        return "user/userLoginForm";
    }

    @GetMapping("/loginInfo")
    public String updateUserInfo(Model model, Principal principal) {
        try {
            // userService.getUserDetails(userId)를 호출합니다.
            UserFormDto userFormDto = userService.getUserDetails(principal.getName());

            // 모델에 사용자 정보를 추가합니다.
            model.addAttribute("user", userFormDto);
        } catch (EntityNotFoundException e) {
            // 사용자가 존재하지 않을 때 처리합니다.
            model.addAttribute("errorMessage", "존재하지 않는 아이디입니다.");
            model.addAttribute("userFormDto", new UserFormDto());
            return "user/userLoginInfo";
        }
        // 수정 페이지를 반환합니다.
        return "user/userLoginInfo";
    }

    @PostMapping(value = "/loginInfo")
    public String updateUserInfo(UserFormDto userFormDto, RedirectAttributes redirectAttributes) {
        userService.updateUser(userFormDto);
        redirectAttributes.addFlashAttribute("successMessage", "수정되었습니다.");
        return "redirect:/user/loginInfo";
    }

    @PostMapping("/delete")
    public String deleteUser(@RequestParam("id") String userId, RedirectAttributes redirectAttributes, HttpSession session) {
        try {
            userService.deleteUser(userId);

            session.invalidate();

            redirectAttributes.addAttribute("successMessage", "탈퇴 완료 되었습니다.");
            return "redirect:/";
        } catch (EntityNotFoundException e) {
            redirectAttributes.addAttribute("errorMessage", "존재하지 않는 아이디입니다.");
        }
        return "redirect:/";
    }




    @GetMapping(value = "/auth/kakao/callback")
    public String kakaoCallback() {

        return "createBoard";
    }

    @GetMapping(value = "/kakao/logout")
    public String logout(HttpSession session) {

        session.invalidate();
        MyOAuth2UserService.kakaoLogin= false;

        return "redirect:/";
    }

    @GetMapping(value = "/logout/1")
    public String NormalLogout(HttpSession session) {

        MyOAuth2UserService.kakaoLogin = false;

        session.invalidate();

        return "redirect:/";

    }

}
