package com.jujodevs.pokelist.pokemon


import com.google.gson.annotations.SerializedName

data class PokemonResponse constructor(
    @SerializedName("abilities")
    val abilities: List<Ability>,
    @SerializedName("height")
    val height: Int,
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("order")
    val order: Int,
    @SerializedName("sprites")
    val sprites: Sprites,
    @SerializedName("types")
    val types: List<Type>,
    @SerializedName("weight")
    val weight: Int,
    val favorite: Boolean = false
)