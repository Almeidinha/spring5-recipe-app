package com.almeida.recipeapp.repositories;

import com.almeida.recipeapp.domain.Category;
import org.springframework.data.repository.CrudRepository;

import java.rmi.server.UID;
import java.util.Optional;

public interface CategoryRepository extends CrudRepository<Category, UID> {

    Optional<Category> findByDescription(String description);
}
