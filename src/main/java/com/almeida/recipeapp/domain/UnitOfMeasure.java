package com.almeida.recipeapp.domain;

import lombok.Data;

import java.util.UUID;

// import javax.persistence.GeneratedValue;
// import javax.persistence.GenerationType;

@Data
public class UnitOfMeasure {

    private UUID id;
    private String description;

    public UnitOfMeasure() {
        this.id = UUID.randomUUID();
    }



}