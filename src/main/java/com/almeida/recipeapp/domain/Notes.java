package com.almeida.recipeapp.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.Type;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.OneToOne;
import java.util.UUID;

@Data
@EqualsAndHashCode(exclude = {"recipe"}) // Lombok doesn't like ManyToMany relations =(
@Entity
public class Notes {

    @Id
    @Type(type = "uuid-char")
    // @GeneratedValue(strategy = GenerationType.IDENTITY)
    private UUID id;

    @OneToOne
    private Recipe recipe;

    @Lob
    private String recipeNotes;

    public Notes() {
        this.id = UUID.randomUUID();
    }

}
