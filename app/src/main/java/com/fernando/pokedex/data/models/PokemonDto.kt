package com.fernando.pokedex.data.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class PokemonDto(
    val name: String,
    @field:Json(name = "sprites")
    val spritesDto: SpritesDto,
    @field:Json(name = "types")
    val typeDto: List<TypeDto>
)