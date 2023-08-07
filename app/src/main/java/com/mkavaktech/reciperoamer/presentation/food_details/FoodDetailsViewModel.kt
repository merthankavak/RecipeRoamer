package com.mkavaktech.reciperoamer.presentation.food_details

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mkavaktech.reciperoamer.data.entities.Meal
import com.mkavaktech.reciperoamer.data.repository.FoodRepository
import com.mkavaktech.reciperoamer.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel

import kotlinx.coroutines.Dispatchers

import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

import javax.inject.Inject


@HiltViewModel
class FoodDetailsViewModel @Inject constructor(private val foodRepository: FoodRepository) :
    ViewModel() {
    private var _foodDetailsLiveData: MutableLiveData<Meal> = MutableLiveData<Meal>()
    private var _foodFavoriteLiveData: MutableLiveData<Meal?> = MutableLiveData<Meal?>()
    val foodDetailsLiveData: LiveData<Meal> = _foodDetailsLiveData
    val foodFavoriteLiveData: LiveData<Meal?> = _foodFavoriteLiveData


    fun getFoodDetails(foodId: String) {
        viewModelScope.launch {
            when (val response = foodRepository.getFoodDetails(foodId)) {
                is Resource.Success -> {
                    _foodDetailsLiveData.value = response.data!!
                }

                is Resource.Error -> {
                    Log.d("HomeViewModel", response.message!!)
                }
            }
        }
    }

    fun addToFavoriteFood(meal: Meal) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                foodRepository.addToFavoriteFood(meal)
            }
        }
    }

    fun getFavoriteFood(mealId: String) {
        viewModelScope.launch {
            val favoriteFood: Meal = foodRepository.getFavoriteFood(mealId)
            _foodFavoriteLiveData.value = favoriteFood
        }
    }

    fun removeFavoriteFood(meal: Meal) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                foodRepository.removeFavoriteFood(meal)
            }
        }
    }
}

