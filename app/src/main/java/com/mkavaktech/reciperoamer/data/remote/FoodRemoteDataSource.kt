package com.mkavaktech.reciperoamer.data.remote

import com.mkavaktech.reciperoamer.data.entities.Category
import com.mkavaktech.reciperoamer.data.entities.FoodByCategory
import com.mkavaktech.reciperoamer.data.entities.Meal
import com.mkavaktech.reciperoamer.utils.Resource
import javax.inject.Inject


class FoodRemoteDataSource @Inject constructor(private val foodService: FoodService) {

    suspend fun getRandomFood(): Resource<Meal> {
        try {

            val response = foodService.getRandomFood()
            if (response.isSuccessful) {
                val body = response.body()
                if (body != null) {
                    val meal: Meal = body.meals.first()
                    return Resource.Success(meal)
                }
            }

            return Resource.Error("${response.code()} - ${response.message()}")

        } catch (e: Exception) {
            return Resource.Error("Network Error -> ${e.message ?: e.toString()}")
        }
    }

    suspend fun getFoodDetails(foodId: String): Resource<Meal> {
        try {
            val response = foodService.getFoodDetails(foodId)
            if (response.isSuccessful) {
                val body = response.body()
                if (body != null) {
                    val meal: Meal = body.meals.first()
                    return Resource.Success(meal)
                }
            }

            return Resource.Error("${response.code()} - ${response.message()}")

        } catch (e: Exception) {
            return Resource.Error("Network Error -> ${e.message ?: e.toString()}")
        }
    }

    suspend fun getFoodsByCategory(categoryName: String): Resource<List<FoodByCategory>> {
        try {
            val response = foodService.getFoodsByCategory(categoryName)
            if (response.isSuccessful) {
                val body = response.body()
                if (body != null) {
                    val mealList: List<FoodByCategory> = body.meals
                    return Resource.Success(mealList)
                }
            }

            return Resource.Error("${response.code()} - ${response.message()}")

        } catch (e: Exception) {
            return Resource.Error("Network Error -> ${e.message ?: e.toString()}")
        }
    }

    suspend fun getCategories(): Resource<List<Category>> {
        try {

            val response = foodService.getCategories()
            if (response.isSuccessful) {
                val body = response.body()
                if (body != null) {
                    val categoryList: List<Category> = body.categories
                    return Resource.Success(categoryList)
                }
            }

            return Resource.Error("${response.code()} - ${response.message()}")

        } catch (e: Exception) {
            return Resource.Error("Network Error -> ${e.message ?: e.toString()}")
        }
    }

    suspend fun searchFoods(searchName: String): Resource<List<Meal>> {
        try {
            val response = foodService.searchFoods(searchName)
            if (response.isSuccessful) {
                val body = response.body()
                if (body != null) {
                    val mealList: List<Meal> = body.meals
                    return Resource.Success(mealList)
                }
            }

            return Resource.Error("${response.code()} - ${response.message()}")

        } catch (e: Exception) {
            return Resource.Error("Network Error -> ${e.message ?: e.toString()}")
        }
    }


}