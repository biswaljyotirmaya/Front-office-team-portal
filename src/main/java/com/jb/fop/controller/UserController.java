package com.jb.fop.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserController {
    @GetMapping("login")
    public String login() {
        return "login";
    }

    @GetMapping("/register")
    public String register() {
        return "register";
    }

    @GetMapping("/signup")
    public String signup() {
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
}
