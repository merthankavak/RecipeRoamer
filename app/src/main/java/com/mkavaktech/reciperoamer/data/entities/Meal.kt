package com.mkavaktech.reciperoamer.data.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.mkavaktech.reciperoamer.utils.Constants

@Entity(tableName = Constants.Database.TABLE_NAME)
data class Meal(
    @PrimaryKey val idMeal: String,
    @ColumnInfo(name = Constants.Database.STR_AREA) val strArea: String?,
    @ColumnInfo(name = Constants.Database.STR_CATEGORY) val strCategory: String?,
    @ColumnInfo(name = Constants.Database.STR_INSTRUCTIONS) val strInstructions: String?,
    @ColumnInfo(name = Constants.Database.STR_MEAL) val strMeal: String?,
    @ColumnInfo(name = Constants.Database.STR_MEAL_THUMB) val strMealThumb: String?,
    @ColumnInfo(name = Constants.Database.STR_YOUTUBE) val strYoutube: String?
)