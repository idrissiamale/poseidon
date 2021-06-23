package com.nnk.springboot.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.security.RolesAllowed;

@Controller
public class HomeController {
    @RequestMapping("/")
    public String home(Model model) {
        return "home";
    }


    @RequestMapping("/admin/home")
    @RolesAllowed("ADMIN")
    public String adminHome(Model model) {
        return "redirect:/bidList/list";
    }


}
