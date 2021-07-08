package com.nnk.springboot.controllers;

import com.nnk.springboot.domains.Rating;
import com.nnk.springboot.services.rating.RatingService;
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
 * Controller class which handles CRUD operations made by the user on the rating pages (rating/add, rating/list and rating/update).
 *
 * @see com.nnk.springboot.services.rating.RatingService
 */
@Controller
@RequestMapping("/rating")
public class RatingController {
    private static final Logger logger = LogManager.getLogger("RatingController");
    private RatingService ratingService;

    @Autowired
    public RatingController(RatingService ratingService) {
        this.ratingService = ratingService;
    }

    /**
     * It displays the rating/list page when a GET request to the following URL is made.
     *
     * @param model - it permits to add "ratings" to the model and to display all the ratings registered in Poseidon.
     * @return the trade/list page.
     */
    @GetMapping("/list")
    public String home(Model model) {
        model.addAttribute("ratings", ratingService.findAllRatings());
        return "rating/list";
    }

    /**
     * It displays the rating/add form when a GET request to the following URL is made.
     *
     * @param rating - Rating entity. Must not be null.
     * @return the rating/add page.
     */
    @GetMapping("/add")
    public String addRatingForm(Rating rating) {
        return "rating/add";
    }

    /**
     * A method which saves rating's data into database after the submission is completed and without errors.
     *
     * @param rating - Rating entity. Must not be null.
     * @param result - permits to handle bind errors and to display it to the user when there are errors on the form fields.
     * @param model  - it permits to add "ratings" to the model and to display all the ratings registered in Poseidon
     *               when the user is redirected to rating/list page.
     * @return it redirects the user to the rating/list page if the submission is completed and without errors. Otherwise the rating/add form is returned.
     */
    @PostMapping("/validate")
    public String validate(@Valid Rating rating, BindingResult result, Model model) {
        if (!result.hasErrors()) {
            logger.info("Rating was saved successfully.");
            ratingService.save(rating);
            model.addAttribute("ratings", ratingService.findAllRatings());
            return "redirect:/rating/list";
        }
        return "rating/add";
    }

    /**
     * It displays the update form when a GET request to the following URL is made.
     *
     * @param id    - it refers to rating's id which is used as the path variable.
     * @param model - it permits to define Rating entity as part of a Model and to display its data into form with the addAttribute method.
     * @return the rating/update page.
     */
    @GetMapping("/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        logger.info("Rating was successfully fetched.");
        Rating rating = ratingService.findById(id);
        model.addAttribute("rating", rating);
        return "rating/update";
    }

    /**
     * A method which updates rating's data after the submission is completed and without errors.
     *
     * @param id     - it refers to rating's id which is used as the path variable.
     * @param rating - Rating entity. Must not be null.
     * @param result - permits to handle bind errors and to display it to the user when there are errors on the form fields.
     * @param model  - it permits to add "ratings" to the model and to display all the ratings registered in Poseidon when the user is redirected to rating/list page.
     * @return it redirects the user to the rating/list page if the submission is completed and without errors. Otherwise the rating/update form is returned.
     */
    @PostMapping("/update/{id}")
    public String updateRating(@PathVariable("id") Integer id, @Valid Rating rating, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "rating/update";
        }
        logger.info("Rating was updated successfully.");
        ratingService.update(id, rating);
        model.addAttribute("ratings", ratingService.findAllRatings());
        return "redirect:/rating/list";
    }

    /**
     * A method which deletes a rating when a GET request to the following URL is made.
     *
     * @param id    - it refers to rating's id which is used as the path variable.
     * @param model - it permits to add "ratings" to the model and to display all the ratings registered in Poseidon
     *              when the user is redirected to rating/list page after the delete operation.
     * @return it redirects the user to the rating/list page after the delete operation.
     */
    @GetMapping("/delete/{id}")
    public String deleteRating(@PathVariable("id") Integer id, Model model) {
        logger.info("Rating was deleted successfully.");
        ratingService.delete(id);
        model.addAttribute("ratings", ratingService.findAllRatings());
        return "redirect:/rating/list";
    }
}
