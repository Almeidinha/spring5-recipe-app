package com.almeida.recipeapp.repositories;

import com.almeida.recipeapp.domain.UnitOfMeasure;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;
import java.util.UUID;

public interface UnitOfMeasureRepository extends CrudRepository<UnitOfMeasure, UUID> {

    Optional<UnitOfMeasure> findByDescription(String description);
}
