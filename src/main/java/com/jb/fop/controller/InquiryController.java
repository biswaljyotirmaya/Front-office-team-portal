package com.jb.fop.controller;

import com.jb.fop.constant.Constants;
import com.jb.fop.dto.DashboardResponse;
import com.jb.fop.dto.InquiryForm;
import com.jb.fop.repository.ICoursesRepo;
import com.jb.fop.repository.IStatusRepo;
import com.jb.fop.service.IInquiryService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class InquiryController {

    private final HttpSession session;

    private final IInquiryService inquiryService;

    private final ICoursesRepo coursesRepo;

    private final IStatusRepo statusRepo;

    @Autowired
    public InquiryController(IInquiryService inquiryService,ICoursesRepo coursesRepo, IStatusRepo statusRepo, HttpSession session) {
        this.inquiryService = inquiryService;
        this.coursesRepo = coursesRepo;
        this.statusRepo = statusRepo;
        this.session = session;
    }

    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        Object userObj = session.getAttribute(Constants.USER_ID);

        if (userObj == null) {
            return "redirect:/login";
        }

        Integer userID = (Integer) userObj;
        model.addAttribute(Constants.USER_ID, userID);
        DashboardResponse res = inquiryService.getDashboard(userID);
        model.addAttribute(Constants.INQUIRY, res);
        return "dashboard";
    }

    @GetMapping("/addinquiry")
    public String addInquiry(Model model) {
        model.addAttribute(Constants.INQUIRY, new InquiryForm());
        model.addAttribute(Constants.USER_ID, session.getAttribute(Constants.USER_ID));
        model.addAttribute(Constants.COURSES, coursesRepo.findAll());
        model.addAttribute(Constants.STATUSES, statusRepo.findAll());
        return "addInquiry";
    }

    @PostMapping("/addinquiry")
    public String handleAddInquiry(@ModelAttribute(Constants.INQUIRY) InquiryForm inquiryForm, RedirectAttributes redirectAttributes) {

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
    public String viewInquiries(@RequestParam(required = false) String course, @RequestParam(required = false) String status, @RequestParam(required = false) String mode, Model model) {
        Integer userID = Integer.parseInt(session.getAttribute(Constants.USER_ID).toString());
        List<InquiryForm> inquiries = inquiryService.getInquiryList(userID, course, status, mode);
        model.addAttribute("inquiries", inquiries);
        model.addAttribute(Constants.COURSES, coursesRepo.findAll());
        model.addAttribute(Constants.STATUSES, statusRepo.findAll());
        System.out.println("inquiries at without ajax: " + inquiries);
        return "viewInquiries";
    }

    @GetMapping("/inquiries/filter")
    @ResponseBody
    public List<InquiryForm> filterInquiries(@RequestParam(required = false) String course, @RequestParam(required = false) String status, @RequestParam(required = false) String mode) {
        Integer userID = Integer.parseInt(session.getAttribute(Constants.USER_ID).toString());
        List<InquiryForm> inquiries = inquiryService.getInquiryList(userID, course, status, mode);
        System.out.println("inquiries at with ajax: " + inquiries);
        return inquiries;
    }

    @GetMapping("/edit/{id}")
    public String editInquiry(@PathVariable Integer id, Model model) {
        InquiryForm form = inquiryService.getInquiryById(id);
        model.addAttribute(Constants.INQUIRY, form);
        model.addAttribute(Constants.COURSES, coursesRepo.findAll());
        model.addAttribute(Constants.STATUSES, statusRepo.findAll());
        return "addInquiry";
    }

    @PostMapping("/edit/{id}")
    public String handleEditInquiry(@PathVariable Integer id, @ModelAttribute(Constants.INQUIRY) InquiryForm form, RedirectAttributes redirectAttributes) {

        System.out.println("EDIT CALLED for ID: " + id);

        String status = inquiryService.editInquiry(id, form);

        if (status.startsWith("SUCCESS")) {
            redirectAttributes.addFlashAttribute("success", "Inquiry updated successfully");
        } else {
            redirectAttributes.addFlashAttribute("error", status);
        }

        return "redirect:/inquiries";
    }

    @GetMapping("/delete/{id}")
    public String deleteInquiry(@PathVariable Integer id, RedirectAttributes redirectAttributes) {
        String msg = inquiryService.deleteInquiry(id);
        redirectAttributes.addFlashAttribute("msg", msg);
        return "redirect:/inquiries";
    }
}
