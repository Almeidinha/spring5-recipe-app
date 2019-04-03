package com.almeida.recipeapp.commands;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class CategoryCommand {
    private UUID id;
    private String description;

    public CategoryCommand() {
        this.id = UUID.randomUUID();
    }
}
