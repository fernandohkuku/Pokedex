package com.fernando.pokedex.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.fernando.pokedex.data.local.db.converter.PokemonConverter
import com.fernando.pokedex.data.local.db.dao.PokemonDao
import com.fernando.pokedex.data.local.db.models.PokemonDb

@Database(
    entities = [PokemonDb::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(PokemonConverter::class)
abstract class PokemonDatabase : RoomDatabase() {
    abstract fun pokemonDao(): PokemonDao
}