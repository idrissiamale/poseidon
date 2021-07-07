package com.nnk.springboot.controllers;

import com.nnk.springboot.domains.CurvePoint;
import com.nnk.springboot.services.curvePoint.CurvePointService;
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
 * Controller class which handles CRUD operations made by the user on the curvePoint pages (curvePoint/add, curvePoint/list and curvePoint/update).
 *
 * @see com.nnk.springboot.services.curvePoint.CurvePointService
 */
@Controller
public class CurveController {
    private static final Logger logger = LogManager.getLogger("CurveController");
    private CurvePointService curvePointService;

    @Autowired
    public CurveController(CurvePointService curvePointService) {
        this.curvePointService = curvePointService;
    }

    /**
     * It displays the curvePoint/list page when a GET request to the following URL is made.
     *
     * @param model - it permits to add "curvePoints" to the model and to display all the curve points registered in Poseidon.
     * @return the curvePoint/list page.
     */
    @GetMapping("/curvePoint/list")
    public String home(Model model) {
        model.addAttribute("curvePoints", curvePointService.findAll());
        return "curvePoint/list";
    }

    /**
     * It displays the curvePoint/add form when a GET request to the following URL is made.
     *
     * @param curvePoint - CurvePoint entity. Must not be null.
     * @return the curvePoint/add page.
     */
    @GetMapping("/curvePoint/add")
    public String addCurvePointForm(CurvePoint curvePoint) {
        return "curvePoint/add";
    }

    /**
     * A method which saves curve point's data into database after the submission is completed and without errors.
     *
     * @param curvePoint - CurvePoint entity. Must not be null.
     * @param result     - permits to handle bind errors and to display it to the user when there are errors on the form fields.
     * @param model      - it permits to add "curvePoints" to the model and to display all the curve points registered in Poseidon
     *                   when the user is redirected to curvePoint/list page.
     * @return it redirects the user to the curvePoint/list page if the submission is completed and without errors. Otherwise the curvePoint/add form is returned.
     */
    @PostMapping("/curvePoint/validate")
    public String validate(@Valid CurvePoint curvePoint, BindingResult result, Model model) {
        if (!result.hasErrors()) {
            logger.info("CurvePoint was saved successfully.");
            curvePointService.save(curvePoint);
            model.addAttribute("curvePoints", curvePointService.findAll());
            return "redirect:/curvePoint/list";
        }
        return "curvePoint/add";
    }

    /**
     * It displays the update form when a GET request to the following URL is made.
     *
     * @param id    - it refers to curvePoint's id which is used as the path variable.
     * @param model - it permits to define CurvePoint entity as part of a Model and to display its data into form with the addAttribute method.
     * @return the curvePoint/update page.
     */
    @GetMapping("/curvePoint/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        logger.info("CurvePoint was successfully fetched.");
        CurvePoint curvePoint = curvePointService.findById(id);
        model.addAttribute("curvePoint", curvePoint);
        return "curvePoint/update";
    }

    /**
     * A method which updates curve point's data after the submission is completed and without errors.
     *
     * @param id         - it refers to curvePoint's id which is used as the path variable.
     * @param curvePoint - CurvePoint entity. Must not be null.
     * @param result     - permits to handle bind errors and to display it to the user when there are errors on the form fields.
     * @param model      - it permits to add "curvePoints" to the model and to display all the curve points registered in Poseidon when the user is redirected to curvePoint/list page.
     * @return it redirects the user to the curvePoint/list page if the submission is completed and without errors. Otherwise the curvePoint/update form is returned.
     */
    @PostMapping("/curvePoint/update/{id}")
    public String updateCurvePoint(@PathVariable("id") Integer id, @Valid CurvePoint curvePoint, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "curvePoint/update";
        }
        logger.info("CurvePoint was updated successfully.");
        curvePointService.update(id, curvePoint);
        model.addAttribute("curvePoints", curvePointService.findAll());
        return "redirect:/curvePoint/list";
    }

    /**
     * A method which deletes a curve point when a GET request to the following URL is made.
     *
     * @param id    - it refers to curvePoint's id which is used as the path variable.
     * @param model - it permits to add "curvePoints" to the model and to display all the curve points registered in Poseidon
     *              when the user is redirected to curvePoint/list page after the delete operation.
     * @return it redirects the user to the curvePoint/list page after the delete operation.
     */
    @GetMapping("/curvePoint/delete/{id}")
    public String deleteCurvePoint(@PathVariable("id") Integer id, Model model) {
        logger.info("CurvePoint was deleted successfully.");
        curvePointService.delete(id);
        model.addAttribute("curvePoints", curvePointService.findAll());
        return "redirect:/curvePoint/list";
    }
}
