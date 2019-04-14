package com.almeida.recipeapp.repositories.reactive;

import com.almeida.recipeapp.domain.Category;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface CategoryReactiveRepository extends ReactiveMongoRepository<Category, UUID> {
    Mono<Category> findByDescription(String description);
}
