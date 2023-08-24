package com.mkavaktech.reciperoamer.presentation.home

import android.util.Log

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mkavaktech.reciperoamer.data.entities.Category
import com.mkavaktech.reciperoamer.data.entities.FoodByCategory
import com.mkavaktech.reciperoamer.data.entities.Meal
import com.mkavaktech.reciperoamer.data.repository.FoodRepository
import com.mkavaktech.reciperoamer.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val foodRepository: FoodRepository) : ViewModel() {
    private var _randomFoodLiveData: MutableLiveData<Meal> = MutableLiveData<Meal>()
    val randomFoodLiveData: LiveData<Meal> = _randomFoodLiveData

    private var _popularFoodLiveData: MutableLiveData<List<FoodByCategory>> =
        MutableLiveData<List<FoodByCategory>>()
    val popularFoodLiveData: LiveData<List<FoodByCategory>> = _popularFoodLiveData

    private var _categoriesLiveData: MutableLiveData<List<Category>> =
        MutableLiveData<List<Category>>()
    val categoriesLiveData: LiveData<List<Category>> = _categoriesLiveData

    init {
        getRandomFood()
        getPopularFoods()
    }

    private fun getRandomFood() {
        viewModelScope.launch {
            when (val response = foodRepository.getRandomFood()) {
                is Resource.Success -> {
                    _randomFoodLiveData.value = response.data!!
                }

                is Resource.Error -> {
                    Log.d("HomeViewModel", response.message!!)
                }
            }
        }
    }

    private fun getRandomCategory(): String {
        val categories = listOf(
            "Beef",
            "Breakfast",
            "Chicken",
            "Dessert",
            "Goat",
            "Lamb",
            "Miscellaneous",
            "Pasta",
            "Pork",
            "Seafood",
            "Side",
            "Starter",
            "Vegan",
            "Vegetarian"
        )
        val randomIndex = (categories.indices).random()
        return categories[randomIndex]
    }

    private fun getPopularFoods() {
        viewModelScope.launch {
            when (val response = foodRepository.getFoodsByCategory(getRandomCategory())) {
                is Resource.Success -> {
                    val popularFoodList = if (response.data!!.size > 5) {
                        response.data.shuffled().take(5)
                    } else {
                        response.data
                    }
                    _popularFoodLiveData.value = popularFoodList
                }

                is Resource.Error -> {
                    Log.d("HomeViewModel", response.message!!)
                }
            }
        }
    }

    fun getCategories() {
        viewModelScope.launch {
            when (val response = foodRepository.getCategories()) {
                is Resource.Success -> {
                    _categoriesLiveData.value = response.data!!
                }

                is Resource.Error -> {
                    Log.d("HomeViewModel", response.message!!)
                }
            }
        }
    }


}

