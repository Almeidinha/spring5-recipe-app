package com.almeida.recipeapp.services;

import com.almeida.recipeapp.domain.Recipe;

import java.util.Set;
//@Service
public interface RecipeService {

    Set<Recipe> getRecipes();
}
