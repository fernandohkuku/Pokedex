package com.fernando.pokedex.data.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class SpritesDto(
    @field:Json(name = "front_default")
    val frontDefault: String,
    @field:Json(name = "front_shiny")
    val frontShiny: String
)