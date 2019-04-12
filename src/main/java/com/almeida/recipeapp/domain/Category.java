package com.almeida.recipeapp.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Set;
import java.util.UUID;

@Data
@EqualsAndHashCode(exclude = {"recipes"}) // Lombok doesn't like ManyToMany relations =(

public class Category {

    private UUID id;
    private String description;
    private Set<Recipe> recipes;

    public Category() {
        this.id = UUID.randomUUID();
    }
}
