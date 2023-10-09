package com.jujodevs.pokelist.data

import com.jujodevs.pokelist.data.local.LocalDataSource
import com.jujodevs.pokelist.data.remote.RemoteDataSource
import com.jujodevs.pokelist.domain.model.Pokemon
import com.jujodevs.pokelist.motherobject.PokemonMotherObject
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.mockito.kotlin.any
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock
import org.mockito.kotlin.times
import org.mockito.kotlin.verifyBlocking

class PokemonRepositoryTest {

    @Test
    fun getPokemons() {
        val pokemon = mock<Pokemon>()
        val mPokemons = listOf(pokemon)
        val flowPokemons = flowOf(listOf(pokemon))
        var result: List<Pokemon>
        val localDataSource = mock<LocalDataSource>(){
            onBlocking { pokemons } doReturn flowPokemons
        }
        val remoteDataSource = mock<RemoteDataSource>()
        val repository = PokemonRepository(localDataSource, remoteDataSource)

        runBlocking { result = repository.pokemons.first() }

        assert(result == mPokemons)
    }

    @Test
    fun `When called requestPokemons then state _downloadInProgress change to false`() {
        val localDataSource = mock<LocalDataSource>()
        val remoteDataSource = mock<RemoteDataSource>()
        var downloadInProgress: Boolean
        val repository = PokemonRepository(localDataSource, remoteDataSource)

        runBlocking {
            repository.requestPokemons()
            downloadInProgress = repository.downloadInProgress.first()
        }

        assert(!downloadInProgress)
    }

    @Test
    fun `When called updatePokemon then update`() {
        val pokemon = mock<Pokemon>()
        val localDataSource = mock<LocalDataSource>()
        val remoteDataSource = mock<RemoteDataSource>()
        val repository = PokemonRepository(localDataSource, remoteDataSource)

        runBlocking { repository.updatePokemon(pokemon) }

        verifyBlocking(localDataSource, times(1)) { updatePokemon(pokemon) }
    }

    @Test
    fun `When pokemon no exists then insert`() {
        val pokemon = mock<Pokemon>()
        val localDataSource = mock<LocalDataSource>() {
            onBlocking { exists(1) } doReturn(true)
            onBlocking { exists(2) } doReturn(false)
            onBlocking { exists(3) } doReturn(false)
            onBlocking { exists(4) } doReturn(false)
        }
        val remoteDataSource = mock<RemoteDataSource>() {
            onBlocking { getPokemonList() } doReturn PokemonMotherObject.pokemonUrlList
            onBlocking { getPokemon(any()) } doReturn pokemon
        }
        val repository = PokemonRepository(localDataSource, remoteDataSource)

        runBlocking { repository.requestPokemons() }

        verifyBlocking(localDataSource, times(3)) { insert(any()) }
    }

    @Test
    fun `When all pokemons exist getPokemon is not called`() {
        val localDataSource = mock<LocalDataSource>() {
            onBlocking { exists(any()) } doReturn(true)
        }
        val remoteDataSource = mock<RemoteDataSource>() {
            onBlocking { getPokemonList() } doReturn PokemonMotherObject.pokemonUrlList
        }
        val repository = PokemonRepository(localDataSource, remoteDataSource)

        runBlocking { repository.requestPokemons() }

        verifyBlocking(remoteDataSource, times(0)) { getPokemon(any()) }
    }
}