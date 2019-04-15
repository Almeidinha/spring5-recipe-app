package com.almeida.recipeapp.services;

import com.almeida.recipeapp.commands.RecipeCommand;
import com.almeida.recipeapp.converters.RecipeCommandToRecipe;
import com.almeida.recipeapp.converters.RecipeToRecipeCommand;
import com.almeida.recipeapp.domain.Recipe;
import com.almeida.recipeapp.exceptions.NotFoundException;
import com.almeida.recipeapp.repositories.RecipeRepository;
import com.almeida.recipeapp.repositories.reactive.RecipeReactiveRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.internal.verification.VerificationModeFactory.times;

public class RecipeServiceImplTest {

    RecipeServiceImpl recipeService;

    @Mock
    RecipeReactiveRepository recipeRepository;

    @Mock
    RecipeToRecipeCommand recipeToRecipeCommand;

    @Mock
    RecipeCommandToRecipe recipeCommandToRecipe;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        recipeService = new RecipeServiceImpl(recipeRepository, recipeCommandToRecipe, recipeToRecipeCommand);
    }

    @Test
    public void getRecipeByIdTest() throws Exception {
        UUID id = UUID.randomUUID();
        Recipe recipe = new Recipe();
        recipe.setId(id);

        when(recipeRepository.findById(any(UUID.class))).thenReturn(Mono.just(recipe));

        Recipe recipeReturned = recipeService.findById(id).block();

        assertNotNull("Null recipe returned", recipeReturned);
        verify(recipeRepository, times(1)).findById(any(UUID.class));
        verify(recipeRepository, never()).findAll();
    }

    @Test
    public void getRecipeCommandByIdTest() throws Exception {
        UUID id = UUID.randomUUID();
        Recipe recipe = new Recipe();
        recipe.setId(id);

        when(recipeRepository.findById(any(UUID.class))).thenReturn(Mono.just(recipe));

        RecipeCommand recipeCommand = new RecipeCommand();
        recipeCommand.setId(id);

        when(recipeToRecipeCommand.convert(any())).thenReturn(recipeCommand);

        RecipeCommand commandById = recipeService.findCommandById(id).block();

        assertNotNull("Null recipe returned", commandById);
        verify(recipeRepository, times(1)).findById(any(UUID.class));
        verify(recipeRepository, never()).findAll();
    }

    @Test
    public void getRecipesTest() throws Exception {

        Recipe recipe = new Recipe();
        HashSet receipesData = new HashSet();
        receipesData.add(recipe);

        when(recipeService.getRecipes()).thenReturn(Flux.just(recipe));

        List<Recipe> recipes = recipeService.getRecipes().collectList().block();

        assertEquals(recipes.size(), 1);
        verify(recipeRepository, times(1)).findAll();
        verify(recipeRepository, never()).findById(any(UUID.class));
    }

    @Test
    public void testDeleteById() throws Exception {

        //given
        UUID idToDelete = UUID.randomUUID();
        when(recipeRepository.deleteById(any(UUID.class))).thenReturn(Mono.empty());

        //when
        recipeService.deleteById(idToDelete);
        //then
        verify(recipeRepository, times(1)).deleteById(any(UUID.class));
    }
}