package com.jujodevs.pokelist.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.jujodevs.pokelist.data.local.model.pokemon.LocalPokemon
import kotlinx.coroutines.flow.Flow

@Dao
interface PokemonDao {

    @Query("SELECT * FROM LocalPokemon ORDER BY `order`")
    fun getPokemonList(): Flow<List<LocalPokemon>>

    @Insert
    suspend fun insert(pokemon: LocalPokemon)

    @Update
    suspend fun updatePokemon(pokemon: LocalPokemon)

    @Query("SELECT EXISTS(SELECT * FROM LocalPokemon WHERE id = :id)")
    suspend fun exists(id: Int): Boolean
}