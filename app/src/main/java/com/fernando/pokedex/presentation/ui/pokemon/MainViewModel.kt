package com.fernando.pokedex.presentation.ui.pokemon

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fernando.pokedex.domain.entities.PokemonEntity
import com.fernando.pokedex.domain.usecase.location.GetLocationUseCase
import com.fernando.pokedex.domain.usecase.pokemon.DetectPokedexEntryUseCase
import com.fernando.pokedex.domain.usecase.pokemon.GetPokemonsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val detectPokedexEntryUseCase: DetectPokedexEntryUseCase,
    private val getLocationUseCase: GetLocationUseCase,
    private val getPokemonsUseCase: GetPokemonsUseCase
) : ViewModel() {
    private val _pokemon = MutableStateFlow(PokemonEntity())
    val pokemon = _pokemon.asStateFlow()

    val location = getLocationUseCase(onFailure = ::onError)

    val pokemons = getPokemonsUseCase(onFailure = ::onError)

    fun detectPokedexEntry() = viewModelScope.launch {
        detectPokedexEntryUseCase().fold({ pokemon ->
            _pokemon.update { pokemon }
        }, ::onError)
    }

    fun clearPokemon() {
        _pokemon.update { PokemonEntity() }
    }

    private fun onError(exception: Exception) {
        Log.e("Error", exception.message.toString())
    }
}