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
    public String SignUp(SignUpForm signUpForm) {

        UserDetails userData = userDetailsRepo.findByEmail(signUpForm.getEmail());

        if (userData != null) {
            return "Email Already Exists";
        }
        try {
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
            String body = emailUtils.buildEmailBody(name, pass, sendTo);
            emailUtils.sendEmail(sendTo, subject, body);
            return "SUCCESS";
        } catch (Exception e) {
            e.printStackTrace();
            return "Something went wrong while processing your request";
        }
    }

    @Override
    public String unLockAccount(UnlockForm unlockForm) {
        try{
            System.out.println("unlockForm at service= " + unlockForm);
            UserDetails userDetails = userDetailsRepo.findByEmail(unlockForm.getEmail());
            if (userDetails == null) {
                return "Email Doesn't Exists";
            }
            if(!unlockForm.getNewPassword().equals(unlockForm.getConfirmPassword())) {
                return "Password Doesn't Match";
            }
            if(!unlockForm.getTemporaryPassword().equals(userDetails.getPassword())){
                return "Please check your temporary password again from your email";
            }
            if(userDetails.getPassword().equals(unlockForm.getNewPassword())) {
                userDetails.setPassword(unlockForm.getNewPassword());
                userDetails.setAccountStatus("UNLOCKED");
                userDetailsRepo.save(userDetails);
            }
            return "SUCCESS";
        }catch (Exception e){
            e.printStackTrace();
            return "Something went wrong while processing your request";
        }
    }

    @Override
    public String forgotPassword(String email) {
        return "";
    }
}
