package com.almeida.recipeapp.controllers;

import com.almeida.recipeapp.services.RecipeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Slf4j
@Controller
//@RequestMapping()
public class IndexController {

    private final RecipeService recipeService;

    public IndexController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @GetMapping({"", "/", "/index"})
    public String getIndex(Model model) {
        log.debug("Getting Index page");

        model.addAttribute("recipes", recipeService.getRecipes().collectList().block());
        return "index";
    }
}
