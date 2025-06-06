package com.fernando.pokedex.domain.entities

data class PokemonEntity(
    val id:Int = 0,
    val name: String? = null,
    val spritesEntity: SpritesEntity? = null,
    val typeEntities: List<TypeEntity>? = null
){
    val imageUrl: String?
        get() = spritesEntity?.frontDefault

    val type: String?
        get() = typeEntities?.firstOrNull()?.type?.name ?: "Unknown"
}