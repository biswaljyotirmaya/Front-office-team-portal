package com.jb.fop.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class InquiryController {
    @GetMapping("/dashboard")
    public String dashboard() {
        return "dashboard";
    }

    @GetMapping("/inquiry")
    public String addInquiry() {
        return "addInquiry";
    }

    @GetMapping("/inquiries")
    public String viewInquiries() {
        return "viewInquiries";
    }
}
