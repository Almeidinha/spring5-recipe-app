package com.almeida.recipeapp.controllers;

import com.almeida.recipeapp.commands.RecipeCommand;
import com.almeida.recipeapp.domain.Recipe;
import com.almeida.recipeapp.exceptions.NotFoundException;
import com.almeida.recipeapp.services.RecipeService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import reactor.core.publisher.Mono;

import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class RecipeControllerTest {

    @Mock
    RecipeService recipeService;

    RecipeController controller;

    MockMvc mockMvc;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        controller = new RecipeController(recipeService);
        mockMvc = MockMvcBuilders.standaloneSetup(controller)
                .setControllerAdvice(new ControllerExceptionHandler())
                .build();
    }

    @Test
    public void testGetRecipe() throws Exception {

        Recipe recipe = new Recipe();

        Mockito.when(recipeService.findById(Mockito.any(UUID.class))).thenReturn(Mono.just(recipe));

        mockMvc.perform(get("/recipe/show/" + recipe.getId()))
                .andExpect(status().isOk())
                .andExpect(view().name("recipe/show"))
                .andExpect(model().attributeExists("recipe"));
    }

    @Test
    public void textRecipeNotFound() throws Exception {
        Mockito.when(recipeService.findById(Mockito.any(UUID.class))).thenThrow(NotFoundException.class);

        mockMvc.perform(get("/recipe/show/" + UUID.randomUUID()))
                .andExpect(status().isNotFound())
                .andExpect(view().name("404error"));
    }

    @Test
    public void textRecipeIllegalArgumentException() throws Exception {

        mockMvc.perform(get("/recipe/show/asdfgh"))
                .andExpect(status().isBadRequest())
                .andExpect(view().name("400error"));
    }

    @Test
    public void testGetNewRecipeForm() throws Exception {
        RecipeCommand command = new RecipeCommand();

        mockMvc.perform(get("/recipe/new"))
                .andExpect(status().isOk())
                .andExpect(view().name("recipe/recipeform"))
                .andExpect(model().attributeExists("recipe"));
    }

    @Test
    public void testPostNewRecipeForm() throws Exception {
        UUID id = UUID.randomUUID();
        RecipeCommand command = new RecipeCommand();
        command.setId(id);

        Mockito.when(recipeService.saveRecipeCommand(Mockito.any())).thenReturn(Mono.just(command));

        mockMvc.perform(post("/recipe")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("id", "")
                .param("description", "some string")
                .param("directions", "some directions")
        )
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/recipe/show/" + id));
    }

    @Test
    public void testPostNewRecipeFormValidationFail() throws Exception {
        UUID id = UUID.randomUUID();
        RecipeCommand command = new RecipeCommand();
        command.setId(id);

        Mockito.when(recipeService.saveRecipeCommand(Mockito.any())).thenReturn(Mono.just(command));

        mockMvc.perform(post("/recipe")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("id", "")

        )
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("recipe"))
                .andExpect(view().name("recipe/recipeform"));
    }

    @Test
    public void testGetUpdateView() throws Exception {
        RecipeCommand command = new RecipeCommand();

        Mockito.when(recipeService.findCommandById(Mockito.any(UUID.class))).thenReturn(Mono.just(command));

        mockMvc.perform(get("/recipe/update/" + command.getId()))
                .andExpect(status().isOk())
                .andExpect(view().name("recipe/recipeform"))
                .andExpect(model().attributeExists("recipe"));
    }

    @Test
    public void testDeleteById() throws Exception {

        mockMvc.perform(get("/recipe/delete/" + UUID.randomUUID()))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/"));

        Mockito.verify(recipeService, Mockito.times(1)).deleteById(Mockito.any(UUID.class));
    }
}