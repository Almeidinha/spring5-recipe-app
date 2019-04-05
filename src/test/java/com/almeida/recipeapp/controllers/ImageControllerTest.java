package com.almeida.recipeapp.controllers;

import com.almeida.recipeapp.commands.RecipeCommand;
import com.almeida.recipeapp.services.ImageService;
import com.almeida.recipeapp.services.RecipeService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class ImageControllerTest {

    @Mock
    ImageService imageService;

    @Mock
    RecipeService recipeService;

    ImageController controller;

    MockMvc mockMvc;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        controller = new ImageController(imageService, recipeService);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    public void getImageForm() throws Exception {
        UUID id = UUID.randomUUID();
        //given
        RecipeCommand command = new RecipeCommand();
        command.setId(id);

        Mockito.when(recipeService.findCommandById(Mockito.any(UUID.class))).thenReturn(command);

        //when
        mockMvc.perform(get("/recipe/" + id + "/image"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("recipe"));

        Mockito.verify(recipeService, Mockito.times(1)).findCommandById(Mockito.any(UUID.class));

    }

    @Test
    public void handleImagePost() throws Exception {
        UUID id = UUID.randomUUID();

        MockMultipartFile multipartFile =
                new MockMultipartFile("imagefile", "testing.txt", "text/plain",
                        "Spring Framework".getBytes());

        mockMvc.perform(multipart("/recipe/" + id + "/image").file(multipartFile))
                .andExpect(status().is3xxRedirection())
                .andExpect(header().string("Location", "/recipe/show/" + id));

        Mockito.verify(imageService, Mockito.times(1))
                .saveImageFile(Mockito.any(UUID.class), Mockito.any());
    }

    @Test
    public void renderImageFromDB() throws Exception {
        UUID id = UUID.randomUUID();
        //given
        RecipeCommand command = new RecipeCommand();
        command.setId(id);

        String s = "fake image text";
        Byte[] bytesBoxed = new Byte[s.getBytes().length];

        int i = 0;

        for (byte primByte : s.getBytes()){
            bytesBoxed[i++] = primByte;
        }

        command.setImage(bytesBoxed);

        Mockito.when(recipeService.findCommandById(Mockito.any(UUID.class))).thenReturn(command);

        //when
        MockHttpServletResponse response = mockMvc.perform(get("/recipe/" + id + "/recipeimage"))
                .andExpect(status().isOk())
                .andReturn().getResponse();

        byte[] reponseBytes = response.getContentAsByteArray();

        assertEquals(s.getBytes().length, reponseBytes.length);
    }


}