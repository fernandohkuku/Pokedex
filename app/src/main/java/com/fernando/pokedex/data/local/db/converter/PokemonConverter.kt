package com.fernando.pokedex.data.local.db.converter

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.fernando.pokedex.data.local.db.models.SpritesDb
import com.fernando.pokedex.data.local.db.models.TypeDb
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import javax.inject.Inject

@ProvidedTypeConverter
class PokemonConverter @Inject constructor(
    private val moshi: Moshi
) {

    private val spritesAdapter = moshi.adapter(SpritesDb::class.java)

    private val typeParameter = Types.newParameterizedType(List::class.java, TypeDb::class.java)

    private val typesAdapter = moshi.adapter<List<TypeDb>>(typeParameter)


    @TypeConverter
    fun fromSpritesDb(spritesDb: SpritesDb): String {
        return spritesAdapter.toJson(spritesDb)
    }

    @TypeConverter
    fun toSpritesDb(spritesJson: String): SpritesDb? {
        return spritesAdapter.fromJson(spritesJson)
    }

    @TypeConverter
    fun fromList(value: List<TypeDb>): String {
        return typesAdapter.toJson(value)
    }

    @TypeConverter
    fun toList(value: String): List<TypeDb> {
        return typesAdapter.fromJson(value) ?: emptyList()
    }

}