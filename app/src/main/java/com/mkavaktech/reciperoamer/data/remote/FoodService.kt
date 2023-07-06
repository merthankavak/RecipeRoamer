package com.mkavaktech.reciperoamer.data.remote

import com.mkavaktech.reciperoamer.data.entities.CategoryList
import com.mkavaktech.reciperoamer.data.entities.FoodByCategoryList
import com.mkavaktech.reciperoamer.data.entities.MealList
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface FoodService {
    @GET("random.php")
    suspend fun getRandomFood() : Response<MealList>

    @GET("lookup.php?")
    suspend fun  getFoodDetails(@Query(value = "i") foodId:String) : Response<MealList>

    @GET("filter.php?")
    suspend fun  getFoodsByCategory(@Query(value = "c") categoryName:String) : Response<FoodByCategoryList>

    @GET("categories.php")
    suspend fun getCategories() : Response<CategoryList>



}