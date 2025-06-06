package com.fernando.pokedex.data.local.source

import com.fernando.pokedex.data.local.db.dao.PokemonDao
import com.fernando.pokedex.data.local.db.models.PokemonDb
import com.fernando.pokedex.data.repositories.PokemonLocalDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject

class PokemonLocalDataSourceImpl @Inject constructor(
    private val pokemonDao: PokemonDao
): PokemonLocalDataSource {
    override suspend fun savePokemonInPokedex(pokemon: PokemonDb) {
        pokemonDao.savePokemonInPokedex(pokemon)
    }

    override fun getPokemons(): Flow<List<PokemonDb>> {
        return pokemonDao.getPokemons()
    }
}