package com.almeida.recipeapp.domain;

import com.almeida.recipeapp.enums.Difficulty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@Document
public class Recipe {

    @Id
    private UUID id;
    private String description;
    private Integer prepTime;
    private Integer cookTime;
    private Integer servings;
    private String source;
    private String url;
    private String directions;
    private Set<Ingredient> ingredients = new HashSet<>();
    private Byte[] image;
    private Difficulty difficulty;
    private Notes notes;

    @DBRef
    private Set<Category> categories = new HashSet<>();

    public void setNotes(Notes notes) {
        if (notes != null) {
            this.notes = notes;
        }
    }

    public Recipe addIngredient(Ingredient ingredient) {
        this.ingredients.add(ingredient);
        return  this;
    }

    public Recipe() {
        this.id = UUID.randomUUID();
    }
}
