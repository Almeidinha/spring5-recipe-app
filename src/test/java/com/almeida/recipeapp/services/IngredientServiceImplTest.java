package com.almeida.recipeapp.services;

import com.almeida.recipeapp.commands.IngredientCommand;
import com.almeida.recipeapp.converters.IngredientCommandToIngredient;
import com.almeida.recipeapp.converters.IngredientToIngredientCommand;
import com.almeida.recipeapp.converters.UnitOfMeasureCommandToUnitOfMeasure;
import com.almeida.recipeapp.converters.UnitOfMeasureToUnitOfMeasureCommand;
import com.almeida.recipeapp.domain.Ingredient;
import com.almeida.recipeapp.domain.Recipe;
import com.almeida.recipeapp.repositories.RecipeRepository;
import com.almeida.recipeapp.repositories.UnitOfMeasureRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.Optional;
import java.util.UUID;

import static org.junit.Assert.assertEquals;

public class IngredientServiceImplTest {

    private final IngredientToIngredientCommand ingredientToIngredientCommand;
    private final IngredientCommandToIngredient ingredientCommandToIngredient;

    @Mock
    RecipeRepository recipeRepository;

    @Mock
    UnitOfMeasureRepository unitOfMeasureRepository;

    IngredientService ingredientService;

    //init converters
    public IngredientServiceImplTest() {
        this.ingredientToIngredientCommand = new IngredientToIngredientCommand(new UnitOfMeasureToUnitOfMeasureCommand());
        this.ingredientCommandToIngredient = new IngredientCommandToIngredient(new UnitOfMeasureCommandToUnitOfMeasure());
    }

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        ingredientService = new IngredientServiceImpl(ingredientToIngredientCommand, ingredientCommandToIngredient,
                recipeRepository, unitOfMeasureRepository);
    }

    @Test
    public void findByRecipeIdAndId() throws Exception {
    }

    @Test
    public void findByRecipeIdAndReceipeIdHappyPath() throws Exception {
        //given
        Recipe recipe = new Recipe();
        recipe.setId(UUID.fromString("71ad0ed1-ddfc-4687-81bd-7db403f18727"));

        Ingredient ingredient1 = new Ingredient();
        ingredient1.setId(UUID.fromString("0bb1cb23-8891-44fd-8004-a8a726fa7d50"));

        Ingredient ingredient2 = new Ingredient();
        ingredient2.setId(UUID.fromString("1e1317be-6b79-4284-ae80-aa8ecee52a39"));

        Ingredient ingredient3 = new Ingredient();
        ingredient3.setId(UUID.fromString("e12fd1fb-99ac-4c2b-a1db-8de6be916f1e"));

        recipe.addIngredient(ingredient1);
        recipe.addIngredient(ingredient2);
        recipe.addIngredient(ingredient3);
        Optional<Recipe> recipeOptional = Optional.of(recipe);

        Mockito.when(recipeRepository.findById(Mockito.any(UUID.class))).thenReturn(recipeOptional);

        //then
        IngredientCommand ingredientCommand = ingredientService
                .findByRecipeIdAndIngredientId(UUID.fromString("71ad0ed1-ddfc-4687-81bd-7db403f18727"), UUID.fromString("e12fd1fb-99ac-4c2b-a1db-8de6be916f1e"));

        //when
        assertEquals(UUID.fromString("e12fd1fb-99ac-4c2b-a1db-8de6be916f1e"), ingredientCommand.getId());
        assertEquals(UUID.fromString("71ad0ed1-ddfc-4687-81bd-7db403f18727"), ingredientCommand.getRecipeId());
        Mockito.verify(recipeRepository, Mockito.times(1)).findById(Mockito.any(UUID.class));
    }

    @Test
    public void testSaveRecipeCommand() throws Exception {
        //given
        IngredientCommand command = new IngredientCommand();
        command.setId(UUID.fromString("5a0364cf-211c-40c6-ae19-7d6110729238"));
        command.setRecipeId(UUID.fromString("803a7d3b-cb9d-4c67-84e2-b5b6f0a9acc2"));

        Optional<Recipe> recipeOptional = Optional.of(new Recipe());

        Recipe savedRecipe = new Recipe();
        savedRecipe.addIngredient(new Ingredient());
        savedRecipe.getIngredients().iterator().next().setId(UUID.fromString("5a0364cf-211c-40c6-ae19-7d6110729238"));

        Mockito.when(recipeRepository.findById(Mockito.any(UUID.class))).thenReturn(recipeOptional);
        Mockito.when(recipeRepository.save(Mockito.any())).thenReturn(savedRecipe);

        //when
        IngredientCommand savedCommand = ingredientService.saveIngredientCommand(command);

        //then
        assertEquals(UUID.fromString("5a0364cf-211c-40c6-ae19-7d6110729238"), savedCommand.getId());
        Mockito.verify(recipeRepository, Mockito.times(1)).findById(Mockito.any(UUID.class));
        Mockito.verify(recipeRepository, Mockito.times(1)).save(Mockito.any(Recipe.class));

    }

}
