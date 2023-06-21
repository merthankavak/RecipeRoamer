package com.mkavaktech.reciperoamer.data.remote

import com.mkavaktech.reciperoamer.data.entities.MealList
import retrofit2.Response
import retrofit2.http.GET

interface FoodService {
    @GET("random.php")
    suspend fun getRandomFood() : Response<MealList>
}