package com.jujodevs.pokelist

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.room.Room
import com.jujodevs.pokelist.data.local.PokemonDatabase
import com.jujodevs.pokelist.ui.screens.home.Home

class MainActivity : ComponentActivity() {

    private lateinit var db : PokemonDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        db = Room.databaseBuilder(applicationContext, PokemonDatabase::class.java, "pokelistdb").build()
        setContent {
            Home(db.pokemonDao())
        }
    }


}
