package com.mkavaktech.reciperoamer.data.repository

import com.mkavaktech.reciperoamer.data.entities.Category
import com.mkavaktech.reciperoamer.data.entities.FoodByCategory
import com.mkavaktech.reciperoamer.data.entities.Meal
import com.mkavaktech.reciperoamer.data.local.MealDao
import com.mkavaktech.reciperoamer.data.remote.FoodRemoteDataSource
import com.mkavaktech.reciperoamer.utils.Resource
import javax.inject.Inject


class FoodRepository @Inject constructor(
    private val remoteDataSource: FoodRemoteDataSource,
    private val localDataSource: MealDao
) {
    suspend fun getRandomFood(): Resource<Meal> {
        return remoteDataSource.getRandomFood()
    }

    suspend fun getFoodDetails(foodId: String): Resource<Meal> {
        return remoteDataSource.getFoodDetails(foodId)
    }

    suspend fun getFoodsByCategory(categoryName: String): Resource<List<FoodByCategory>> {
        return remoteDataSource.getFoodsByCategory(categoryName)
    }

    suspend fun getCategories(): Resource<List<Category>> {
        return remoteDataSource.getCategories()
    }

    suspend fun addToFavoriteFood(meal: Meal) = localDataSource.insertMeal(meal)

    suspend fun getFavoriteFood(mealId: String) : Meal = localDataSource.getMeal(mealId)


    suspend fun removeFavoriteFood(meal: Meal) = localDataSource.deleteMeal(meal)


    suspend fun getFavoriteFoods(): List<Meal> = localDataSource.getAllMeals()


}