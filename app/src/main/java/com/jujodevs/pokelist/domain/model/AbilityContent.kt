package com.jujodevs.pokelist.domain.model

import com.jujodevs.pokelist.data.local.model.pokemon.LocalAbilityContent

data class AbilityContent(
    val name: String,
    val url: String
) {
    fun toLocalContent() = LocalAbilityContent(name, url)
}