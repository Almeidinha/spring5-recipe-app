package com.almeida.recipeapp.controllers;

import com.almeida.recipeapp.commands.RecipeCommand;
import com.almeida.recipeapp.exceptions.NotFoundException;
import com.almeida.recipeapp.services.RecipeService;
import com.almeida.recipeapp.services.UnitOfMeasureService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.UUID;

@Slf4j
@Controller
public class RecipeController {

    private final RecipeService recipeService;
    @Autowired
    private final UnitOfMeasureService unitOfMeasureService;
    private static final String RECIPE_RECIPEFORM_URL = "recipe/recipeform";

    public RecipeController(RecipeService recipeService,UnitOfMeasureService unitOfMeasureService) {
        this.recipeService = recipeService;
        this.unitOfMeasureService = unitOfMeasureService;
    }

    @GetMapping("/recipe/show/{id}")
    public String showById(@PathVariable UUID id, Model model){

        model.addAttribute("recipe", recipeService.findById(id).block());
        return "recipe/show";
    }

    @GetMapping("recipe/new")
    public String newRecipe(Model model) {
        model.addAttribute("recipe", new RecipeCommand());
        return RECIPE_RECIPEFORM_URL;
    }

    @GetMapping("recipe/update/{id}")
    public String updateRecipe(@PathVariable String id, Model model) {
        model.addAttribute("recipe", recipeService.findCommandById(UUID.fromString(id)).block());
        return RECIPE_RECIPEFORM_URL;
    }

    @PostMapping("recipe")
    public String saveOrUpdate(@Valid @ModelAttribute("recipe") RecipeCommand command, BindingResult result) {
        if (result.hasErrors()) {
            result.getAllErrors().forEach(objectError -> {
                log.debug(objectError.toString());
            });
            return RECIPE_RECIPEFORM_URL;
        }
        // todo Review thia later...
        command.getIngredients().forEach(ingredientCommand -> {
            ingredientCommand.setUnitOfMeasure(
                    unitOfMeasureService.findById(ingredientCommand.getUnitOfMeasure().getId()).block());
        });

        RecipeCommand savedCommand = recipeService.saveRecipeCommand(command).block();
        return "redirect:/recipe/show/" + savedCommand.getId();
    }

    @GetMapping("recipe/delete/{id}")
    public String deleteById(@PathVariable String id) {
        log.debug("Deleting id: " + id);
        recipeService.deleteById(UUID.fromString(id));
        return "redirect:/";
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NotFoundException.class)
    public ModelAndView handleNotFount(Exception e) {
        log.error("Handling not found exception");
        log.error(e.getMessage());

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("404error");
        modelAndView.addObject("exception", e);

        return modelAndView;
    }


}
