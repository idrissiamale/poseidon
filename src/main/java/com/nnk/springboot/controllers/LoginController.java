package com.nnk.springboot.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Controller class which returns the login page to html pages that require user's authentication.
 *
 */
@Controller
@RequestMapping("/app")
public class LoginController {
    /**
     * It displays the login page to the user when he/she tries to access pages that require authentication.
     *
     * @return the login page.
     */
    @GetMapping("/login")
    public String login() {
        return "login_page";
    }

    /**
     * It displays the user/list page when a GET request to the following URL is made.
     *
     * @return the user/list page.
     */
    @GetMapping("/secure/article-details")
    public String getAllUserArticles() {
        return "redirect:/user/list";
    }

    /**
     * It displays the 403 page when the access is denied to the user.
     *
     * @param model - it permits to add "errorMsg" to the model and to display our error message to the user when access is denied.
     * @return the 403 page.
     */
    @GetMapping("/error")
    public String error(Model model) {
        String errorMessage = "You are not authorized for the requested data.";
        model.addAttribute("errorMsg", errorMessage);
        return "403";
    }
}
