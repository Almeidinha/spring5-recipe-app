package com.almeida.recipeapp.services;

import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

public interface ImageService {

    void saveImageFile(UUID recipeId, MultipartFile file);
}
