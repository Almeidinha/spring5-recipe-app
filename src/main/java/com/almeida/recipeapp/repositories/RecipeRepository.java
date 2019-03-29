package com.almeida.recipeapp.repositories;

import com.almeida.recipeapp.domain.Recipe;
import org.springframework.data.repository.CrudRepository;

import java.rmi.server.UID;

public interface RecipeRepository extends CrudRepository<Recipe, UID> {
}
