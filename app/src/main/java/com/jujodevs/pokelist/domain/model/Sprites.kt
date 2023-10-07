package com.jujodevs.pokelist.domain.model

import com.jujodevs.pokelist.data.local.model.pokemon.LocalSprites

data class Sprites (
    val backDefault: String?,
    val backFemale: String?,
    val backShiny: String?,
    val backShinyFemale: String?,
    val frontDefault: String?,
    val frontFemale: String?,
    val frontShiny: String?,
    val frontShinyFemale: String?
){
    fun toLocalSprites() = LocalSprites(backDefault, backFemale, backShiny, backShinyFemale, frontDefault, frontFemale, frontShiny, frontShinyFemale)
}