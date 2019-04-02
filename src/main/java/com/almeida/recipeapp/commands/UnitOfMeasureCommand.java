package com.almeida.recipeapp.commands;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
public class UnitOfMeasureCommand {
    private UUID id;
    private String description;
}
