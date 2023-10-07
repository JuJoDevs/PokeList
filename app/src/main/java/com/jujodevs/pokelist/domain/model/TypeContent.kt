package com.jujodevs.pokelist.domain.model

import com.jujodevs.pokelist.data.local.model.pokemon.LocalTypeContent

data class TypeContent(
    val name: String,
    val url: String
) {
    fun toLocalContent() = LocalTypeContent(name, url)
}
