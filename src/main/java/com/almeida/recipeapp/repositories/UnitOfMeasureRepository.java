package com.almeida.recipeapp.repositories;

import com.almeida.recipeapp.domain.UnitOfMeasure;
import org.springframework.data.repository.CrudRepository;

import java.rmi.server.UID;
import java.util.Optional;

public interface UnitOfMeasureRepository extends CrudRepository<UnitOfMeasure, UID> {

    Optional<UnitOfMeasure> findByDescription(String description);
}
