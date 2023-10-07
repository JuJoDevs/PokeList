package com.jujodevs.pokelist.data.local

import com.jujodevs.pokelist.data.local.dao.PokemonDao
import com.jujodevs.pokelist.domain.model.Pokemon
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class LocalDataSource(private val dao: PokemonDao) {
    val pokemons: Flow<List<Pokemon>> =
        dao.getPokemonList().map { pokemons -> pokemons.map { it.toPokemon() } }

    suspend fun insert(pokemon: Pokemon) {
        dao.insert(pokemon.toLocalPokemon())
    }

    suspend fun updatePokemon(pokemon: Pokemon) {
        dao.updatePokemon(pokemon.toLocalPokemon())
    }

    suspend fun exists(id: Int) = dao.exists(id)
}