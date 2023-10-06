package com.jujodevs.pokelist.data.local

import androidx.room.Dao
import androidx.room.Database
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.PrimaryKey
import androidx.room.Query
import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import androidx.room.Update
import com.google.gson.Gson

@Database(
    entities = [
        LocalPokemon::class
    ],
    version = 1,
    exportSchema = true
)
@TypeConverters(Converters::class)
abstract class PokemonDatabase : RoomDatabase() {
    abstract fun pokemonDao(): PokemonDao
}

@Dao
interface PokemonDao {

    @Query("SELECT * FROM LocalPokemon")
    suspend fun getPokemonList(): List<LocalPokemon>

    @Insert
    suspend fun insertAll(pokemos: List<LocalPokemon>)

    @Update
    suspend fun updatePokemon(pokemon: LocalPokemon)

    @Query("SELECT COUNT(*) FROM LocalPokemon")
    suspend fun count(): Int
}

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
    val favorite: Boolean = false
)

data class LocalAbilitiesHolder(
    val abilities: List<LocalAbility>
)

data class LocalAbility(
    val ability: LocalAbilityContent,
    val isHidden: Boolean,
    val slot: Int
)

data class LocalAbilityContent(
    val name: String,
    val url: String
)

data class LocalSprites constructor(
    val backDefault: String?,
    val backFemale: String?,
    val backShiny: String?,
    val backShinyFemale: String?,
    val frontDefault: String?,
    val frontFemale: String?,
    val frontShiny: String?,
    val frontShinyFemale: String?
)

data class LocalTypesHolder(
    val types: List<LocalType>
)

data class LocalType(
    val slot: Int,
    val type: LocalTypeContent
)

data class LocalTypeContent(
    val name: String,
    val url: String
)

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


