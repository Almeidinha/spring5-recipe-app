package com.almeida.recipeapp.commands;

import com.almeida.recipeapp.enums.Difficulty;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
public class RecipeCommand {
    private UUID id;
    private String description;
    private Integer prepTime;
    private Integer cookTime;
    private Integer servings;
    private String source;
    private String url;
    private String directions;
    private Set<IngredientCommand> ingredients = new HashSet<>();
    private Difficulty difficulty;
    private NotesCommand notes;
    private Set<CategoryCommand> categories = new HashSet<>();

    public RecipeCommand() {
        this.id = UUID.randomUUID();
    }
}
