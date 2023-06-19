package com.mkavaktech.reciperoamer.di

import com.mkavaktech.reciperoamer.data.remote.FoodRemoteDataSource
import com.mkavaktech.reciperoamer.data.remote.FoodService
import com.mkavaktech.reciperoamer.data.repository.FoodRepository
import com.mkavaktech.reciperoamer.utils.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {
    @Singleton
    @Provides
    fun provideRetrofit() : Retrofit {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Singleton
    @Provides
    fun provideFoodService(retrofit: Retrofit) : FoodService{
        return retrofit.create(FoodService::class.java)
    }

    @Provides
    fun provideFoodRemoteDataSource(foodService: FoodService): FoodRemoteDataSource {
        return FoodRemoteDataSource(foodService)
    }

    @Provides
    fun provideFoodRepository(foodRemoteDataSource: FoodRemoteDataSource): FoodRepository {
        return FoodRepository(foodRemoteDataSource)
    }
}