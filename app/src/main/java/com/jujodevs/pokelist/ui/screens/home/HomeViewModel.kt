package com.jujodevs.pokelist.ui.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jujodevs.pokelist.data.PokemonRepository
import com.jujodevs.pokelist.domain.model.Pokemon
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class HomeViewModel(private val repository: PokemonRepository) : ViewModel() {


    private val _state = MutableStateFlow(UiState())

    val state: StateFlow<UiState> = _state
    init {
        viewModelScope.launch {
            repository.requestPokemons()
        }
        viewModelScope.launch {
            repository.downloadInProgress.collect{
                _state.value = _state.value.copy(loading = it)
            }
        }
        viewModelScope.launch {
            repository.pokemons.collect{
                _state.value = _state.value.copy(pokemons = it)
            }
        }
    }

    data class UiState(
        val loading: Boolean = false,
        val pokemons: List<Pokemon> = emptyList()
    )

    fun onMoviewClick(pokemon: Pokemon) {
        viewModelScope.launch {
            repository.updatePokemon(pokemon.copy(favorite = !pokemon.favorite))
        }
    }
}
