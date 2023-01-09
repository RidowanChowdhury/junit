package com.example.junit.pokemonReview.controllers;

import com.example.junit.pokemonReview.dto.PokemonDto;
import com.example.junit.pokemonReview.dto.ReviewDto;
import com.example.junit.pokemonReview.models.Pokemon;
import com.example.junit.pokemonReview.models.Review;
import com.example.junit.pokemonReview.service.ReviewService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@WebMvcTest(controllers = ReviewController.class)
@AutoConfigureMockMvc(addFilters = false)
@ActiveProfiles("test")
class ReviewControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private ReviewService reviewService;
    @Autowired
    private ObjectMapper objectMapper;
    private Pokemon pokemon;
    private Review review;
    private ReviewDto reviewDto;
    private PokemonDto pokemonDto;

    @Value("${test.data}")
    String data;

    @BeforeEach
    public void init() {
        pokemon = Pokemon.builder().name("pikachu").type("electric").build();
        pokemonDto = PokemonDto.builder().name("pickachu").type("electric").build();
        review = Review.builder().title("title").content("content").stars(5).build();
        reviewDto = ReviewDto.builder().title("review title").content("test content").stars(5).build();
    }

    @Test
    void createReview() throws Exception {
        int pokemonId = 1;
        when(reviewService.createReview(pokemonId, reviewDto)).thenReturn(reviewDto);

        ResultActions response = mockMvc.perform(post("/api/pokemon/1/reviews")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(reviewDto)));

        response.andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.title", CoreMatchers.is(reviewDto.getTitle())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content", CoreMatchers.is(reviewDto.getContent())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.stars", CoreMatchers.is(reviewDto.getStars())));
    }

    @Test
    void getReviewsByPokemonId() throws Exception {
        int pokemonId = 1;
        when(reviewService.getReviewsByPokemonId(pokemonId)).thenReturn(Arrays.asList(reviewDto));

        ResultActions response = mockMvc.perform(get("/api/pokemon/1/reviews")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(pokemonDto)));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.size()", CoreMatchers.is(Arrays.asList(reviewDto).size())));

    }

    @Test
    void getReviewById() throws Exception {
        int pokemonId = 1;
        int reviewId = 1;
        when(reviewService.getReviewById(reviewId, pokemonId)).thenReturn(reviewDto);

        ResultActions response = mockMvc.perform(get("/api/pokemon/1/reviews/1")
                .contentType(MediaType.APPLICATION_JSON));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.title", CoreMatchers.is(reviewDto.getTitle())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content", CoreMatchers.is(reviewDto.getContent())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.stars", CoreMatchers.is(reviewDto.getStars())));
    }

    @Test
    void updateReview() throws Exception {
        int pokemonId = 1;
        int reviewId = 1;
        when(reviewService.updateReview(pokemonId, reviewId, reviewDto)).thenReturn(reviewDto);

        ResultActions response = mockMvc.perform(put("/api/pokemon/1/reviews/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(reviewDto)));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.title", CoreMatchers.is(reviewDto.getTitle())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content", CoreMatchers.is(reviewDto.getContent())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.stars", CoreMatchers.is(reviewDto.getStars())));
    }

    @Test
    void deleteReview() throws Exception {
        int pokemonId = 1;
        int reviewId = 1;

        doNothing().when(reviewService).deleteReview(pokemonId, reviewId);

        ResultActions response = mockMvc.perform(delete("/api/pokemon/1/reviews/1")
                .contentType(MediaType.APPLICATION_JSON));

        response.andExpect(MockMvcResultMatchers.status().isOk());
    }
}