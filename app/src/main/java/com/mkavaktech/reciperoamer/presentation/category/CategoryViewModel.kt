package com.mkavaktech.reciperoamer.presentation.category

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mkavaktech.reciperoamer.data.entities.FoodByCategory
import com.mkavaktech.reciperoamer.data.repository.FoodRepository
import com.mkavaktech.reciperoamer.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CategoryViewModel @Inject constructor(private val foodRepository: FoodRepository) : ViewModel() {
    private var _foodsLiveData: MutableLiveData<List<FoodByCategory>> =
        MutableLiveData<List<FoodByCategory>>()
    val foodsLiveData: LiveData<List<FoodByCategory>> = _foodsLiveData


    fun getFoodByCategory(categoryName: String) {
        viewModelScope.launch {
            when (val response = foodRepository.getFoodsByCategory(categoryName)) {
                is Resource.Success -> {
                    _foodsLiveData.value = response.data!!
                }

                is Resource.Error -> {
                    Log.d("CategoryViewModel", response.message!!)
                }
            }
        }
    }
}