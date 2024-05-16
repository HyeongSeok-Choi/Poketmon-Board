package com.boot.tsamo.controller;

import com.boot.tsamo.dto.UserFormDto;
import com.boot.tsamo.entity.Users;
import com.boot.tsamo.repository.UserRepository;
import com.boot.tsamo.service.UserService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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
        return "/user/userForm";
    }

    @PostMapping(value = "/new")
    public String newUser(@Valid UserFormDto userFormDto,
                          BindingResult bindingResult, Model model){
        if(bindingResult.hasErrors()){
            return "/user/userForm";
        }
        try{
             Users user = Users.createUser(userFormDto, passwordEncoder);
             userService.saveUser(user);
        }catch(UsernameNotFoundException e){
            model.addAttribute("errorMessage", e.getMessage());
            System.out.println("2222222 : "  + e.getMessage());
            return "/user/userForm";
        }

        return "redirect:/user/login";
    }

    @GetMapping(value = "/login")
    public String loginUser(){
        return "/user/userLoginForm";
    }

    @GetMapping(value = "/login/error")
    public String loginError(Model model){
        model.addAttribute("loginErrorMsg", "아이디 또는 비밀번호를 확인해주세요");
        return "/user/userLoginForm";
    }

 /*   @GetMapping("/update")
    public String updateUsername(@Valid MemberUsernameUpdateDTO memberUsernameUpdateDTO, Errors errors, Model model, Authentication authentication) {
        if (errors.hasErrors()) {
            model.addAttribute("member", memberUsernameUpdateDTO);
            globalService.messageHandling(errors, model);
            return "/user/updateUsername";
        }

        memberService.updateMemberUsername(memberUsernameUpdateDTO);

        return "redirect:/user/userInfo";
    }*/

}
