package com.mkavaktech.reciperoamer.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.mkavaktech.reciperoamer.data.entities.Meal

@Dao
interface MealDao {
    @Query("SELECT * FROM meals ORDER BY str_meal")
    suspend fun getAllMeals(): List<Meal>

    @Query("SELECT * FROM meals WHERE idMeal = :mealId")
    suspend fun getMeal(mealId: String): Meal

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMeal(meal: Meal) : Long

    @Delete
    suspend fun deleteMeal(meal: Meal)
}