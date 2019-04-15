package com.almeida.recipeapp.services;

import com.almeida.recipeapp.commands.UnitOfMeasureCommand;
import com.almeida.recipeapp.converters.UnitOfMeasureToUnitOfMeasureCommand;
import com.almeida.recipeapp.domain.UnitOfMeasure;
import com.almeida.recipeapp.repositories.reactive.UnitOfMeasureReactiveRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Service
public class UnitOfMeasureServiceImpl implements UnitOfMeasureService {

    private final UnitOfMeasureReactiveRepository unitOfMeasureRepository;
    private final UnitOfMeasureToUnitOfMeasureCommand unitOfMeasureToUnitOfMeasureCommand;

    public UnitOfMeasureServiceImpl(UnitOfMeasureReactiveRepository unitOfMeasureRepository, UnitOfMeasureToUnitOfMeasureCommand unitOfMeasureToUnitOfMeasureCommand) {
        this.unitOfMeasureRepository = unitOfMeasureRepository;
        this.unitOfMeasureToUnitOfMeasureCommand = unitOfMeasureToUnitOfMeasureCommand;
    }

    @Override
    public Flux<UnitOfMeasureCommand> listAllUoms() {

        return unitOfMeasureRepository
                .findAll()
                .map(unitOfMeasureToUnitOfMeasureCommand::convert);
    }

    @Override
    public Mono<UnitOfMeasureCommand> findById(UUID id) {


        return unitOfMeasureRepository.findById(id)
                .map(unitOfMeasure -> {
                    UnitOfMeasureCommand unitOfMeasureCommand = unitOfMeasureToUnitOfMeasureCommand.convert(unitOfMeasure);
                    // enhance command object with id value
                    return unitOfMeasureCommand;
                });

    }
}
