package com.almeida.recipeapp.services;

import com.almeida.recipeapp.domain.Recipe;
import com.almeida.recipeapp.repositories.RecipeRepository;
import com.almeida.recipeapp.repositories.reactive.RecipeReactiveRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;
import reactor.core.publisher.Mono;

import java.util.Optional;
import java.util.UUID;

import static org.junit.Assert.assertEquals;

public class ImageServiceImplTest {

    @Mock
    RecipeReactiveRepository recipeRepository;

    ImageService imageService;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        imageService = new ImageServiceImpl(recipeRepository);
    }

    @Test
    public void saveImageFile() throws Exception {
        //given
        UUID id = UUID.randomUUID();
        MultipartFile multipartFile = new MockMultipartFile("imagefile", "testing.txt", "text/plain",
                "Spring Framework Guru".getBytes());

        Recipe recipe = new Recipe();
        recipe.setId(id);

        Mockito.when(recipeRepository.findById(Mockito.any(UUID.class))).thenReturn(Mono.just(recipe));
        Mockito.when(recipeRepository.save(Mockito.any(Recipe.class))).thenReturn(Mono.just(recipe));
        ArgumentCaptor<Recipe> argumentCaptor = ArgumentCaptor.forClass(Recipe.class);

        //when
        imageService.saveImageFile(id, multipartFile);

        //then
        Mockito.verify(recipeRepository, Mockito.times(1)).save(argumentCaptor.capture());
        Recipe savedRecipe = argumentCaptor.getValue();
        assertEquals(multipartFile.getBytes().length, savedRecipe.getImage().length);
    }


}