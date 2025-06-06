package com.fernando.pokedex.data.repositories

import com.fernando.pokedex.data.local.db.models.PokemonDb
import com.fernando.pokedex.data.mappers.asEntityFlow
import com.fernando.pokedex.data.mappers.toDb
import com.fernando.pokedex.data.mappers.toEntity
import com.fernando.pokedex.data.models.PokemonDto
import com.fernando.pokedex.domain.entities.PokemonEntity
import com.fernando.pokedex.domain.repositories.PokemonRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

internal class PokemonRepositoryImpl @Inject constructor(
    private val remoteDataSource: PokemonRemoteDataSource,
    private val localDataSource: PokemonLocalDataSource
) : PokemonRepository {

    override fun getPokemons(): Flow<List<PokemonEntity>> =
        localDataSource.getPokemons().asEntityFlow()

    override suspend fun detectPokedexEntry(): PokemonEntity {
        val pokemon = remoteDataSource.getPokemon()
        localDataSource.savePokemonInPokedex(
            pokemon = pokemon.toDb()
        )
        return pokemon.toEntity()
    }
}


internal interface PokemonRemoteDataSource {
    suspend fun getPokemon(): PokemonDto
}

internal interface PokemonLocalDataSource {
    suspend fun savePokemonInPokedex(pokemon: PokemonDb)
    fun getPokemons(): Flow<List<PokemonDb>>
}