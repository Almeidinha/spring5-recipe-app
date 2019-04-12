package com.almeida.recipeapp.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
@EqualsAndHashCode(exclude = {"recipe"}) // Lombok doesn't like ManyToOne relations =(
public class Ingredient {

    private UUID id;
    private String description;
    private BigDecimal amount;
    UnitOfMeasure unitOfMeasure;
    private Recipe recipe;

    public Ingredient() {
        this.id = UUID.randomUUID();
    }

    public Ingredient(String description, BigDecimal amount, UnitOfMeasure unitOfMeasure) {
        this.id = UUID.randomUUID();
        this.description = description;
        this.amount = amount;
        this.unitOfMeasure = unitOfMeasure;
    }

    public Ingredient(String description, BigDecimal amount, UnitOfMeasure unitOfMeasure, Recipe recipe) {
        this.id = UUID.randomUUID();
        this.description = description;
        this.amount = amount;
        this.unitOfMeasure = unitOfMeasure;
        this.recipe = recipe;
    }

}
