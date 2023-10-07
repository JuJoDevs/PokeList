package com.jujodevs.pokelist.domain.model

import com.jujodevs.pokelist.data.local.model.pokemon.LocalAbilitiesHolder
import com.jujodevs.pokelist.data.local.model.pokemon.LocalPokemon
import com.jujodevs.pokelist.data.local.model.pokemon.LocalTypesHolder

data class Pokemon(
    val abilities: List<Ability>,
    val height: Int,
    val id: Int,
    val name: String,
    val order: Int,
    val sprites: Sprites,
    val types: List<Type>,
    val weight: Int,
    val favorite: Boolean = false
){
    fun toLocalPokemon() = LocalPokemon(
        LocalAbilitiesHolder(abilities.map { it.toLocalAbility() }),
        height,
        id,
        name,
        order,
        sprites.toLocalSprites(),
        LocalTypesHolder(types.map { it.toLocalType() }),
        weight,
        favorite
    )
}