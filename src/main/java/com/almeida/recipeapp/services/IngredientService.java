package com.almeida.recipeapp.services;

import com.almeida.recipeapp.commands.IngredientCommand;

import java.util.UUID;

public interface IngredientService {

    IngredientCommand findByRecipeIdAndIngredientId(UUID recipeId, UUID ingredientId);
}
