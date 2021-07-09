package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.services.bidList.BidListService;
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
 * Controller class which handles CRUD operations made by the user on the bidList pages (bidList/add, bidList/list and bidList/update).
 *
 * @see com.nnk.springboot.services.bidList.BidListService
 */
@Controller
@RequestMapping("/bidlist")
public class BidListController {
    private static final Logger logger = LogManager.getLogger("BidListController");
    private BidListService bidListService;

    @Autowired
    public BidListController(BidListService bidListService) {
        this.bidListService = bidListService;
    }

    /**
     * It displays the bidList/list page when a GET request to the following URL is made.
     *
     * @param model - it permits to add "bids" to the model and to display all the bids registered in Poseidon.
     * @return the bidList/list page.
     */
    @GetMapping("/list")
    public String home(Model model) {
        model.addAttribute("bids", bidListService.findAll());
        return "bidList/list";
    }

    /**
     * It displays the bidList/add form when a GET request to the following URL is made.
     *
     * @param bidList - BidList entity. Must not be null.
     * @return the bidList/add page.
     */
    @GetMapping("/add")
    public String addBidForm(BidList bidList) {
        return "bidList/add";
    }

    /**
     * A method which saves bidList's data into database after the submission is completed and without errors.
     *
     * @param bid    - BidList entity. Must not be null.
     * @param result - permits to handle bind errors and to display it to the user when there are errors on the form fields.
     * @param model  - it permits to add "bids" to the model and to display all the bids registered in Poseidon
     *               when the user is redirected to bidList/list page.
     * @return it redirects the user to the bidList/list page if the submission is completed and without errors. Otherwise the bidList/add form is returned.
     */
    @PostMapping("/validate")
    public String validate(@Valid BidList bid, BindingResult result, Model model) {
        if (!result.hasErrors()) {
            logger.info("BidList was saved successfully.");
            bidListService.save(bid);
            model.addAttribute("bids", bidListService.findAll());
            return "redirect:/bidlist/list";
        }
        return "bidList/add";
    }

    /**
     * It displays the update form when a GET request to the following URL is made.
     *
     * @param id    - it refers to bidList's id which is used as the path variable.
     * @param model - it permits to define BidList entity as part of a Model and to display its data into form with the addAttribute method.
     * @return the bidList/update page.
     */
    @GetMapping("/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        logger.info("BidList was successfully fetched.");
        BidList bid = bidListService.findById(id);
        model.addAttribute("bidList", bid);
        return "bidList/update";
    }

    /**
     * A method which updates bidList's data after the submission is completed and without errors.
     *
     * @param id     - it refers to bidList's id which is used as the path variable.
     * @param bid    - BidList entity. Must not be null.
     * @param result - permits to handle bind errors and to display it to the user when there are errors on the form fields.
     * @param model  - it permits to add "bids" to the model and to display all the bids registered in Poseidon when the user is redirected to bidList/list page.
     * @return it redirects the user to the bidList/list page if the submission is completed and without errors. Otherwise the bidList/update form is returned.
     */
    @PostMapping("/update/{id}")
    public String updateBid(@PathVariable("id") Integer id, @Valid BidList bid, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "bidList/update";
        }
        logger.info("BidList was updated successfully.");
        bidListService.update(id, bid);
        model.addAttribute("bids", bidListService.findAll());
        return "redirect:/bidlist/list";
    }

    /**
     * A method which deletes a bid when a GET request to the following URL is made.
     *
     * @param id    - it refers to bidList's id which is used as the path variable.
     * @param model - it permits to add "bids" to the model and to display all the bids registered in Poseidon
     *              when the user is redirected to bidList/list page after the delete operation.
     * @return it redirects the user to the bidList/list page after the delete operation.
     */
    @GetMapping("/delete/{id}")
    public String deleteBid(@PathVariable("id") Integer id, Model model) {
        logger.info("BidList was deleted successfully.");
        bidListService.delete(id);
        model.addAttribute("bids", bidListService.findAll());
        return "redirect:/bidlist/list";
    }
}
