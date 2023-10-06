package com.jujodevs.pokelist.data.remote


import com.google.gson.annotations.SerializedName

data class PokemonUrl(
    @SerializedName("name")
    val name: String,
    @SerializedName("url")
    val url: String
){
    fun getId() = url.replace("https://pokeapi.co/api/v2/pokemon/", "").replace("/", "").toIntOrNull() ?: 0
}