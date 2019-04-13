package com.almeida.recipeapp.domain;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
public class Ingredient {

    @Id
    private UUID id;
    private String description;
    private BigDecimal amount;

    @DBRef
    UnitOfMeasure unitOfMeasure;

    public Ingredient() {
        this.id = UUID.randomUUID();
    }

    public Ingredient(String description, BigDecimal amount, UnitOfMeasure unitOfMeasure) {
        this.id = UUID.randomUUID();
        this.description = description;
        this.amount = amount;
        this.unitOfMeasure = unitOfMeasure;
    }

}
