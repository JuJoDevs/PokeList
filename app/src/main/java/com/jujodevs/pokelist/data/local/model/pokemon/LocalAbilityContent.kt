package com.jujodevs.pokelist.data.local.model.pokemon

import com.jujodevs.pokelist.domain.model.AbilityContent

data class LocalAbilityContent(
    val name: String,
    val url: String
) {
    fun toAbilityContent() = AbilityContent(name, url)
}