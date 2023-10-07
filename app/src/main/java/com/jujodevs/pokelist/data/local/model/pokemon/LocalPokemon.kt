package com.jujodevs.pokelist.data.local.model.pokemon

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.jujodevs.pokelist.domain.model.Pokemon

@Entity
data class LocalPokemon(
    val abilities: LocalAbilitiesHolder,
    val height: Int,
    @PrimaryKey val id: Int,
    val name: String,
    val order: Int,
    @Embedded val sprites: LocalSprites,
    val types: LocalTypesHolder,
    val weight: Int,
    val favorite: Boolean
){
    fun toPokemon() = Pokemon(abilities.toAbilities(), height, id, name, order, sprites.toSprites(), types.toTypes(), weight, favorite)
}