package com.example.gourmetglobe.data.model
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Ingredients {
    val id: Int,
    val name: String,
    // val amount: Amount, 
    
}