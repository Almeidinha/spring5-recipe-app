package com.almeida.recipeapp.commands;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
public class IngredientCommand {
    private UUID id;
    private UUID recipeId;
    private String description;
    private BigDecimal amount;
    private UnitOfMeasureCommand unitOfMeasure;

    public IngredientCommand() {
        this.id = UUID.randomUUID();
    }
}
