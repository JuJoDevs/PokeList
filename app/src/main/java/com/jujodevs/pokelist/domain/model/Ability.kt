package com.jujodevs.pokelist.domain.model

import com.jujodevs.pokelist.data.local.model.pokemon.LocalAbility

data class Ability(
    val ability: AbilityContent,
    val isHidden: Boolean,
    val slot: Int
){
    fun toLocalAbility() = LocalAbility(ability.toLocalContent(), isHidden, slot)
}