package com.jujodevs.pokelist


import com.google.gson.annotations.SerializedName

data class PokemonUrlResult(
    @SerializedName("count")
    val count: Int,
    @SerializedName("next")
    val next: Any,
    @SerializedName("previous")
    val previous: Any,
    @SerializedName("results")
    val results: List<PokemonUrl>
)