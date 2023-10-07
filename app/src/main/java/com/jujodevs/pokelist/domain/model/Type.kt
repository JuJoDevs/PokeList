package com.jujodevs.pokelist.domain.model

import com.jujodevs.pokelist.data.local.model.pokemon.LocalType

data class Type(
    val slot: Int,
    val type: TypeContent
){
    fun toLocalType() = LocalType(slot, type.toLocalContent())
}