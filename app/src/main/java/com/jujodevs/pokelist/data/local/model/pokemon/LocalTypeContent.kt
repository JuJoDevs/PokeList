package com.jujodevs.pokelist.data.local.model.pokemon

import com.jujodevs.pokelist.domain.model.TypeContent

data class LocalTypeContent(
    val name: String,
    val url: String
) {
    fun toTypeContent() = TypeContent(name, url)
}