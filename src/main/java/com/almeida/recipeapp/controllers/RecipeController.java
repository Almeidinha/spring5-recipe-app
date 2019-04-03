package com.almeida.recipeapp.controllers;

import com.almeida.recipeapp.commands.RecipeCommand;
import com.almeida.recipeapp.services.RecipeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Slf4j
@Controller
public class RecipeController {

    private final RecipeService recipeService;

    public RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @GetMapping
    @RequestMapping("/recipe/show/{id}")
    public String showById(@PathVariable UUID id, Model model){

        model.addAttribute("recipe", recipeService.findById(id));

        return "recipe/show";
    }

    @GetMapping
    @RequestMapping("recipe/new")
    public String newRecipe(Model model) {
        model.addAttribute("recipe", new RecipeCommand());

        return "recipe/recipeform";
    }

    @GetMapping
    @RequestMapping("recipe/update/{id}")
    public String updateRecipe(@PathVariable String id, Model model) {
        model.addAttribute("recipe", recipeService.findCommandById(UUID.fromString(id)));
        return "recipe/recipeform";
    }

    @PostMapping
    @RequestMapping("recipe")
    public String saveOrUpdate(@ModelAttribute RecipeCommand command) {
        RecipeCommand savedCommand = recipeService.saveRecipeCommand(command);

        return "redirect:/recipe/show/" + savedCommand.getId();
    }

    @GetMapping
    @RequestMapping("recipe/delete/{id}")
    public String deleteById(@PathVariable String id) {
        log.debug("Deleting id: " + id);

        recipeService.deleteById(UUID.fromString(id));
        return "redirect:/";
    }
}