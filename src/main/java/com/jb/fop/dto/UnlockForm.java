package com.jb.fop.dto;

import lombok.Data;

@Data
public class UnlockForm {
    private String email;
    private String temporaryPassword;
    private String newPassword;
    private String confirmPassword;
}
