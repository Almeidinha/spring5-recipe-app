package com.almeida.recipeapp.services;

import com.almeida.recipeapp.commands.IngredientCommand;
import com.almeida.recipeapp.commands.UnitOfMeasureCommand;
import com.almeida.recipeapp.converters.IngredientCommandToIngredient;
import com.almeida.recipeapp.converters.IngredientToIngredientCommand;
import com.almeida.recipeapp.converters.UnitOfMeasureCommandToUnitOfMeasure;
import com.almeida.recipeapp.converters.UnitOfMeasureToUnitOfMeasureCommand;
import com.almeida.recipeapp.domain.Ingredient;
import com.almeida.recipeapp.domain.Recipe;
import com.almeida.recipeapp.repositories.reactive.RecipeReactiveRepository;
import com.almeida.recipeapp.repositories.reactive.UnitOfMeasureReactiveRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.verify;

public class IngredientServiceImplTest {

    private final IngredientToIngredientCommand ingredientToIngredientCommand;
    private final IngredientCommandToIngredient ingredientCommandToIngredient;

    @Mock
    RecipeReactiveRepository recipeReactiveRepository;

    @Mock
    UnitOfMeasureReactiveRepository unitOfMeasureRepository;

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
                recipeReactiveRepository, unitOfMeasureRepository);
    }

    @Test
    public void findByRecipeIdAndId() throws Exception {
    }

    @Test
    public void findByRecipeIdAndReceipeIdHappyPath() throws Exception {
        //given
        Recipe recipe = new Recipe();
        recipe.setId(UUID.randomUUID());

        Ingredient ingredient1 = new Ingredient();
        ingredient1.setId(UUID.randomUUID());

        Ingredient ingredient2 = new Ingredient();
        ingredient2.setId(UUID.randomUUID());

        Ingredient ingredient3 = new Ingredient();
        ingredient3.setId(UUID.randomUUID());

        recipe.addIngredient(ingredient1);
        recipe.addIngredient(ingredient2);
        recipe.addIngredient(ingredient3);
        Optional<Recipe> recipeOptional = Optional.of(recipe);

        when(recipeReactiveRepository.findById(any(UUID.class))).thenReturn(Mono.just(recipe));

        //then
        IngredientCommand ingredientCommand = ingredientService.findByRecipeIdAndIngredientId(recipe.getId(), ingredient3.getId()).block();

        //when
        assertEquals(ingredient3.getId(), ingredientCommand.getId());
        verify(recipeReactiveRepository, times(1)).findById(any(UUID.class));
    }


    @Test
    public void testSaveRecipeCommand() throws Exception {
        UUID ingrediendId = UUID.randomUUID();
        //given
        IngredientCommand command = new IngredientCommand();
        command.setId(ingrediendId);
        command.setRecipeId(UUID.randomUUID());
        command.setUnitOfMeasure(new UnitOfMeasureCommand());
        command.getUnitOfMeasure().setId(UUID.randomUUID());

        Recipe savedRecipe = new Recipe();
        savedRecipe.addIngredient(new Ingredient());
        savedRecipe.getIngredients().iterator().next().setId(UUID.randomUUID());

        when(recipeReactiveRepository.findById(any(UUID.class))).thenReturn(Mono.just(new Recipe()));
        when(recipeReactiveRepository.save(any())).thenReturn(Mono.just(savedRecipe));

        //when
        IngredientCommand savedCommand = ingredientService.saveIngredientCommand(command).block();

        //then
        assertEquals(ingrediendId, savedCommand.getId());
        verify(recipeReactiveRepository, times(1)).findById(any(UUID.class));
        verify(recipeReactiveRepository, times(1)).save(any(Recipe.class));

    }

    @Test
    public void testDeleteById() throws Exception {
        UUID recipeId = UUID.randomUUID();
        UUID ingredientId = UUID.randomUUID();

        //given
        Recipe recipe = new Recipe();
        recipe.setId(recipeId);
        Ingredient ingredient = new Ingredient();
        ingredient.setId(ingredientId);
        recipe.addIngredient(ingredient);
        Optional<Recipe> recipeOptional = Optional.of(recipe);

        when(recipeReactiveRepository.findById(any(UUID.class))).thenReturn(Mono.just(recipe));
        when(recipeReactiveRepository.save(any())).thenReturn(Mono.just(recipe));

        //when
        ingredientService.deleteById(recipeId, ingredientId);

        //then
        verify(recipeReactiveRepository, times(1)).findById(any(UUID.class));
        verify(recipeReactiveRepository, times(1)).save(any(Recipe.class));
    }

}
