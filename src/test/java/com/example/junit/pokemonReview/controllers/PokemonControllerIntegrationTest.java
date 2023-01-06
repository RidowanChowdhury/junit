package com.example.junit.pokemonReview.controllers;

import com.example.junit.pokemonReview.dto.PokemonDto;
import com.example.junit.pokemonReview.service.PokemonService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;

import static org.hamcrest.Matchers.is;
/*
Integration testing with WebTestClient
 */
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class PokemonControllerIntegrationTest {

    @Autowired
    WebTestClient webTestClient;

    @Autowired
    PokemonService pokemonService;

    @BeforeEach
    void init() {
        pokemonService.createPokemon(PokemonDto.builder()
                .name("pikachu")
                .type("electric")
                .id(1).build());
    }

    @Test
    void getPokemons() {
        webTestClient.get().uri("/api/pokemon")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.pageNo").value(is(0))
                .jsonPath("$.content[0].name").value(is("pikachu"))
                .returnResult()
        ;


    }


    @Test
    void pokemonDetail() {
        webTestClient.get().uri("/api/pokemon/1")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectBody()
                .jsonPath("$.name").value(is("pikachu"))
                .jsonPath("$.type").value(is("electric"));
    }

    @Test
    void createPokemon() {
        webTestClient.post().uri("/api/pokemon/create")
                .bodyValue(PokemonDto.builder().id(2).name("John").type("electric").build())
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectBody()
                .jsonPath("$.name").value(is("John"));
    }

}