package com.mkavaktech.reciperoamer.presentation.favorites

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mkavaktech.reciperoamer.data.entities.Meal
import com.mkavaktech.reciperoamer.data.repository.FoodRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class FavoritesViewModel @Inject constructor(private val foodRepository: FoodRepository) :
    ViewModel() {
    private var _favoritesFoodLiveData: MutableLiveData<List<Meal>> = MutableLiveData<List<Meal>>()
    val favoritesFoodLiveData: LiveData<List<Meal>> = _favoritesFoodLiveData

    fun getFavoriteFoods() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                val favoriteFoodList: List<Meal> = foodRepository.getFavoriteFoods()
                _favoritesFoodLiveData.postValue(favoriteFoodList)
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

    fun removeFavoriteFood(meal: Meal) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                foodRepository.removeFavoriteFood(meal)
            }
        }
    }
}