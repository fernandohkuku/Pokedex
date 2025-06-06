package com.fernando.pokedex.data.remote.api

import com.fernando.pokedex.data.models.PokemonDto
import retrofit2.http.GET
import retrofit2.http.Path

interface PokemonService {

    @GET("pokemon/{id}")
    suspend fun getPokemonById(@Path("id") pokemonId: Int): PokemonDto
}