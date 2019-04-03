package com.almeida.recipeapp.controllers;

import com.almeida.recipeapp.commands.RecipeCommand;
import com.almeida.recipeapp.services.RecipeService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class IngredientColtrollerTest {

    @Mock
    RecipeService recipeService;

    IngredientController ingredientColtroller;

    MockMvc mockMvc;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        ingredientColtroller = new IngredientController(recipeService);
        mockMvc = MockMvcBuilders.standaloneSetup(ingredientColtroller).build();
    }

    @Test
    public void listIngredientsTest() throws Exception {
        // given
        RecipeCommand recipeCommand = new RecipeCommand();
        Mockito.when(recipeService.findCommandById(Mockito.any(UUID.class))).thenReturn(recipeCommand);

        // when
        mockMvc.perform(get("/recipe/ingredients/" + UUID.randomUUID()))
        .andExpect(status().isOk())
        .andExpect(view().name("recipe/ingredient/list"))
        .andExpect(model().attributeExists("recipe"));

        // then
        Mockito.verify(recipeService, Mockito.times(1)).findCommandById(Mockito.any(UUID.class));
    }
}