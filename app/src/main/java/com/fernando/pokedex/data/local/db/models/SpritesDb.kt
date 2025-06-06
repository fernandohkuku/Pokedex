package com.fernando.pokedex.data.local.db.models

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class SpritesDb(
    val frontDefault: String,
    val frontShiny: String
)