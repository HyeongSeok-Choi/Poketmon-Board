package com.boot.tsamo.controller;

import com.boot.tsamo.dto.UserFormDto;
import com.boot.tsamo.entity.Users;
import com.boot.tsamo.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/users")
@Controller
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    @GetMapping(value = "/new")
    public String userForm(Model model) {
        model.addAttribute("userFormDto", new UserFormDto());
        return "user/userForm";
    }

  /*  @PostMapping(value = "/new")
    public String userForm(UserFormDto userFormDto){
        Users user = Users.createUser(userFormDto, passwordEncoder);
        userService.saveUser(user);

        return "redirect:/";
    }*/

    @PostMapping(value = "/new")
    public String newUser(@Valid UserFormDto userFormDto,
                          BindingResult bindingResult, Model model){
        if(bindingResult.hasErrors()){
            return "user/userForm";
        }
        try{
             Users user = Users.createUser(userFormDto, passwordEncoder);
             userService.saveUser(user);
        }catch(IllegalStateException e){
            model.addAttribute("errorMessage", e.getMessage());
            return "user/userForm";
        }

        return "redirect:/main";
    }
}
