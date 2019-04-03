package com.almeida.recipeapp.services;

import com.almeida.recipeapp.converters.RecipeCommandToRecipe;
import com.almeida.recipeapp.converters.RecipeToRecipeCommand;
import com.almeida.recipeapp.domain.Recipe;
import com.almeida.recipeapp.repositories.RecipeRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class RecipeServiceImplTest {

    RecipeServiceImpl recipeService;

    @Mock
    RecipeRepository recipeRepository;

    @Mock
    RecipeToRecipeCommand recipeToRecipeCommand;

    @Mock
    RecipeCommandToRecipe recipeCommandToRecipe;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        recipeService = new RecipeServiceImpl(recipeRepository, recipeCommandToRecipe, recipeToRecipeCommand);
    }

    @Test
    public void getRecipeByIdTest() throws Exception {
        Recipe recipe = new Recipe();
        Optional<Recipe> recipeOptional = Optional.of(recipe);

        Mockito.when(recipeRepository.findById(Mockito.any(UUID.class))).thenReturn(recipeOptional);

        Recipe recipeReturned = recipeService.findById(recipe.getId());

        assertNotNull("Null recipe returned", recipeReturned);
        Mockito.verify(recipeRepository, Mockito.times(1)).findById(Mockito.any(UUID.class));
        Mockito.verify(recipeRepository, Mockito.never()).findAll();
    }

    @Test
    public void getRecipesTest() throws Exception {
        Recipe recipe = new Recipe();
        HashSet recipeData = new HashSet();
        recipeData.add(recipe);

        Mockito.when(recipeRepository.findAll()).thenReturn(recipeData);

        Set<Recipe>  recipes = recipeService.getRecipes();

        assertEquals(recipes.size(), 1);
        Mockito.verify(recipeRepository, Mockito.times(1)).findAll();
    }

    @Test
    public void deleteById() throws Exception {
        // given
        UUID id = UUID.randomUUID();
        // then
        recipeService.deleteById(id);
        // when
        Mockito.verify(recipeRepository, Mockito.times(1)).deleteById(Mockito.any(UUID.class));
    }
}