package com.fernando.pokedex.presentation.ui.pokemon

import android.Manifest
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.fernando.pokedex.domain.entities.LocationEntity
import com.fernando.pokedex.ktx.vibratePhone
import com.fernando.pokedex.presentation.components.permission.PermissionRequester
import com.fernando.pokedex.ui.theme.PokedexTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private object Permission {
        const val LOCATION = Manifest.permission.ACCESS_FINE_LOCATION
    }

    private val locationPermissionRequester =
        PermissionRequester(this, Permission.LOCATION, onDenied = {
            Toast.makeText(this, "Location permission required", Toast.LENGTH_SHORT).show()
        })


    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        locationPermissionRequester.runWithPermission()
        setContent {

            val location by viewModel.location.collectAsStateWithLifecycle(initialValue = LocationEntity())

            val pokemons by viewModel.pokemons.collectAsStateWithLifecycle(initialValue = emptyList())

            val pokemon by viewModel.pokemon.collectAsStateWithLifecycle()

            var showDialog by remember { mutableStateOf(true) }

            PokedexTheme {

                LaunchedEffect(key1 = location) {
                    if (location.latitude != 0.0 && location.longitude != 0.0) {
                        viewModel.detectPokedexEntry()
                    }
                }

                Box(
                    modifier = Modifier
                        .systemBarsPadding()
                        .fillMaxSize()
                ) {
                    Column (
                        modifier = Modifier.fillMaxSize()
                    ){
                        Button(
                            modifier = Modifier.align(Alignment.CenterHorizontally),
                            onClick = { viewModel.detectPokedexEntry() }

                        ) {
                            Text(text = "Obtener Pokémon")
                        }
                        LazyVerticalGrid(
                            columns = GridCells.Fixed(3),
                            contentPadding = PaddingValues(8.dp),
                            verticalArrangement = Arrangement.spacedBy(8.dp),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            items(pokemons, key = { it.id }) { pokemon ->
                                PokemonCard(
                                    name = pokemon.name,
                                    type = pokemon.type,
                                    imageUrl = pokemon.imageUrl,
                                    modifier = Modifier
                                        .padding(8.dp)
                                        .wrapContentSize()
                                )
                            }
                        }
                    }
                }

                AnimatedVisibility(visible = pokemon.name != null) {
                    if (pokemon.name != null) {
                        LaunchedEffect(key1 = pokemon.name) {
                            vibratePhone()
                        }
                        InfoDialog(
                            imageUrl = pokemon.imageUrl,
                            title = pokemon.name,
                            message = pokemon.type,
                            onDismiss = {
                                viewModel.clearPokemon()
                            }
                        )
                    }
                }

            }
        }
    }
}
