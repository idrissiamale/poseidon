package com.nnk.springboot.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/app")
public class LoginController {
    @GetMapping("/login")
    public String login() {
        return "login_page";
    }

    @GetMapping("/secure/article-details")
    public String getAllUserArticles(Model model) {
        return "redirect:/user/list";
    }

    @GetMapping("/error")
    public String error(Model model) {
        String errorMessage = "You are not authorized for the requested data.";
        model.addAttribute("errorMsg", errorMessage);
        return "403";
    }
}
