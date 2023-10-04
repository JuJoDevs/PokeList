package com.jujodevs.pokelist

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.produceState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.imageLoader
import coil.request.ImageRequest
import com.jujodevs.pokelist.pokemon.PokemonResponse
import com.jujodevs.pokelist.ui.theme.PokeListTheme
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.Locale

class MainActivity : ComponentActivity() {

    private val service: PokeService =
        Retrofit.Builder()
            .baseUrl("https://pokeapi.co/api/v2/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(PokeService::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PokeListTheme {
                val context = LocalContext.current
                val pokemons = produceState<List<PokemonResponse>>(initialValue = emptyList()) {
                    val pokemons = service.getPokemonList().results
                    value = emptyList()

                    var deferreds: List<Deferred<Unit>> = emptyList()
                    pokemons.forEach { pokemonUrl ->
                        deferreds = deferreds.plus(async {
                            val pokemon = service.getPokemon(pokemonUrl.getId())
                            if (pokemon.sprites.frontDefault != null) {
                                val pokeOrder = pokemon.copy(
                                    order = if (pokemon.order <= 0) 100000 else pokemon.order,
                                    name = pokemon.name.replace("-", " ").replaceFirstChar {
                                        if (it.isLowerCase()) it.titlecase(
                                            Locale.getDefault()
                                        ) else it.toString()
                                    }
                                )
                                value = value.plus(pokeOrder)
                            }
                        })
                    }
                    awaitAll(*deferreds.toTypedArray())
                    value = value.sortedBy { it.order }
                    value.forEach { pokemon ->
                        val request = ImageRequest.Builder(context)
                            .data(pokemon.sprites.frontDefault).build()
                        context.imageLoader.enqueue(request)
                    }
                }

                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    BodyPokeList(pokemons.value)
                }
            }
        }
    }
}

@Composable
fun BodyPokeList(pokemons: List<PokemonResponse>) {

    LazyVerticalGrid(columns = GridCells.Adaptive(120.dp)){
        items(pokemons) { pokemon ->
            PokeItem(pokemon)
        }
    }
}

@Composable
fun PokeItem(pokemon: PokemonResponse) {
    Card(
        elevation = CardDefaults.cardElevation(defaultElevation = 16.dp),
        modifier = Modifier.padding(8.dp)
    ) {

        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
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
            Column(Modifier.height(54.dp), verticalArrangement = Arrangement.Center) {
                Text(text = pokemon.name, fontWeight = FontWeight.Bold, maxLines = 2, textAlign = TextAlign.Center)
            }
        }
    }

}
