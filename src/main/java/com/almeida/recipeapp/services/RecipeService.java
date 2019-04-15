package com.almeida.recipeapp.services;

import com.almeida.recipeapp.commands.RecipeCommand;
import com.almeida.recipeapp.domain.Recipe;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

//@Service
public interface RecipeService {

    Flux<Recipe> getRecipes();

    Mono<Recipe> findById(UUID id);

    Mono<RecipeCommand> findCommandById(UUID fromString);

    Mono<RecipeCommand> saveRecipeCommand(RecipeCommand command);

    Mono<Void> deleteById(UUID id);

}
