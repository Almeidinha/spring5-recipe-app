package com.almeida.recipeapp.domain;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.UUID;

// import javax.persistence.GeneratedValue;
// import javax.persistence.GenerationType;

@Data
@Entity
public class UnitOfMeasure {

    @Id
    //@GeneratedValue(strategy = GenerationType.IDENTITY)
    private UUID id;
    private String description;

    public UnitOfMeasure() {
        this.id = UUID.randomUUID();
    }



}