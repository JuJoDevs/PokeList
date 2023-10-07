package com.jujodevs.pokelist.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.jujodevs.pokelist.data.local.dao.PokemonDao
import com.jujodevs.pokelist.data.local.model.pokemon.LocalPokemon

@Database(
    entities = [LocalPokemon::class],
    version = 1
)
@TypeConverters(Converters::class)
abstract class PokemonDatabase : RoomDatabase() {
    abstract fun pokemonDao(): PokemonDao
}




