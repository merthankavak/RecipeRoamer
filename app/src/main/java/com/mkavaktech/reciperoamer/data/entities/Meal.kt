package com.mkavaktech.reciperoamer.data.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "meals")
data class Meal(
    @PrimaryKey val idMeal: String,
    @ColumnInfo(name = "str_area") val strArea: String?,
    @ColumnInfo(name = "str_category") val strCategory: String?,
    @ColumnInfo(name = "str_instructions") val strInstructions: String?,
    @ColumnInfo(name = "str_meal") val strMeal: String?,
    @ColumnInfo(name = "str_meal_thumb") val strMealThumb: String?,
    @ColumnInfo(name = "str_youtube") val strYoutube: String?

)