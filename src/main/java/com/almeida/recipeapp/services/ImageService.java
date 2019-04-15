package com.almeida.recipeapp.services;

import org.springframework.web.multipart.MultipartFile;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface ImageService {

    Mono<Void> saveImageFile(UUID recipeId, MultipartFile file);
}
