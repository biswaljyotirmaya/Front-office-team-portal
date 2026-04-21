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
        String status = iUserService.SignUp(signUpForm);
        if ("SUCCESS".equals(status)) {
            model.addAttribute("success", "Signup Successful, please check your email");
            model.addAttribute("user", signUpForm);
        } else {
            model.addAttribute("error", status);
            model.addAttribute("user", signUpForm);
        }
        return "signUp";
    }
}
