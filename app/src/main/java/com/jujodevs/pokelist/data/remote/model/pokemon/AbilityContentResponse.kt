package com.jujodevs.pokelist.data.remote.model.pokemon


import com.google.gson.annotations.SerializedName
import com.jujodevs.pokelist.domain.model.AbilityContent

data class AbilityContentResponse(
    @SerializedName("name")
    val name: String,
    @SerializedName("url")
    val url: String
) {
    fun toAbilityContent() = AbilityContent(name, url)
}