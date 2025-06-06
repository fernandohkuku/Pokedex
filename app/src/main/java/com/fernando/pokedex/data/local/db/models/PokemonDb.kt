package com.fernando.pokedex.data.local.db.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class PokemonDb(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String,
    val spritesDb: SpritesDb,
    val typeDbs: List<TypeDb>
)