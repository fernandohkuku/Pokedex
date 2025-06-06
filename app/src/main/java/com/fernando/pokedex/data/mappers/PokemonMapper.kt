package com.fernando.pokedex.data.mappers

import com.fernando.pokedex.data.local.db.models.PokemonDb
import com.fernando.pokedex.data.local.db.models.SpritesDb
import com.fernando.pokedex.data.local.db.models.TypeDb
import com.fernando.pokedex.data.local.db.models.TypeXDb
import com.fernando.pokedex.data.models.PokemonDto
import com.fernando.pokedex.data.models.SpritesDto
import com.fernando.pokedex.data.models.TypeDto
import com.fernando.pokedex.data.models.TypeXDto
import com.fernando.pokedex.domain.entities.PokemonEntity
import com.fernando.pokedex.domain.entities.SpritesEntity
import com.fernando.pokedex.domain.entities.TypeEntity
import com.fernando.pokedex.domain.entities.TypeXEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

fun PokemonDto.toDb() = PokemonDb(
    name = name,
    spritesDb = spritesDto.toDb(),
    typeDbs = typeDto.map { it.toDb() }
)

fun SpritesDto.toDb() = SpritesDb(
    frontDefault = frontDefault,
    frontShiny = frontShiny
)

fun TypeDto.toDb() = TypeDb(
    slot = slot,
    type = type.toDb()
)

fun TypeXDto.toDb() = TypeXDb(
    name = name,
    url = url
)

fun PokemonDb.toEntity() = PokemonEntity(
    id = id,
    name = name,
    spritesEntity = spritesDb.toEntity(),
    typeEntities = typeDbs.map { it.toEntity() }
)

fun SpritesDb.toEntity() = SpritesEntity(
    frontDefault = frontDefault,
    frontShiny = frontShiny
)

fun TypeDb.toEntity() = TypeEntity(
    slot = slot,
    type = type.toEntity()
)

fun TypeXDb.toEntity() = TypeXEntity(
    name = name,
    url = url
)

fun Flow<List<PokemonDb>>.asEntityFlow(): Flow<List<PokemonEntity>> {
    return this.map { list -> list.map { it.toEntity() } }
}

fun PokemonDto.toEntity() = PokemonEntity(
    name = name,
    spritesEntity = spritesDto.toEntity(),
    typeEntities = typeDto.map { it.toEntity() }
)

fun SpritesDto.toEntity() = SpritesEntity(
    frontDefault = frontDefault,
    frontShiny = frontShiny
)

fun TypeDto.toEntity() = TypeEntity(
    slot = slot,
    type = type.toEntity()
)

fun TypeXDto.toEntity() = TypeXEntity(
    name = name,
    url = url
)