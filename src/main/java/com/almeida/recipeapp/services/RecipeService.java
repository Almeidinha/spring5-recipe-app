package com.almeida.recipeapp.services;

import com.almeida.recipeapp.commands.RecipeCommand;
import com.almeida.recipeapp.domain.Recipe;

import java.util.Set;
import java.util.UUID;

//@Service
public interface RecipeService {

    Set<Recipe> getRecipes();

    Recipe findById(UUID id);

    RecipeCommand findCommandById(UUID fromString);

    RecipeCommand saveRecipeCommand(RecipeCommand command);

    void deleteById(UUID id);

}
