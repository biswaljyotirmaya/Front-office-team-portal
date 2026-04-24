package com.jb.fop.controller;

import com.jb.fop.dto.LoginForm;
import com.jb.fop.dto.SignUpForm;
import com.jb.fop.dto.UnlockForm;
import com.jb.fop.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class UserController {

    @Autowired
    private IUserService userService;

    @GetMapping("login")
    public String login(Model model) {
        model.addAttribute("login", new LoginForm());
        return "login";
    }
    @PostMapping("/login")
    public String handleLogin(@ModelAttribute("login") LoginForm loginForm, Model model ) {
        String res=userService.Login(loginForm);
        if(!"SUCCESS".equals(res.toUpperCase())){
            model.addAttribute("error",res);
        return "login";
        }
        return "redirect:/dashboard";
    }

    @GetMapping("/signup")
    public String signup(Model model) {
        model.addAttribute("user", new SignUpForm());
        return "signUp";
    }

    @GetMapping("/unlock")
    public String unlock(@RequestParam String email, Model model) {
        model.addAttribute("email", email);
        model.addAttribute("unlockUser", new UnlockForm());
        return "unlock";
    }

    @PostMapping("/unlock")
    public String handleUnlock(@ModelAttribute("unlockUser") UnlockForm unlockForm, @RequestParam String email, Model model) {
        model.addAttribute("email", email);
        model.addAttribute("unlockForm", new UnlockForm());
        System.out.println("unlockForm = " + unlockForm);
        String result =userService.unLockAccount(unlockForm);
        if(result.equals("SUCCESS")){
            model.addAttribute("success","Your account has been unlocked,\nPlease login with your new credentials");
        }else{
            model.addAttribute("error",result);
        }
        return "unlock";
    }

    @PostMapping("/signup")
    public String handleSignup(@ModelAttribute("user") SignUpForm signUpForm, Model model) {
        String status = userService.SignUp(signUpForm);
        if ("SUCCESS".equals(status)) {
            model.addAttribute("success", "Signup Successful, please check your email");
            model.addAttribute("user", signUpForm);
        } else {
            model.addAttribute("error", status);
            model.addAttribute("user", signUpForm);
        }
        return "signUp";
    }
    @GetMapping("/forgotpass")
    public String forgotPassword() {
        return "forgotPassword";
    }
}
