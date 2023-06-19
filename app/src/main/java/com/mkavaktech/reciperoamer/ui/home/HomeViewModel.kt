package com.mkavaktech.reciperoamer.ui.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mkavaktech.reciperoamer.data.entities.Meal
import com.mkavaktech.reciperoamer.data.repository.FoodRepository
import com.mkavaktech.reciperoamer.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val foodRepository: FoodRepository): ViewModel() {
    private var _randomFoodLiveData : MutableLiveData<Meal> = MutableLiveData<Meal>()
    val randomFoodLiveData :LiveData<Meal> = _randomFoodLiveData

    fun getRandomFood() {
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

}

