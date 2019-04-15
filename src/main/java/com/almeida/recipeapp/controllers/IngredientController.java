package com.almeida.recipeapp.controllers;

import com.almeida.recipeapp.commands.IngredientCommand;
import com.almeida.recipeapp.commands.RecipeCommand;
import com.almeida.recipeapp.commands.UnitOfMeasureCommand;
import com.almeida.recipeapp.services.IngredientService;
import com.almeida.recipeapp.services.RecipeService;
import com.almeida.recipeapp.services.UnitOfMeasureService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.UUID;

@Slf4j
@Controller
public class IngredientController {

    private final IngredientService ingredientService;
    private final RecipeService recipeService;
    private final UnitOfMeasureService unitOfMeasureService;

    public IngredientController(IngredientService ingredientService, RecipeService recipeService, UnitOfMeasureService unitOfMeasureService) {
        this.ingredientService = ingredientService;
        this.recipeService = recipeService;
        this.unitOfMeasureService = unitOfMeasureService;
    }

    @GetMapping("/recipe/ingredients/{id}")
    public String listIngredients(@PathVariable String id, Model model) {
        log.debug("Getting ogredients list for recipe id: " + id);

        // use command to avoid load errors in Thymeleaf
        model.addAttribute("recipe", recipeService.findCommandById(UUID.fromString(id)).block());

        return "recipe/ingredient/list";
    }

    @GetMapping("recipe/{recipeId}/ingredient/{id}/show")
    public String showRecipeIngredient(@PathVariable String recipeId,
                                       @PathVariable String id, Model model){
        model.addAttribute("ingredient", ingredientService
                .findByRecipeIdAndIngredientId(UUID.fromString(recipeId), UUID.fromString(id)).block());
        return "recipe/ingredient/show";
    }

    @GetMapping("recipe/{recipeId}/ingredient/new")
    public String newIngredient(@PathVariable String recipeId, Model model){

        //make sure we have a good id value
        RecipeCommand recipeCommand = recipeService.findCommandById(UUID.fromString(recipeId)).block();
        //todo raise exception if null

        //need to return back parent id for hidden form property
        IngredientCommand ingredientCommand = new IngredientCommand();
        ingredientCommand.setRecipeId(UUID.fromString(recipeId));
        model.addAttribute("ingredient", ingredientCommand);

        //init uom
        ingredientCommand.setUnitOfMeasure(new UnitOfMeasureCommand());
        model.addAttribute("uomList",  unitOfMeasureService.listAllUoms().collectList().block());

        return "recipe/ingredient/ingredientform";
    }

    @GetMapping("recipe/{recipeId}/ingredient/{id}/update")
    public String updateRecipeIngredient(@PathVariable String recipeId,
                                         @PathVariable String id, Model model){

        model.addAttribute("ingredient", ingredientService
                .findByRecipeIdAndIngredientId(UUID.fromString(recipeId), UUID.fromString(id)).block());

        model.addAttribute("uomList", unitOfMeasureService.listAllUoms().collectList().block());
        return "recipe/ingredient/ingredientform";
    }

    @PostMapping("recipe/{recipeId}/ingredient")
    public String saveOrUpdate(@ModelAttribute IngredientCommand command){
        command.setUnitOfMeasure(unitOfMeasureService.findById(command.getUnitOfMeasure().getId()).block());
        IngredientCommand savedCommand = ingredientService.saveIngredientCommand(command).block();

        log.debug("saved ingredient id:" + savedCommand.getId());
        return "redirect:/recipe/" + savedCommand.getRecipeId() + "/ingredient/" + savedCommand.getId() + "/show";
    }

    @GetMapping("recipe/{recipeId}/ingredient/{id}/delete")
    public String deleteIngredient(@PathVariable String recipeId,
                                   @PathVariable String id) {

        log.debug("deleting ingredient id:" + id);
        ingredientService.deleteById(UUID.fromString(recipeId), UUID.fromString(id)).block();
        return "redirect:/recipe/ingredients/" + recipeId;
    }

}
