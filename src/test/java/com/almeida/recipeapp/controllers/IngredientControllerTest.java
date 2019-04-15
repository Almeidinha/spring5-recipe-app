package com.almeida.recipeapp.controllers;

import com.almeida.recipeapp.commands.IngredientCommand;
import com.almeida.recipeapp.commands.RecipeCommand;
import com.almeida.recipeapp.commands.UnitOfMeasureCommand;
import com.almeida.recipeapp.services.IngredientService;
import com.almeida.recipeapp.services.RecipeService;
import com.almeida.recipeapp.services.UnitOfMeasureService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.HashSet;
import java.util.UUID;

import static org.mockito.internal.verification.VerificationModeFactory.times;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class IngredientControllerTest {

    @Mock
    IngredientService ingredientService;

    @Mock
    UnitOfMeasureService unitOfMeasureService;

    @Mock
    RecipeService recipeService;

    IngredientController controller;

    MockMvc mockMvc;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        controller = new IngredientController(ingredientService, recipeService, unitOfMeasureService);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    public void listIngredientsTest() throws Exception {
        // given
        RecipeCommand recipeCommand = new RecipeCommand();
        Mockito.when(recipeService.findCommandById(Mockito.any(UUID.class))).thenReturn(Mono.just(recipeCommand));

        // when
        mockMvc.perform(get("/recipe/ingredients/" + UUID.randomUUID()))
        .andExpect(status().isOk())
        .andExpect(view().name("recipe/ingredient/list"))
        .andExpect(model().attributeExists("recipe"));

        // then
        Mockito.verify(recipeService, Mockito.times(1)).findCommandById(Mockito.any(UUID.class));
    }
    @Test
    public void testShowIngredient() throws Exception {
        //given
        IngredientCommand ingredientCommand = new IngredientCommand();

        //when
        Mockito.when(ingredientService.findByRecipeIdAndIngredientId(
                Mockito.any(UUID.class), Mockito.any(UUID.class))).thenReturn(Mono.just(ingredientCommand));

        //then
        mockMvc.perform(get("/recipe/"+ UUID.randomUUID() +"/ingredient/"+ UUID.randomUUID() +"/show"))
                .andExpect(status().isOk())
                .andExpect(view().name("recipe/ingredient/show"))
                .andExpect(model().attributeExists("ingredient"));
    }

    @Test
    public void testNewIngredientForm() throws Exception {
        //given
        RecipeCommand recipeCommand = new RecipeCommand();

        //when
        Mockito.when(recipeService.findCommandById(Mockito.any(UUID.class))).thenReturn(Mono.just(recipeCommand));
        Mockito.when(unitOfMeasureService.listAllUoms()).thenReturn(Flux.just(new UnitOfMeasureCommand()));

        //then
        mockMvc.perform(get("/recipe/"+ recipeCommand.getId() +"/ingredient/new"))
                .andExpect(status().isOk())
                .andExpect(view().name("recipe/ingredient/ingredientform"))
                .andExpect(model().attributeExists("ingredient"))
                .andExpect(model().attributeExists("uomList"));

        Mockito.verify(recipeService, Mockito.times(1)).findCommandById(Mockito.any(UUID.class));

    }

    @Test
    public void testUpdateIngredientForm() throws Exception {
        //given
        IngredientCommand ingredientCommand = new IngredientCommand();

        //when
        Mockito.when(ingredientService
                .findByRecipeIdAndIngredientId(Mockito.any(UUID.class), Mockito.any(UUID.class))).thenReturn(Mono.just(ingredientCommand));
        Mockito.when(unitOfMeasureService.listAllUoms()).thenReturn(Flux.just(new UnitOfMeasureCommand()));

        //then
        mockMvc.perform(get("/recipe/"+ UUID.randomUUID() +"/ingredient/"+ UUID.randomUUID() +"/update"))
                .andExpect(status().isOk())
                .andExpect(view().name("recipe/ingredient/ingredientform"))
                .andExpect(model().attributeExists("ingredient"))
                .andExpect(model().attributeExists("uomList"));
    }

    @Test
    public void testSaveOrUpdate() throws Exception {
        //given
        IngredientCommand command = new IngredientCommand();
        command.setId(UUID.randomUUID());
        command.setRecipeId(UUID.randomUUID());

        //when
        Mockito.when(ingredientService.saveIngredientCommand(Mockito.any())).thenReturn(Mono.just(command));

        //then
        mockMvc.perform(post("/recipe/"+ command.getRecipeId() +"/ingredient")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("id", "")
                .param("description", "some string")
        )
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/recipe/"+ command.getRecipeId() +"/ingredient/"+ command.getId() +"/show"));
    }

    @Test
    public void testDeleteIngredient() throws Exception {

        UUID recipeId = UUID.randomUUID();
        UUID ingredientId = UUID.randomUUID();

        Mockito.when(ingredientService
                .deleteById(Mockito.any(UUID.class), Mockito.any(UUID.class))).thenReturn(Mono.empty());
        //then
        mockMvc.perform(get("/recipe/" + recipeId + "/ingredient/" + ingredientId + "/delete")
        )
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/recipe/ingredients/" + recipeId ));

        Mockito.verify(ingredientService, times(1))
                .deleteById(Mockito.any(UUID.class), Mockito.any(UUID.class));

    }



}