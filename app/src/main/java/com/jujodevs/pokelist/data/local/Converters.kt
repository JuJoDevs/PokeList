package com.jujodevs.pokelist.data.local

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.jujodevs.pokelist.data.local.model.pokemon.LocalAbilitiesHolder
import com.jujodevs.pokelist.data.local.model.pokemon.LocalTypesHolder

class Converters {
    @TypeConverter
    fun fromAbility(abilities: LocalAbilitiesHolder): String = Gson().toJson(abilities)
    @TypeConverter
    fun toAbility(abilities: String): LocalAbilitiesHolder = Gson().fromJson(abilities, LocalAbilitiesHolder::class.java)
    @TypeConverter
    fun fromType(types: LocalTypesHolder): String = Gson().toJson(types)
    @TypeConverter
    fun toType(types: String): LocalTypesHolder = Gson().fromJson(types, LocalTypesHolder::class.java)
}