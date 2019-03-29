package com.almeida.recipeapp.repositories;

import com.almeida.recipeapp.domain.UnitOfMeasure;
import org.springframework.data.repository.CrudRepository;

import java.rmi.server.UID;

public interface UnitOfMeasureRepository extends CrudRepository<UnitOfMeasure, UID> {
}
