package com.jujodevs.pokelist.data.remote.model.pokemon


import com.google.gson.annotations.SerializedName
import com.jujodevs.pokelist.domain.model.Ability

data class AbilityResponse(
    @SerializedName("ability")
    val ability: AbilityContentResponse,
    @SerializedName("is_hidden")
    val isHidden: Boolean,
    @SerializedName("slot")
    val slot: Int
){
    fun toAbility() = Ability(ability.toAbilityContent(), isHidden, slot)
}