package com.jujodevs.pokelist.data.local.model.pokemon

import com.jujodevs.pokelist.domain.model.Sprites

data class LocalSprites (
    val backDefault: String?,
    val backFemale: String?,
    val backShiny: String?,
    val backShinyFemale: String?,
    val frontDefault: String?,
    val frontFemale: String?,
    val frontShiny: String?,
    val frontShinyFemale: String?
){
    fun toSprites() = Sprites(backDefault, backFemale, backShiny, backShinyFemale, frontDefault, frontFemale, frontShiny, frontShinyFemale)
}