package com.almeida.recipeapp.services;

import com.almeida.recipeapp.domain.Recipe;
import com.almeida.recipeapp.repositories.RecipeRepository;
import com.almeida.recipeapp.repositories.reactive.RecipeReactiveRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.util.UUID;

@Slf4j
@Service
public class ImageServiceImpl implements ImageService {


    private final RecipeReactiveRepository recipeRepository;

    public ImageServiceImpl( RecipeReactiveRepository recipeService) {
        this.recipeRepository = recipeService;
    }

    @Override
    public Mono<Void> saveImageFile(UUID recipeId, MultipartFile file) {
        Mono<Recipe> recipeMono = recipeRepository.findById(recipeId)
                .map(recipe -> {
                    Byte[] bytes = new Byte[0];
                    try {
                        bytes = new Byte[file.getBytes().length];

                        int i = 0;
                        for (byte b : file.getBytes()) {
                            bytes[i++] = b;
                        }

                        recipe.setImage(bytes);
                        return  recipe;
                    } catch (IOException e) {
                        e.printStackTrace();
                        throw new RuntimeException(e);
                    }
                });

        recipeRepository.save(recipeMono.block()).block();
        return Mono.empty();
    }
}
