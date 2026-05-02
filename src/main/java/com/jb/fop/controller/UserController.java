package com.jb.fop.controller;

import com.jb.fop.constant.Constants;
import com.jb.fop.dto.LoginForm;
import com.jb.fop.dto.SignUpForm;
import com.jb.fop.dto.UnlockForm;
import com.jb.fop.service.IUserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class UserController {

    private final IUserService userService;

    private final HttpSession session;

    @Autowired
    public UserController(IUserService userService, HttpSession session) {
        this.userService = userService;
        this.session = session;
    }

    @GetMapping("login")
    public String login(Model model) {
        model.addAttribute("login", new LoginForm());
        return "login";
    }

    @PostMapping("/login")
    public String handleLogin(@ModelAttribute("login") LoginForm loginForm, RedirectAttributes redirectAttributes) {
        String res = userService.Login(loginForm);
        if (!Constants.CONST_SUCCESS.equalsIgnoreCase(res)) {
            redirectAttributes.addFlashAttribute(Constants.ERROR, res);
            return "redirect:/login";
        } else {
            redirectAttributes.addFlashAttribute(Constants.SUCCESS, "Login Success, Welcome back!");
        }
        session.setAttribute("userID", session.getAttribute("userId"));
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
        String result = userService.unLockAccount(unlockForm);
        if (result.equals(Constants.CONST_SUCCESS)) {
            model.addAttribute(Constants.SUCCESS, "Your account has been unlocked,\nPlease login with your new credentials");
        } else {
            model.addAttribute(Constants.ERROR, result);
        }
        return "unlock";
    }

    @PostMapping("/signup")
    public String handleSignup(@ModelAttribute("user") SignUpForm signUpForm, Model model) {
        String status = userService.SignUp(signUpForm);
        if (Constants.CONST_SUCCESS.equals(status)) {
            model.addAttribute(Constants.SUCCESS, "Signup Successful, please check your email");
            model.addAttribute("user", signUpForm);
        } else {
            model.addAttribute(Constants.ERROR, status);
            model.addAttribute("user", signUpForm);
        }
        return "signUp";
    }

    @GetMapping("/forgotpass")
    public String forgotPassword() {
        return "forgotPassword";
    }

    @PostMapping("/forgotpass")
    public String handleForgotPassword(@RequestParam("email") String email, RedirectAttributes redirectAttributes) {
        String msg = userService.forgotPassword(email);
        if (msg.startsWith("ERROR:")) {
            redirectAttributes.addFlashAttribute(Constants.ERROR, msg.substring(6));
        } else {
            redirectAttributes.addFlashAttribute(Constants.SUCCESS, "Password sent successfully. Please check your email and log in.");
            return "redirect:/login";
        }
        return "redirect:/forgotpass";
    }

    @GetMapping("logout")
    public String logout(RedirectAttributes redirectAttributes) {
        session.invalidate();
        redirectAttributes.addFlashAttribute(Constants.SUCCESS, "Logout Successful");
        return "redirect:/";
    }
}
