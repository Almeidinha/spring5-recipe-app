package com.almeida.recipeapp.commands;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class NotesCommand {
    private UUID id;
    private String recipeNotes;

    public NotesCommand() {
        this.id = UUID.randomUUID();
    }
}
