package com.almeida.recipeapp.repositories;

import org.springframework.data.repository.CrudRepository;

import java.rmi.server.UID;

public interface RecipeRepository extends CrudRepository<RecipeRepository, UID> {
}
