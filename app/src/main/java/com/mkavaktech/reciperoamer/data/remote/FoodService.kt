package com.mkavaktech.reciperoamer.data.remote

import com.mkavaktech.reciperoamer.data.entities.CategoryList
import com.mkavaktech.reciperoamer.data.entities.FoodByCategoryList
import com.mkavaktech.reciperoamer.data.entities.MealList
import com.mkavaktech.reciperoamer.utils.Constants
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface FoodService {
    @GET(Constants.Network.RANDOM)
    suspend fun getRandomFood(): Response<MealList>

    @GET(Constants.Network.LOOKUP)
    suspend fun getFoodDetails(@Query(value = "i") foodId: String): Response<MealList>

    @GET(Constants.Network.FILTER)
    suspend fun getFoodsByCategory(@Query(value = "c") categoryName: String): Response<FoodByCategoryList>

    @GET(Constants.Network.CATEGORIES)
    suspend fun getCategories(): Response<CategoryList>

    @GET(Constants.Network.SEARCH)
    suspend fun searchFoods(@Query(value = "s") searchName: String): Response<MealList>
}