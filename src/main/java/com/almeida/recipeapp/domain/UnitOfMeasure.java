package com.almeida.recipeapp.domain;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.UUID;

// import javax.persistence.GeneratedValue;
// import javax.persistence.GenerationType;

@Entity
public class UnitOfMeasure {

    @Id
    //@GeneratedValue(strategy = GenerationType.IDENTITY)
    private UUID id;
    private String description;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public UnitOfMeasure() {
        this.id = UUID.randomUUID();
    }
}