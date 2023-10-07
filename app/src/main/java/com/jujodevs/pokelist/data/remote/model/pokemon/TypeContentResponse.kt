package com.jujodevs.pokelist.data.remote.model.pokemon


import com.google.gson.annotations.SerializedName
import com.jujodevs.pokelist.domain.model.TypeContent

data class TypeContentResponse(
    @SerializedName("name")
    val name: String,
    @SerializedName("url")
    val url: String
) {
    fun toTypeContent() = TypeContent(name, url)
}