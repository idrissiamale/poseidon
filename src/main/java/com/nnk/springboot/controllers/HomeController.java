package com.nnk.springboot.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Controller class whose role is to return the home and the admin home pages to the user.
 */
@Controller
public class HomeController {
    /**
     * It displays the home page when a GET request to the following URL is made.
     *
     * @return the home html page.
     */
    @GetMapping("/")
    public String home() {
        return "home";
    }

    /**
     * It displays the bidList/list page when a GET request is made to the "/admin/home" URL.
     *
     * @return the bidList/list page.
     */
    @GetMapping("/admin/home")
    public String adminHome() {
        return "redirect:/bidlist/list";
    }
}
