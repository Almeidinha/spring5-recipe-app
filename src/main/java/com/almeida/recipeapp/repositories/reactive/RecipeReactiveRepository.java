package com.almeida.recipeapp.repositories.reactive;

import com.almeida.recipeapp.domain.Recipe;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

/**
 * Created by jt on 8/17/17.
 */
public interface RecipeReactiveRepository extends ReactiveMongoRepository<Recipe, String>{
}
