package com.almeida.recipeapp.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.UUID;

@Data
@EqualsAndHashCode(exclude = {"recipe"}) // Lombok doesn't like ManyToMany relations =(

public class Notes {

    private UUID id;
    private Recipe recipe;
    private String recipeNotes;

    public Notes() {
        this.id = UUID.randomUUID();
    }

}
