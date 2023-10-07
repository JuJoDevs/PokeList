package com.jujodevs.pokelist.data

import com.jujodevs.pokelist.data.local.LocalDataSource
import com.jujodevs.pokelist.data.remote.RemoteDataSource
import com.jujodevs.pokelist.domain.model.Pokemon
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.withContext

class PokemonRepository(
    private val localDataSource: LocalDataSource,
    private val remoteDataSource: RemoteDataSource
) {

    val pokemons: Flow<List<Pokemon>> =
        localDataSource.pokemons

    private val _downloadInProgress = MutableStateFlow(true)
    val downloadInProgress: Flow<Boolean> = _downloadInProgress

    suspend fun updatePokemon(pokemon: Pokemon){
        localDataSource.updatePokemon(pokemon)
    }

    suspend fun requestPokemons(){
        withContext(Dispatchers.IO) {
            _downloadInProgress.value = true
            var deferreds: List<Deferred<Unit>> = emptyList()
            remoteDataSource.getPokemonList()?.onEach { pokemon ->
                deferreds = deferreds.plus(async {
                    if (!localDataSource.exists(pokemon.getId())) {
                        remoteDataSource.getPokemon(pokemon)?.let { localDataSource.insert(it) }
                    }
                })
            }

            deferreds.awaitAll()

            _downloadInProgress.value = false
        }
    }

}