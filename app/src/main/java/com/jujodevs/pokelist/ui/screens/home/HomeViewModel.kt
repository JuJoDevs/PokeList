package com.jujodevs.pokelist.ui.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jujodevs.pokelist.data.local.PokemonDao
import com.jujodevs.pokelist.data.remote.PokeService
import com.jujodevs.pokelist.data.remote.pokemon.PokemonResponse
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.Locale

class HomeViewModel(val dao: PokemonDao) : ViewModel() {
    private val service: PokeService =
        Retrofit.Builder()
            .baseUrl("https://pokeapi.co/api/v2/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(PokeService::class.java)

    private val _state = MutableStateFlow(UiState())

    val state: StateFlow<UiState> = _state
    init {
        viewModelScope.launch {
            val isDbEmpty = dao.count() == 0
            if (isDbEmpty) {

            }

            _state.value = _state.value.copy(loading = true)
            val pokemons = service.getPokemonList().results

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
                        _state.value = _state.value.copy(pokemons = _state.value.pokemons.plus(pokeOrder))
                    }
                })
            }
            awaitAll(*deferreds.toTypedArray())
            _state.value = _state.value.copy(pokemons = _state.value.pokemons.sortedBy { it.order })
            _state.value = _state.value.copy(loading = false)
        }
    }

    data class UiState(
        val loading: Boolean = false,
        val pokemons: List<PokemonResponse> = emptyList()
    )

    fun onMoviewClick(pokemon: PokemonResponse) {
        _state.value = with(_state.value){
            copy(pokemons = pokemons.map { if (pokemon.id == it.id) it.copy(favorite = !it.favorite) else it } )
        }
    }
}
