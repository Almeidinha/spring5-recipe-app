package com.almeida.recipeapp.commands;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class UnitOfMeasureCommand {
    private UUID id;
    private String description;

    public UnitOfMeasureCommand() {
        this.id = UUID.randomUUID();
    }
}
