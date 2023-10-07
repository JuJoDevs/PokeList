package com.jujodevs.pokelist.data.local.model.pokemon

import com.jujodevs.pokelist.domain.model.Ability

data class LocalAbility(
    val ability: LocalAbilityContent,
    val isHidden: Boolean,
    val slot: Int
) {
    fun toAbility() = Ability(ability.toAbilityContent(), isHidden, slot)
}