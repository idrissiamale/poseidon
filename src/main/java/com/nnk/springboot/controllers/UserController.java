package com.nnk.springboot.controllers;

import com.nnk.springboot.domains.User;
import com.nnk.springboot.services.user.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.util.List;

@Controller
public class UserController {
    private static final Logger logger = LogManager.getLogger("UserController");
    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping("/user/list")
    public String home(Model model) {
        List<User> users = userService.findAllUsers();
        model.addAttribute("users", users);
        return "user/list";
    }

    @GetMapping("/user/add")
    public String addUser(User user) {
        return "user/add";
    }

    @PostMapping("/user/validate")
    public String validate(@Valid User user, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "user/add";
        }
        try {
            logger.info("User was saved successfully.");
            userService.save(user);
        } catch (IllegalArgumentException ie) {
            model.addAttribute("error", ie.getMessage());
            logger.error(" Error occurred. Unable to save user.", ie);
            return "user/add";
        }
        model.addAttribute("users", userService.findAllUsers());
        return "redirect:/user/list";
    }

    @GetMapping("/user/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        logger.info("User was successfully fetched.");
        User user = userService.findById(id);
        user.setPassword("");
        model.addAttribute("user", user);
        return "user/update";
    }

    @PostMapping("/user/update/{id}")
    public String updateUser(@PathVariable("id") Integer id, @Valid User user, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "user/update";
        }
        logger.info("User was updated successfully.");
        userService.update(id, user);
        model.addAttribute("users", userService.findAllUsers());
        return "redirect:/user/list";
    }

    @GetMapping("/user/delete/{id}")
    public String deleteUser(@PathVariable("id") Integer id, Model model) {
        logger.info("User was deleted successfully.");
        userService.delete(id);
        model.addAttribute("users", userService.findAllUsers());
        return "redirect:/user/list";
    }
}
