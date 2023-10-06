package com.jujodevs.pokelist.ui.screens.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import coil.imageLoader
import coil.request.ImageRequest
import com.jujodevs.pokelist.data.local.PokemonDao
import com.jujodevs.pokelist.data.remote.pokemon.PokemonResponse
import com.jujodevs.pokelist.ui.theme.PokeListTheme
import java.util.Locale

@Composable
fun Home(pokemonDao: PokemonDao) {
    PokeListTheme {

        val viewModel: HomeViewModel = viewModel { HomeViewModel(pokemonDao) }
        val state by viewModel.state.collectAsState()

        val context = LocalContext.current
        LaunchedEffect(state.loading) {
            state.pokemons.forEach { pokemon ->
                val request = ImageRequest.Builder(context)
                    .data(pokemon.sprites.frontDefault)
                    .build()
                context.imageLoader.enqueue(request)
            }
        }

        // A surface container using the 'background' color from the theme
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {

            Box(modifier = Modifier.fillMaxSize()) {
                BodyPokeList(viewModel, state.pokemons)
                if (state.loading) {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.TopEnd).padding(16.dp))
                }
            }
        }
    }
}

@Composable
fun BodyPokeList(viewModel: HomeViewModel, pokemons: List<PokemonResponse>) {

    LazyVerticalGrid(columns = GridCells.Adaptive(120.dp)) {
        items(pokemons) { pokemon ->
            PokeItem(pokemon) { viewModel.onMoviewClick(pokemon) }
        }
    }
}

@Composable
fun PokeItem(pokemon: PokemonResponse, onClick: () -> Unit) {
    Card(
        elevation = CardDefaults.cardElevation(defaultElevation = 16.dp),
        modifier = Modifier
            .padding(8.dp)
            .clickable(onClick = onClick),
    ) {

        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box {
                AsyncImage(
                    model = pokemon.sprites.frontDefault,
                    contentDescription = pokemon.name.replaceFirstChar {
                        if (it.isLowerCase()) it.titlecase(
                            Locale.getDefault()
                        ) else it.toString()
                    },
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxWidth()
                )
                if (pokemon.favorite) {
                    Icon(
                        imageVector = Icons.Default.Favorite,
                        contentDescription = pokemon.name,
                        modifier = Modifier
                            .align(Alignment.TopEnd)
                            .padding(8.dp)
                    )
                }
            }


            Column(Modifier.height(54.dp), verticalArrangement = Arrangement.Center) {
                Text(
                    text = pokemon.name,
                    fontWeight = FontWeight.Bold,
                    maxLines = 2,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}