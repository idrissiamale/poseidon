package com.nnk.springboot.controllers;

import com.nnk.springboot.domains.RuleName;
import com.nnk.springboot.services.ruleName.RuleNameService;
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
 * Controller class which handles CRUD operations made by the user on the rule pages (ruleName/add, ruleName/list and ruleName/update).
 *
 * @see com.nnk.springboot.services.ruleName.RuleNameService
 */
@Controller
public class RuleNameController {
    private static final Logger logger = LogManager.getLogger("RuleNameController");
    private RuleNameService ruleNameService;

    @Autowired
    public RuleNameController(RuleNameService ruleNameService) {
        this.ruleNameService = ruleNameService;
    }

    /**
     * It displays the ruleName/list page when a GET request to the following URL is made.
     *
     * @param model - it permits to add "rules" to the model and to display all the rules registered in Poseidon.
     * @return the ruleName/list page.
     */
    @GetMapping("/rulename/list")
    public String home(Model model) {
        model.addAttribute("rules", ruleNameService.findAll());
        return "ruleName/list";
    }

    /**
     * It displays the ruleName/add form when a GET request to the following URL is made.
     *
     * @param ruleName - RuleName entity. Must not be null.
     * @return the ruleName/add page.
     */
    @GetMapping("/rulename/add")
    public String addRuleForm(RuleName ruleName) {
        return "ruleName/add";
    }

    /**
     * A method which saves ruleName's data into database after the submission is completed and without errors.
     *
     * @param ruleName - RuleName entity. Must not be null.
     * @param result   - permits to handle bind errors and to display it to the user when there are errors on the form fields.
     * @param model    - it permits to add "rules" to the model and to display all the rules registered in Poseidon
     *                 when the user is redirected to ruleName/list page.
     * @return it redirects the user to the ruleName/list page if the submission is completed and without errors. Otherwise the ruleName/add form is returned.
     */
    @PostMapping("/rulename/validate")
    public String validate(@Valid RuleName ruleName, BindingResult result, Model model) {
        if (!result.hasErrors()) {
            logger.info("RuleName was saved successfully.");
            ruleNameService.save(ruleName);
            model.addAttribute("rules", ruleNameService.findAll());
            return "redirect:/rulename/list";
        }
        return "ruleName/add";
    }

    /**
     * It displays the update form when a GET request to the following URL is made.
     *
     * @param id    - it refers to ruleName's id which is used as the path variable.
     * @param model - it permits to define RuleName entity as part of a Model and to display its data into form with the addAttribute method.
     * @return the ruleName/update page.
     */
    @GetMapping("/rulename/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        logger.info("RuleName was successfully fetched.");
        RuleName ruleName = ruleNameService.findById(id);
        model.addAttribute("ruleName", ruleName);
        return "ruleName/update";
    }

    /**
     * A method which updates ruleName's data after the submission is completed and without errors.
     *
     * @param id       - it refers to ruleName's id which is used as the path variable.
     * @param ruleName - RuleName entity. Must not be null.
     * @param result   - permits to handle bind errors and to display it to the user when there are errors on the form fields.
     * @param model    - it permits to add "rules" to the model and to display all the rules registered in Poseidon when the user is redirected to ruleName/list page.
     * @return it redirects the user to the ruleName/list page if the submission is completed and without errors. Otherwise the ruleName/update form is returned.
     */
    @PostMapping("/rulename/update/{id}")
    public String updateRuleName(@PathVariable("id") Integer id, @Valid RuleName ruleName, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "ruleName/update";
        }
        logger.info("RuleName was updated successfully.");
        ruleNameService.update(id, ruleName);
        model.addAttribute("rules", ruleNameService.findAll());
        return "redirect:/rulename/list";
    }

    /**
     * A method which deletes a rule when a GET request to the following URL is made.
     *
     * @param id    - it refers to ruleName's id which is used as the path variable.
     * @param model - it permits to add "rules" to the model and to display all the rules registered in Poseidon
     *              when the user is redirected to ruleName/list page after the delete operation.
     * @return it redirects the user to the ruleName/list page after the delete operation.
     */
    @GetMapping("/rulename/delete/{id}")
    public String deleteRuleName(@PathVariable("id") Integer id, Model model) {
        logger.info("RuleName was deleted successfully.");
        ruleNameService.delete(id);
        model.addAttribute("rules", ruleNameService.findAll());
        return "redirect:/rulename/list";
    }
}
