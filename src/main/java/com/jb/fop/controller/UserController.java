package com.jb.fop.controller;

import com.jb.fop.dto.SignUpForm;
import com.jb.fop.service.IUserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UserController {
    private final IUserService iUserService;

    public UserController(IUserService iUserService) {
        this.iUserService = iUserService;
    }

    @GetMapping("login")
    public String login() {
        return "login";
    }

    @GetMapping("/signup")
    public String signup(Model model) {
        model.addAttribute("user", new SignUpForm());
        return "signUp";
    }

    @GetMapping("/unlock")
    public String unlock() {
        return "unlock";
    }

    @GetMapping("/forgotpass")
    public String forgotPassword() {
        return "forgotPassword";
    }

    @PostMapping("/signup")
    public String handleSignup(@ModelAttribute("user") SignUpForm signUpForm, Model model) {
        boolean status = iUserService.SignUp(signUpForm);
        if (status) {
            model.addAttribute("success", "Signup Successful, please check your email");
        } else {
            model.addAttribute("error", "something went wrong, please try with different email address");
        }
        model.addAttribute("user", signUpForm);
        return "signUp";
    }
}
