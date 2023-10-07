package com.jujodevs.pokelist.data.local.model.pokemon

import com.jujodevs.pokelist.domain.model.Type

data class LocalType(
    val slot: Int,
    val type: LocalTypeContent
) {
    fun toType() = Type(slot, type.toTypeContent())
}