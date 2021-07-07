package com.nnk.springboot.controllers;

import com.nnk.springboot.domains.Trade;
import com.nnk.springboot.services.bidList.BidListService;
import com.nnk.springboot.services.trade.TradeService;
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
 * Controller class which handles CRUD operations made by the user on the trade pages (trade/add, trade/list and trade/update).
 *
 * @see com.nnk.springboot.services.trade.TradeService
 */
@Controller
public class TradeController {
    private static final Logger logger = LogManager.getLogger("TradeController");
    private TradeService tradeService;

    @Autowired
    public TradeController(TradeService tradeService) {
        this.tradeService = tradeService;
    }

    /**
     * It displays the trade/list page when a GET request to the following URL is made.
     *
     * @param model - it permits to add "tradeList" to the model and to display all the trades registered in Poseidon.
     * @return the trade/list page.
     */
    @RequestMapping("/trade/list")
    public String home(Model model) {
        model.addAttribute("tradeList", tradeService.findAll());
        return "trade/list";
    }

    /**
     * It displays the trade/add form when a GET request to the following URL is made.
     *
     * @param trade - Trade entity. Must not be null.
     * @return the trade/add page.
     */
    @GetMapping("/trade/add")
    public String addTradeForm(Trade trade) {
        return "trade/add";
    }

    /**
     * A method which saves trade's data into database after the submission is completed and without errors.
     *
     * @param trade  - Trade entity. Must not be null.
     * @param result - permits to handle bind errors and to display it to the user when there are errors on the form fields.
     * @param model  - it permits to add "tradeList" to the model and to display all the trades registered in Poseidon
     *               when the user is redirected to trade/list page.
     * @return it redirects the user to the trade/list page if the submission is completed and without errors. Otherwise the trade/add form is returned.
     */
    @PostMapping("/trade/validate")
    public String validate(@Valid Trade trade, BindingResult result, Model model) {
        if (!result.hasErrors()) {
            logger.info("Trade was saved successfully.");
            tradeService.save(trade);
            model.addAttribute("tradeList", tradeService.findAll());
            return "redirect:/trade/list";
        }
        return "trade/add";
    }

    /**
     * It displays the update form when a GET request to the following URL is made.
     *
     * @param id    - it refers to trade's id which is used as the path variable.
     * @param model - it permits to define Trade entity as part of a Model and to display its data into form with the addAttribute method.
     * @return the trade/update page.
     */
    @GetMapping("/trade/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        logger.info("Trade was successfully fetched.");
        Trade trade = tradeService.findById(id);
        model.addAttribute("trade", trade);
        return "trade/update";
    }

    /**
     * A method which updates trade's data after the submission is completed and without errors.
     *
     * @param id-    it refers to trade's id which is used as the path variable.
     * @param trade   - Trade entity. Must not be null.
     * @param result - permits to handle bind errors and to display it to the user when there are errors on the form fields.
     * @param model  - it permits to add "tradeList" to the model and to display all the trades registered in Poseidon when the user is redirected to trade/list page.
     * @return it redirects the user to the trade/list page if the submission is completed and without errors. Otherwise the trade/update form is returned.
     */
    @PostMapping("/trade/update/{id}")
    public String updateTrade(@PathVariable("id") Integer id, @Valid Trade trade, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "trade/update";
        }
        logger.info("Trade data were updated successfully.");
        tradeService.update(id, trade);
        model.addAttribute("tradeList", tradeService.findAll());
        return "redirect:/trade/list";
    }

    /**
     * A method which deletes a user when a GET request to the following URL is made.
     *
     * @param id-   it refers to trade's id which is used as the path variable.
     * @param model - it permits to add "tradeList" to the model and to display all the trades registered in Poseidon
     *              when the user is redirected to trade/list page after the delete operation.
     * @return it redirects the user to the trade/list page after the delete operation.
     */
    @GetMapping("/trade/delete/{id}")
    public String deleteTrade(@PathVariable("id") Integer id, Model model) {
        logger.info("Trade data were deleted successfully.");
        tradeService.delete(id);
        model.addAttribute("tradeList", tradeService.findAll());
        return "redirect:/trade/list";
    }
}
