package com.mkavaktech.reciperoamer.data.repository

import com.mkavaktech.reciperoamer.data.entities.Meal
import com.mkavaktech.reciperoamer.data.remote.FoodRemoteDataSource
import com.mkavaktech.reciperoamer.utils.Resource
import javax.inject.Inject


class FoodRepository @Inject constructor(
    private val remoteDataSource: FoodRemoteDataSource
) {
    suspend fun getRandomFood() : Resource<Meal> {
        return remoteDataSource.getRandomFood()
    }
}