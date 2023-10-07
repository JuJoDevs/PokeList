package com.jujodevs.pokelist.data.remote.model.pokemon


import com.google.gson.annotations.SerializedName
import com.jujodevs.pokelist.domain.model.Type

data class TypeResponse(
    @SerializedName("slot")
    val slot: Int,
    @SerializedName("type")
    val type: TypeContentResponse
){
    fun toType() = Type(slot, type.toTypeContent())
}