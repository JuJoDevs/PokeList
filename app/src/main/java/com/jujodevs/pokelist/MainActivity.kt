package com.jujodevs.pokelist

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.room.Room
import coil.ImageLoader
import coil.ImageLoaderFactory
import coil.disk.DiskCache
import coil.request.CachePolicy
import com.jujodevs.pokelist.data.PokemonRepository
import com.jujodevs.pokelist.data.local.LocalDataSource
import com.jujodevs.pokelist.data.local.PokemonDatabase
import com.jujodevs.pokelist.data.remote.RemoteDataSource
import com.jujodevs.pokelist.ui.screens.home.Home

class MainActivity : ComponentActivity(), ImageLoaderFactory {

    private lateinit var db : PokemonDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        db = Room.databaseBuilder(applicationContext, PokemonDatabase::class.java, "pokelistdb").build()

        val repository = PokemonRepository(LocalDataSource(db.pokemonDao()), RemoteDataSource())

        setContent {
            Home(repository)
        }
    }

    override fun newImageLoader(): ImageLoader = ImageLoader.Builder(applicationContext)
        .diskCache {
            DiskCache.Builder()
                .directory(applicationContext.cacheDir.resolve("image_cache"))
                .build()
        }
        .networkCachePolicy(CachePolicy.ENABLED)
        .diskCachePolicy(CachePolicy.ENABLED)
        .memoryCachePolicy(CachePolicy.ENABLED)
        .respectCacheHeaders(true)
        .build()


}
