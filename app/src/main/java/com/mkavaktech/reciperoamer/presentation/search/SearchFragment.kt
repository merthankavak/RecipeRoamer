package com.mkavaktech.reciperoamer.presentation.search


import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.mkavaktech.reciperoamer.data.entities.Meal
import com.mkavaktech.reciperoamer.databinding.FragmentSearchBinding
import com.mkavaktech.reciperoamer.presentation.food_details.FoodDetailsActivity
import com.mkavaktech.reciperoamer.utils.Constants
import com.mkavaktech.reciperoamer.utils.Helper.Companion.debounce
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class SearchFragment : Fragment(), SearchFoodsAdapter.SearchFoodsListener {
    private lateinit var binding: FragmentSearchBinding
    private val searchViewModel: SearchViewModel by viewModels()

    private lateinit var searchFoodsAdapter: SearchFoodsAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        observeSearchFoods()
        executeSearch()
    }

    private fun executeSearch() {
        val debounceSearch = debounce<String>(
            waitMs = 300L, coroutineScope = searchViewModel.viewModelScope
        ) { searchText ->
            searchViewModel.searchFoods(searchText)
        }
        binding.apply {
            searchEditText.addTextChangedListener { editable ->
                val searchText = editable.toString()
                debounceSearch(searchText)
            }
            clearIcon.setOnClickListener {
                searchEditText.text.clear()
            }
        }
    }

    private fun setupRecyclerView() {
        searchFoodsAdapter = SearchFoodsAdapter(this)
        binding.searchFoodsRecView.apply {
            layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
            adapter = searchFoodsAdapter
        }
    }


    private fun observeSearchFoods() {
        searchViewModel.searchFoodsLiveData.observe(
            viewLifecycleOwner
        ) { foodList ->
            searchFoodsAdapter.setFood(foodList as ArrayList<Meal>)
        }
    }

    override fun onFoodClick(food: Meal) {
        val intent = Intent(activity, FoodDetailsActivity::class.java)
        intent.putExtra(Constants.Details.FOOD_ID, food.idMeal)
        intent.putExtra(Constants.Details.FOOD_NAME, food.strMeal)
        intent.putExtra(Constants.Details.FOOD_THUMB, food.strMealThumb)
        startActivity(intent)
    }

}