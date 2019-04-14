package com.almeida.recipeapp.repositories.reactive;

import com.almeida.recipeapp.domain.UnitOfMeasure;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface UnitOfMeasureReactiveRepository extends ReactiveMongoRepository<UnitOfMeasure, UUID> {
    Mono<UnitOfMeasure> findByDescription(String description);
}
