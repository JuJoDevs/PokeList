package com.jujodevs.pokelist.pokemon


import com.google.gson.annotations.SerializedName

data class TypeContent(
    @SerializedName("name")
    val name: String,
    @SerializedName("url")
    val url: String
)