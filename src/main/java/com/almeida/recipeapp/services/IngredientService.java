package com.almeida.recipeapp.services;

import com.almeida.recipeapp.commands.IngredientCommand;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface IngredientService {

    Mono<IngredientCommand> findByRecipeIdAndIngredientId(UUID recipeId, UUID ingredientId);

    Mono<IngredientCommand> saveIngredientCommand(IngredientCommand command);

    Mono<Void> deleteById(UUID recipeId, UUID idToDelete);
}
