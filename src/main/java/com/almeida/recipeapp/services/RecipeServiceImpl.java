package com.almeida.recipeapp.services;

import com.almeida.recipeapp.commands.RecipeCommand;
import com.almeida.recipeapp.converters.RecipeCommandToRecipe;
import com.almeida.recipeapp.converters.RecipeToRecipeCommand;
import com.almeida.recipeapp.domain.Recipe;
import com.almeida.recipeapp.repositories.reactive.RecipeReactiveRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Slf4j
@Service
public class RecipeServiceImpl implements RecipeService {

    private final RecipeReactiveRepository recipeRepository;
    private final RecipeCommandToRecipe recipeCommandToRecipe;
    private final RecipeToRecipeCommand recipeToRecipeCommand;

    public RecipeServiceImpl(RecipeReactiveRepository recipeRepository, RecipeCommandToRecipe recipeCommandToRecipe, RecipeToRecipeCommand recipeToRecipeCommand) {
        this.recipeRepository = recipeRepository;
        this.recipeCommandToRecipe = recipeCommandToRecipe;
        this.recipeToRecipeCommand = recipeToRecipeCommand;
    }

    @Override
    public Flux<Recipe> getRecipes() {
        log.debug("I'm am in the Service!!!");
        return recipeRepository.findAll();
    }

    @Override
    public Mono<Recipe> findById(UUID id) {
        return recipeRepository.findById(id);
    }

    @Override
    public Mono<RecipeCommand> findCommandById(UUID id) {

        return recipeRepository.findById(id)
                .map(recipe -> {
                    RecipeCommand recipeCommand = recipeToRecipeCommand.convert(recipe);
                    // enhance command object with id value
                    recipeCommand.getIngredients().forEach(rc -> {
                        rc.setRecipeId(recipeCommand.getId());
                    });
                    return recipeCommand;
                });
    }

    @Override
    public Mono<RecipeCommand> saveRecipeCommand(RecipeCommand command) {
        return recipeRepository.save(recipeCommandToRecipe.convert(command))
                .map(recipeToRecipeCommand::convert);
    }

    @Override
    public Mono<Void> deleteById(UUID id) {
        recipeRepository.deleteById(id).block();
        return Mono.empty();
    }
}
