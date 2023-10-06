package com.jujodevs.pokelist.data.remote.pokemon


import com.google.gson.annotations.SerializedName

data class Type(
    @SerializedName("slot")
    val slot: Int,
    @SerializedName("type")
    val type: TypeContent
)