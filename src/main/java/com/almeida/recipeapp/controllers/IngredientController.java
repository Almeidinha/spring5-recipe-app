package com.almeida.recipeapp.controllers;

import com.almeida.recipeapp.services.IngredientService;
import com.almeida.recipeapp.services.RecipeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.UUID;

@Slf4j
@Controller
public class IngredientController {

    private final RecipeService recipeService;
    private final IngredientService ingredientService;

    public IngredientController(RecipeService recipeService, IngredientService ingredientService) {
        this.recipeService = recipeService;
        this.ingredientService = ingredientService;
    }

    @GetMapping
    @RequestMapping("/recipe/ingredients/{id}")
    public String listIngredients(@PathVariable String id, Model model) {
        log.debug("Getting ogredients list for recipe id: " + id);

        // use command to avoid load errors in Thymeleaf
        model.addAttribute("recipe", recipeService.findCommandById(UUID.fromString(id)));

        return "recipe/ingredient/list";
    }
    @GetMapping
    @RequestMapping("recipe/{recipeId}/ingredient/{id}/show")
    public String showRecipeIngredient(@PathVariable String recipeId,
                                       @PathVariable String id, Model model){
        model.addAttribute("ingredient", ingredientService
                .findByRecipeIdAndIngredientId(UUID.fromString(recipeId), UUID.fromString(id)));
        return "recipe/ingredient/show";
    }

}
