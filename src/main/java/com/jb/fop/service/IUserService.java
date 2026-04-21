package com.jb.fop.service;

import com.jb.fop.dto.LoginForm;
import com.jb.fop.dto.SignUpForm;
import com.jb.fop.dto.UnlockForm;

public interface IUserService {
    public String Login(LoginForm loginForm);

    public String SignUp(SignUpForm signUpForm);

    public boolean unLockAccount(UnlockForm unlockForm);

    public String forgotPassword(String email);
}
