package com.almeida.recipeapp.services;

import com.almeida.recipeapp.commands.IngredientCommand;
import reactor.core.publisher.Mono;

/**
 * Created by jt on 6/27/17.
 */
public interface IngredientService {

    Mono<IngredientCommand> findByRecipeIdAndIngredientId(String recipeId, String ingredientId);

    Mono<IngredientCommand> saveIngredientCommand(IngredientCommand command);

    Mono<Void> deleteById(String recipeId, String idToDelete);
}
