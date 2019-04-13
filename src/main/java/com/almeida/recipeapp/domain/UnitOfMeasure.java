package com.almeida.recipeapp.domain;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.UUID;



@Getter
@Setter
@Document
public class UnitOfMeasure {

    @Id
    private UUID id;
    private String description;

    public UnitOfMeasure() {
        this.id = UUID.randomUUID();
    }



}