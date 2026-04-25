package com.jb.fop.controller;

import com.jb.fop.dto.DashboardResponse;
import com.jb.fop.dto.InquiryForm;
import com.jb.fop.dto.InquirySearchCriteria;
import com.jb.fop.entity.InquiryDetails;
import com.jb.fop.repository.ICoursesRepo;
import com.jb.fop.repository.IStatusRepo;
import com.jb.fop.service.IInquiryService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class InquiryController {

    @Autowired
    private HttpSession session;

    @Autowired
    private IInquiryService inquiryService;

    @Autowired
    private ICoursesRepo coursesRepo;

    @Autowired
    private IStatusRepo statusRepo;

    @Autowired
    private IInquiryService iInquiryService;

    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        Integer userID = Integer.parseInt(session.getAttribute("userID").toString());
        if (userID == null) {
            return "redirect:/login";
        }
        model.addAttribute("userId", userID);
        DashboardResponse res = inquiryService.getDashboard(userID);
        model.addAttribute("inquiry", res);
        return "dashboard";
    }

    @GetMapping("/addinquiry")
    public String addInquiry(Model model) {
        model.addAttribute("inquiry", new InquiryForm());
        model.addAttribute("userId", session.getAttribute("userID"));
        model.addAttribute("courses", coursesRepo.findAll());
        model.addAttribute("statuses", statusRepo.findAll());
        return "addInquiry";
    }

    @PostMapping("/addinquiry")
    public String handleAddInquiry(@ModelAttribute("inquiry") InquiryForm inquiryForm, RedirectAttributes redirectAttributes) {

        String res = inquiryService.addInquiry(inquiryForm);

        if (res.startsWith("ERROR:")) {
            redirectAttributes.addFlashAttribute("error", res.substring(6));
            return "redirect:/addinquiry"; // stay on form page
        } else {
            redirectAttributes.addFlashAttribute("success", res.substring(8));
            return "redirect:/dashboard";
        }
    }

    @GetMapping("/inquiries")
    public String viewInquiries( @RequestParam(required = false) String course,
                                 @RequestParam(required = false) String status,
                                 @RequestParam(required = false) String mode, Model model) {
        Integer userID = Integer.parseInt(session.getAttribute("userID").toString());
        List<InquiryForm> inquiries = inquiryService.getInquiryList(userID, course, status, mode);
        model.addAttribute("inquiries", inquiries);
        model.addAttribute("courses", coursesRepo.findAll());
        model.addAttribute("statuses", statusRepo.findAll());
        return "viewInquiries";
    }
}
