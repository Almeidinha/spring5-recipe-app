package com.almeida.recipeapp.services;

import com.almeida.recipeapp.commands.UnitOfMeasureCommand;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;


public interface UnitOfMeasureService {

    Flux<UnitOfMeasureCommand> listAllUoms();

    Mono<UnitOfMeasureCommand> findById(UUID id);
}
