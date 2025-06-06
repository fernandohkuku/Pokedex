package com.fernando.pokedex.data.remote.source

import com.fernando.pokedex.data.models.PokemonDto
import com.fernando.pokedex.data.remote.api.PokemonService
import com.fernando.pokedex.data.repositories.PokemonRemoteDataSource
import javax.inject.Inject
import kotlin.random.Random

class PokemonRemoteDataSourceImpl @Inject constructor(
    private val pokemonService: PokemonService
) : PokemonRemoteDataSource {

    override suspend fun getPokemon(): PokemonDto {
        val randomPokemonId = Random.nextInt(1, 1000)
        return pokemonService.getPokemonById(randomPokemonId)
    }
}