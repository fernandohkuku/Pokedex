package com.fernando.pokedex.domain.repositories

import com.fernando.pokedex.domain.entities.PokemonEntity
import kotlinx.coroutines.flow.Flow

interface PokemonRepository {
    fun getPokemons(): Flow<List<PokemonEntity>>
    suspend fun detectPokedexEntry(): PokemonEntity
}