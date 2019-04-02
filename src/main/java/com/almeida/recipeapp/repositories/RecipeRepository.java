package com.almeida.recipeapp.repositories;

import com.almeida.recipeapp.domain.Recipe;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface RecipeRepository extends CrudRepository<Recipe, UUID> {
}
