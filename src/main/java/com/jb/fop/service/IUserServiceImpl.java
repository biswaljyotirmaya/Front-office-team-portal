package com.jb.fop.service;

import com.jb.fop.dto.LoginForm;
import com.jb.fop.dto.SignUpForm;
import com.jb.fop.dto.UnlockForm;
import com.jb.fop.entity.UserDetails;
import com.jb.fop.repository.IUserDetailsRepo;
import com.jb.fop.utility.EmailUtils;
import com.jb.fop.utility.PasswordUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class IUserServiceImpl implements IUserService {

    @Autowired
    private PasswordUtils passwordUtils;

    @Autowired
    private IUserDetailsRepo userDetailsRepo;

    @Autowired
    private EmailUtils emailUtils;

    @Override
    public String Login(LoginForm loginForm) {
        return "";
    }

    @Override
    public boolean SignUp(SignUpForm signUpForm) {

        UserDetails userData = userDetailsRepo.findByEmail(signUpForm.getEmail());

        if (userData != null) {
            return false;
        }

        UserDetails userDetails = new UserDetails();
//        1. Copy data from binding object to entity object
        BeanUtils.copyProperties(signUpForm, userDetails);
//        2. Generate random pass and set to object
        String pass = passwordUtils.generatePassword();
        userDetails.setPassword(pass);
//        3. set account status as locked
        userDetails.setAccountStatus("LOCKED");
//        4. Insert record
        userDetailsRepo.save(userDetails);
//        5. send email to unlock the account
        String sendTo = signUpForm.getEmail();
        String name = signUpForm.getUserName();
        String subject = "Welcome! Set Your Password";
        String body = "<div style='font-family:Arial,sans-serif;max-width:600px;margin:auto;padding:20px;border:1px solid #eee;border-radius:10px;background:#ffffff;'>"

                + "<h2 style='text-align:center;color:#333;'>Welcome to Our Platform 🎉</h2>"

                + "<p>Hi <b>" + name + "</b>,</p>"

                + "<p>Your account has been successfully created. To get started, please set your password using the temporary password and link below.</p>"

                + "<div style='background:#eef2ff;padding:12px;border-radius:6px;font-weight:bold;color:#2b4eff;text-align:center;letter-spacing:1px;margin:20px 0;'>" + "Temporary Password: " + pass + "</div>"

                + "<div style='text-align:center;'>" + "<a href='http://localhost:8080/unlock?email=" + sendTo + "' style='display:inline-block;background:#4CAF50;color:#ffffff;text-decoration:none;padding:12px 20px;border-radius:6px;font-weight:bold;'>" + "Set Your Password" + "</a>" + "</div>" + "<p style='margin-top:20px;'>For security reasons, we recommend changing your password as soon as possible.</p>"

                + "<p>If you didn’t create this account, please ignore this email.</p>"

                + "<p style='margin-top:20px;'>Welcome aboard,<br><b>Your Support Team</b></p>"

                + "<p style='font-size:12px;color:#777;text-align:center;margin-top:30px;'>© 2026 Your Company</p>"

                + "</div>";

        emailUtils.sendEmail(sendTo, subject, body);
        return true;
    }

    @Override
    public boolean unLockAccount(UnlockForm unlockForm) {
        return false;
    }

    @Override
    public String forgotPassword(String email) {
        return "";
    }
}
