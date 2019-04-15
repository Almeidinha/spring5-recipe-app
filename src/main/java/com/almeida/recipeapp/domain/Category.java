package com.almeida.recipeapp.domain;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@Document
public class Category {

    @Id
    private UUID id;
    private String description;
    private Set<Recipe> recipes;

    public Category() {
        this.id = UUID.randomUUID();
    }
}
