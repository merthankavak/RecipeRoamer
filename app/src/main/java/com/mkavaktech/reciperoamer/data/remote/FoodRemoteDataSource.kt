package com.mkavaktech.reciperoamer.data.remote

import com.mkavaktech.reciperoamer.data.entities.Meal
import com.mkavaktech.reciperoamer.utils.Resource
import javax.inject.Inject


class FoodRemoteDataSource @Inject constructor(private val foodService: FoodService) {

    suspend fun getRandomFood() : Resource<Meal> {
        try {

            val response = foodService.getRandomFood()
            if (response.isSuccessful) {
                val body  = response.body()
                if (body != null) {
                    val meal : Meal = body.meals.first()
                    return Resource.Success(meal)
                }
            }

            return Resource.Error("${response.code()} - ${response.message()}")

        } catch (e: Exception) {
            return Resource.Error("Network Error -> ${e.message ?: e.toString()}")
        }
    }

    suspend fun getFoodDetails(foodId: String) : Resource<Meal> {
        try {
            val response = foodService.getFoodDetails(foodId)
            if (response.isSuccessful) {
                val body  = response.body()
                if (body != null) {
                    val meal : Meal = body.meals.first()
                    return Resource.Success(meal)
                }
            }

            return Resource.Error("${response.code()} - ${response.message()}")

        } catch (e: Exception) {
            return Resource.Error("Network Error -> ${e.message ?: e.toString()}")
        }
    }

}