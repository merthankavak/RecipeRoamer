package com.mkavaktech.reciperoamer.presentation.search

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
class SearchViewModel @Inject constructor(private val foodRepository: FoodRepository) :
    ViewModel() {

    private var _searchFoodsLiveData: MutableLiveData<List<Meal>> = MutableLiveData<List<Meal>>()
    val searchFoodsLiveData: LiveData<List<Meal>> = _searchFoodsLiveData

    fun searchFoods(searchName: String) {
        viewModelScope.launch {
            when (val response = foodRepository.searchFoods(searchName)) {
                is Resource.Success -> {
                    _searchFoodsLiveData.value = response.data ?: ArrayList()
                }

                is Resource.Error -> {
                    Log.d("SearchViewModel", response.message!!)
                }
            }
        }
    }
}