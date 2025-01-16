package com.example.gourmetglobe.data.local.entities

import androidx.room.TypeConverter



/**
 * Classe utilitaire contenant des convertisseurs utilisés avec Room
 * Ces convertisseurs permettent de convertir des types non primitifs (comme les listes) en types primitifs
 * qui peuvent être stockés dans Room
 */
class Converters {


    /**
     * Convertit une liste de chaînes de caractères en une chaîne de texte où chaque élément est séparé par une virgule.
     *
     * @param list La liste de chaînes à convertir.
     * @return Une chaîne de caractères avec les éléments de la liste séparés par des virgules.
     */
    @TypeConverter
    fun fromStringList(list: List<String>): String {
    // Convertir une liste en une chaîne séparée par des virgules
        return list.joinToString(",")
    }

    /**
     * Convertit une chaîne de caractères séparée par des virgules en une liste de chaînes de caractères.
     *
     * @param data La chaîne de caractères à convertir.
     * @return Une liste de chaînes de caractères, vide si la chaîne d'entrée est vide.
     */
    @TypeConverter
    fun toStringList(data: String): List<String> {
    // Convertir une chaîne séparée par des virgules en liste
    return if (data.isEmpty()) emptyList() else data.split(",")
    }


}