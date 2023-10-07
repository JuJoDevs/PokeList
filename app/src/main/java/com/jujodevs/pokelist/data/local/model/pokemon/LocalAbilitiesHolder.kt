package com.jujodevs.pokelist.data.local.model.pokemon

data class LocalAbilitiesHolder(
    val abilities: List<LocalAbility>
){
    fun toAbilities() = abilities.map { it.toAbility() }
}