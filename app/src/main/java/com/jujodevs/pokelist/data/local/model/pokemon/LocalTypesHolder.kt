package com.jujodevs.pokelist.data.local.model.pokemon

data class LocalTypesHolder(
    val types: List<LocalType>
){
    fun toTypes() = types.map { it.toType() }
}