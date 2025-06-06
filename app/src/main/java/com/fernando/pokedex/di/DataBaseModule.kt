package com.fernando.pokedex.di

import android.content.Context
import androidx.room.Room
import androidx.room.TypeConverter
import com.fernando.pokedex.data.local.db.PokemonDatabase
import com.fernando.pokedex.data.local.db.converter.PokemonConverter
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.internal.Contexts
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DataBaseModule {

    @Provides
    @Singleton
    fun providePokemonDatabase(
        @ApplicationContext context: Context,
        pokemonConverter: PokemonConverter
    ): PokemonDatabase =
        Room.databaseBuilder(
            context,
            PokemonDatabase::class.java,
            "pokemon_database"
        ).addTypeConverter(pokemonConverter).build()

    @Provides
    @Singleton
    fun providePokemonDao(
        database: PokemonDatabase
    ) = database.pokemonDao()
}