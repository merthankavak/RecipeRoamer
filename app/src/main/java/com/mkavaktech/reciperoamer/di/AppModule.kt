package com.mkavaktech.reciperoamer.di

import android.content.Context
import com.mkavaktech.reciperoamer.data.local.AppDatabase
import com.mkavaktech.reciperoamer.data.local.MealDao
import com.mkavaktech.reciperoamer.data.remote.FoodRemoteDataSource
import com.mkavaktech.reciperoamer.data.remote.FoodService
import com.mkavaktech.reciperoamer.data.repository.FoodRepository
import com.mkavaktech.reciperoamer.utils.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {
    @Singleton
    @Provides
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder().baseUrl(Constants.Network.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create()).build()
    }

    @Singleton
    @Provides
    fun provideAppDatabase(@ApplicationContext context: Context) = AppDatabase.getDatabase(context)

    @Singleton
    @Provides
    fun provideFoodService(retrofit: Retrofit): FoodService {
        return retrofit.create(FoodService::class.java)
    }

    @Provides
    fun provideFoodRemoteDataSource(foodService: FoodService): FoodRemoteDataSource {
        return FoodRemoteDataSource(foodService)
    }

    @Provides
    fun provideFoodLocalDataSource(database: AppDatabase) = database.mealDao()

    @Provides
    fun provideFoodRepository(
        foodRemoteDataSource: FoodRemoteDataSource, foodLocalDataSource: MealDao
    ): FoodRepository {
        return FoodRepository(foodRemoteDataSource, foodLocalDataSource)
    }
}