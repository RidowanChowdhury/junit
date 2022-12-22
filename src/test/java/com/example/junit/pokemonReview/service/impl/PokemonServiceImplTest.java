package com.example.junit.pokemonReview.service.impl;

import com.example.junit.pokemonReview.dto.PokemonDto;
import com.example.junit.pokemonReview.dto.PokemonResponse;
import com.example.junit.pokemonReview.models.Pokemon;
import com.example.junit.pokemonReview.repository.PokemonRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PokemonServiceImplTest {

    @Mock
    PokemonRepository pokemonRepository;

    @InjectMocks
    PokemonServiceImpl pokemonService;

    @Test
    public void PokemonService_CreatePokemon_ReturnsPokemonDto() {
        Pokemon pokemon = Pokemon.builder()
                .name("pikachu")
                .type("electric").build();
        PokemonDto pokemonDto = PokemonDto.builder()
                .name("pikachu")
                .type("electric").build();
        when(pokemonRepository.save(Mockito.any(Pokemon.class)))
                .thenReturn(pokemon);
        PokemonDto savedPokemon = pokemonService.createPokemon(pokemonDto);
        assertThat(savedPokemon).isNotNull();
        assertThat(savedPokemon).isEqualTo(pokemonDto);
    }

    @Test
    public void PokemonService_GetAllPokemon_ReturnsResponseDto() {
        Page<Pokemon> pokemons = Mockito.mock(Page.class);
        when(pokemonRepository.findAll(Mockito.any(Pageable.class))).thenReturn(pokemons);
        PokemonResponse pokemonResponse = pokemonService.getAllPokemon(10,10);
        assertThat(pokemonResponse).isNotNull();
    }
    @Test
    public void PokemonService_FindById_ReturnPokemonDto() {
        int pokemonId = 1;
        Pokemon pokemon = Pokemon.builder().id(1).name("pikachu").type("electric").type("this is a type").build();
        when(pokemonRepository.findById(pokemonId)).thenReturn(Optional.ofNullable(pokemon));

        PokemonDto pokemonReturn = pokemonService.getPokemonById(pokemonId);

        assertThat(pokemonReturn).isNotNull();
    }

    @Test
    public void PokemonService_UpdatePokemon_ReturnPokemonDto() {
        int pokemonId = 1;
        Pokemon pokemon = Pokemon.builder().id(1).name("pikachu").type("electric").type("this is a type").build();
        PokemonDto pokemonDto = PokemonDto.builder().id(1).name("pikachu").type("electric").type("this is a type").build();

        when(pokemonRepository.findById(pokemonId)).thenReturn(Optional.ofNullable(pokemon));
        when(pokemonRepository.save(pokemon)).thenReturn(pokemon);

        PokemonDto updateReturn = pokemonService.updatePokemon(pokemonDto, pokemonId);

        assertThat(updateReturn).isNotNull();
    }

    @Test
    public void PokemonService_DeletePokemonById_ReturnVoid() {
        int pokemonId = 1;
        Pokemon pokemon = Pokemon.builder().id(1).name("pikachu").type("electric").type("this is a type").build();

        when(pokemonRepository.findById(pokemonId)).thenReturn(Optional.ofNullable(pokemon));
        doNothing().when(pokemonRepository).delete(pokemon);

        assertAll(() -> pokemonService.deletePokemonId(pokemonId));
    }

}