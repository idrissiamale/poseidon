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

/**
 * Controller class which handles CRUD operations made by the user on the user management page.
 *
 * @see com.nnk.springboot.services.user.UserService
 */
@Controller
@RequestMapping("/user")
public class UserController {
    private static final Logger logger = LogManager.getLogger("UserController");
    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * It displays the User management page when a GET request to the following URL is made.
     *
     * @param model - it permits to add "users" to the model and to display all the users registered in Poseidon.
     * @return the user/list page.
     */
    @GetMapping("/list")
    public String home(Model model) {
        model.addAttribute("users", userService.findAllUsers());
        return "user/list";
    }

    /**
     * It displays the user/add form when a GET request to the following URL is made.
     *
     * @param user - User entity. Must not be null.
     * @return the user/add page.
     */
    @GetMapping("/add")
    public String addUser(User user) {
        return "user/add";
    }

    /**
     * A method which saves user's registration data into database after the submission is completed and without errors.
     *
     * @param user   - User entity. Must not be null.
     * @param result - permits to handle bind errors and to display it to the user when there are errors on the form fields.
     * @param model  - it permits to add "error" to the model and to display IllegalArgumentException's error message if the exception is thrown.
     * @return it redirects the user to the user/list page if the submission is completed and without errors. Otherwise the user/add form is returned.
     */
    @PostMapping("/validate")
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

    /**
     * It displays the update form when a GET request to the following URL is made.
     *
     * @param id    - it refers to user's id which is used as the path variable.
     * @param model - it permits to define User entity as part of a Model and to display its data into form with the addAttribute method.
     * @return the user/update page.
     */
    @GetMapping("/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        logger.info("User was successfully fetched.");
        User user = userService.findById(id);
        user.setPassword("");
        model.addAttribute("user", user);
        return "user/update";
    }

    /**
     * A method which updates user's registration data after the submission is completed and without errors.
     *
     * @param id-    it refers to user's id which is used as the path variable.
     * @param user   - User entity. Must not be null.
     * @param result - permits to handle bind errors and to display it to the user when there are errors on the form fields.
     * @param model  - it permits to add "users" to the model and to display all the users registered in Poseidon when the user is redirected to user/list page.
     * @return it redirects the user to the user/list page if the submission is completed and without errors. Otherwise the user/update form is returned.
     */
    @PostMapping("/update/{id}")
    public String updateUser(@PathVariable("id") Integer id, @Valid User user, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "user/update";
        }
        logger.info("User was updated successfully.");
        userService.update(id, user);
        model.addAttribute("users", userService.findAllUsers());
        return "redirect:/user/list";
    }

    /**
     * A method which deletes a user when a GET request to the following URL is made.
     *
     * @param id-   it refers to user's id which is used as the path variable.
     * @param model - it permits to add "users" to the model and to display all the users registered in Poseidon
     *              when the user is redirected to user/list page after the delete operation.
     * @return it redirects the user to the user/list page after the delete operation.
     */
    @GetMapping("/delete/{id}")
    public String deleteUser(@PathVariable("id") Integer id, Model model) {
        logger.info("User was deleted successfully.");
        userService.delete(id);
        model.addAttribute("users", userService.findAllUsers());
        return "redirect:/user/list";
    }
}
