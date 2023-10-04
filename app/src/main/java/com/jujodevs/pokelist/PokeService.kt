package com.jujodevs.pokelist

import com.jujodevs.pokelist.pokemon.PokemonResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PokeService {

    @GET("pokemon")
    suspend fun getPokemonList(@Query ("limit") limit: Int = 100000, @Query("offset") offset: Int = 0): PokemonUrlResult

    @GET("pokemon/{id}")
    suspend fun getPokemon(@Path("id") index: Int): PokemonResponse
}