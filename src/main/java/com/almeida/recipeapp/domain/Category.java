package com.almeida.recipeapp.domain;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import java.util.Set;
import java.util.UUID;

@Data
@Entity
public class Category {

    @Id
    // @GeneratedValue(strategy = GenerationType.IDENTITY)
    private UUID id;
    private String description;

    @ManyToMany(mappedBy = "categories")
    private Set<Recipe> recipes;

    public Category() {
        this.id = UUID.randomUUID();
    }
}
