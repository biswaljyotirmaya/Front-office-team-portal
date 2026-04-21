package com.jb.fop.utility;

import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class EmailUtils {

    private final JavaMailSender mailSender;

    public boolean sendEmail(String to, String subject, String body) {
        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();

            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(body, true);

            mailSender.send(mimeMessage);

            log.info("Email sent successfully to {}", to);
            return true;

        } catch (Exception e) {
            log.error("Failed to send email to {}", to, e);
            return false;
        }
    }

    public String buildEmailBody(String name, String pass, String email) {

        return "<div style='font-family:Arial,sans-serif;background:#f4f6f8;padding:30px;'>"
                + "<div style='max-width:600px;margin:auto;background:#ffffff;padding:30px;border-radius:10px;"
                + "box-shadow:0 4px 12px rgba(0,0,0,0.1);'>"
                + "<h2 style='text-align:center;color:#333;margin-bottom:20px;'>Welcome to Our Platform 🎉</h2>"
                + "<p style='font-size:15px;color:#333;'>Hi <b>" + name + "</b>,</p>"
                + "<p style='font-size:14px;color:#555;line-height:1.6;'>"
                + "Your account has been successfully created. To get started, please set your password "
                + "using the temporary password and link below."
                + "</p>"
                + "<div style='background:#eef2ff;padding:14px;border-radius:6px;font-weight:bold;"
                + "color:#2b4eff;text-align:center;margin:20px 0;font-size:15px;'>"
                + "Temporary Password: " + pass
                + "</div>"
                + "<div style='text-align:center;margin:25px 0;'>"
                + "<a href='http://localhost:8080/unlock?email=" + email + "' "
                + "style='display:inline-block;background:#4CAF50;color:#ffffff;text-decoration:none;"
                + "padding:12px 24px;border-radius:6px;font-weight:bold;font-size:14px;'>"
                + "Set Your Password"
                + "</a>"
                + "</div>"
                + "<p style='font-size:13px;color:#555;'>"
                + "For security reasons, we recommend changing your password as soon as possible."
                + "</p>"
                + "<p style='font-size:13px;color:#555;'>"
                + "If you didn’t create this account, you can safely ignore this email."
                + "</p>"
                + "<p style='margin-top:20px;font-size:14px;color:#333;'>"
                + "Thanks,<br><b>Support Team</b>"
                + "</p>"
                + "<hr style='margin:25px 0;border:none;border-top:1px solid #eee;'>"
                + "<p style='font-size:12px;color:#999;text-align:center;'>"
                + "© 2026 Your Company. All rights reserved."
                + "</p>"
                + "</div>"
                + "</div>";
    }
}