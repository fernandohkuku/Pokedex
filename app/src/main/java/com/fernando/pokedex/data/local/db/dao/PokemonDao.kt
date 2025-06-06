package com.fernando.pokedex.data.local.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.fernando.pokedex.data.local.db.models.PokemonDb
import kotlinx.coroutines.flow.Flow

@Dao
interface PokemonDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun savePokemonInPokedex(pokemon: PokemonDb)

    @Query("SELECT * FROM PokemonDb")
    fun getPokemons(): Flow<List<PokemonDb>>
}