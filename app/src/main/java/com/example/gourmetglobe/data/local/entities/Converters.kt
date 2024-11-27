package com.example.gourmetglobe.data.local.entities

import androidx.room.TypeConverter

class Converters {

    @TypeConverter
    fun fromStringList(list: List<String>): String {
    // Convertir une liste en une chaîne séparée par des virgules
        return list.joinToString(",")
    }

    @TypeConverter
    fun toStringList(data: String): List<String> {
    // Convertir une chaîne séparée par des virgules en liste
    return if (data.isEmpty()) emptyList() else data.split(",")
    }


}