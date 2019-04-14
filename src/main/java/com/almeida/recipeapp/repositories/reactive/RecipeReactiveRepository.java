package com.almeida.recipeapp.repositories.reactive;

import com.almeida.recipeapp.domain.Recipe;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import java.util.UUID;

public interface RecipeReactiveRepository extends ReactiveMongoRepository<Recipe, UUID> {
}
