package com.jujodevs.pokelist.data.remote

import android.util.Log
import com.jujodevs.pokelist.data.remote.model.PokemonUrl
import com.jujodevs.pokelist.domain.model.Pokemon
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.Locale

class RemoteDataSource {
    private val service: PokeService =
        Retrofit.Builder()
            .baseUrl("https://pokeapi.co/api/v2/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(PokeService::class.java)

    suspend fun getPokemonList() =
        try {
            service.getPokemonList().results
        } catch (e: Exception) {
            Log.e(this::class.java.simpleName, e.message.toString())
            null
        }

    suspend fun getPokemon(pokemonUrl: PokemonUrl): Pokemon? {
        try {
            service.getPokemon(pokemonUrl.getId()).also { pokemon ->
                if (pokemon.sprites.frontDefault != null) {
                    return pokemon.copy(
                        order = if (pokemon.order <= 0) 100000 else pokemon.order,
                        name = pokemon.name.replace("-", " ").replaceFirstChar {
                            if (it.isLowerCase()) it.titlecase(
                                Locale.getDefault()
                            ) else it.toString()
                        }
                    ).toPokemon()
                }
                return null
            }
        } catch (e: Exception) {
            Log.e(this::class.java.simpleName, e.message.toString())
            return null
        }
    }

}