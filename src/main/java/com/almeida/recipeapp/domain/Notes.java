package com.almeida.recipeapp.domain;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;

import java.util.UUID;

@Getter
@Setter
public class Notes {

    @Id
    private UUID id;
    private String recipeNotes;

    public Notes() {
        this.id = UUID.randomUUID();
    }

}
