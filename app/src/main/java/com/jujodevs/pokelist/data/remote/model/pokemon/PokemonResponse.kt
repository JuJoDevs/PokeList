package com.jujodevs.pokelist.data.remote.model.pokemon


import com.google.gson.annotations.SerializedName
import com.jujodevs.pokelist.domain.model.Pokemon

data class PokemonResponse constructor(
    @SerializedName("abilities")
    val abilities: List<AbilityResponse>,
    @SerializedName("height")
    val height: Int,
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("order")
    val order: Int,
    @SerializedName("sprites")
    val sprites: SpritesResponse,
    @SerializedName("types")
    val types: List<TypeResponse>,
    @SerializedName("weight")
    val weight: Int,
    val favorite: Boolean = false
) {
    fun toPokemon() = Pokemon(
        abilities.map { it.toAbility() },
        height,
        id,
        name,
        order,
        sprites.toSprites(),
        types.map { it.toType() },
        weight,
        favorite
    )
}