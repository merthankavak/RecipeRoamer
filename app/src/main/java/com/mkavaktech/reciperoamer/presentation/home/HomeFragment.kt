package com.mkavaktech.reciperoamer.presentation.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.mkavaktech.reciperoamer.R
import com.mkavaktech.reciperoamer.data.entities.Category
import com.mkavaktech.reciperoamer.data.entities.FoodByCategory
import com.mkavaktech.reciperoamer.data.entities.Meal
import com.mkavaktech.reciperoamer.databinding.FragmentHomeBinding
import com.mkavaktech.reciperoamer.presentation.category.CategoryActivity
import com.mkavaktech.reciperoamer.presentation.category.CategoryAdapter
import com.mkavaktech.reciperoamer.presentation.food_details.FoodDetailsActivity
import com.mkavaktech.reciperoamer.utils.Constants
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment(), PopularFoodAdapter.PopularFoodListener,
    CategoryAdapter.CategoryListener {

    private lateinit var binding: FragmentHomeBinding
    private val homeViewModel: HomeViewModel by viewModels()
    private lateinit var randomFood: Meal
    private lateinit var popularFoodAdapter: PopularFoodAdapter
    private lateinit var categoryAdapter: CategoryAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()

        homeViewModel.getCategories()

        observeRandomFood()
        observePopularFood()
        observeCategories()

        onRandomFoodClick()
        onSearchIconClick()
    }

    private fun onSearchIconClick() {
        binding.imageSearch.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_searchFragment)
        }
    }

    private fun setupRecyclerView() {
        popularFoodAdapter = PopularFoodAdapter(this)
        categoryAdapter = CategoryAdapter(this)
        with(binding) {
            popularFoodsRecView.apply {
                layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
                adapter = popularFoodAdapter
            }
            categoriesRecView.apply {
                layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
                adapter = categoryAdapter
            }
        }
    }


    private fun observeRandomFood() {
        homeViewModel.randomFoodLiveData.observe(
            viewLifecycleOwner
        ) { food ->
            Glide.with(this@HomeFragment).load(food.strMealThumb).into(binding.randomFoodImage)
            binding.randomFoodTxt.text = food.strMeal
            this.randomFood = food
        }
    }

    private fun observePopularFood() {
        homeViewModel.popularFoodLiveData.observe(
            viewLifecycleOwner
        ) { foodList ->
            popularFoodAdapter.setFood(foodList as ArrayList<FoodByCategory>)
        }
    }

    private fun observeCategories() {
        homeViewModel.categoriesLiveData.observe(
            viewLifecycleOwner
        ) { categories ->
            categoryAdapter.setCategory(categories as ArrayList<Category>)
        }
    }


    private fun onRandomFoodClick() {
        binding.randomFoodImage.setOnClickListener {
            val intent = Intent(activity, FoodDetailsActivity::class.java)
            intent.putExtra(Constants.Details.FOOD_ID, randomFood.idMeal)
            intent.putExtra(Constants.Details.FOOD_NAME, randomFood.strMeal)
            intent.putExtra(Constants.Details.FOOD_THUMB, randomFood.strMealThumb)
            startActivity(intent)
        }
    }

    override fun onPopularFoodClick(popularFood: FoodByCategory) {
        val intent = Intent(activity, FoodDetailsActivity::class.java)
        intent.putExtra(Constants.Details.FOOD_ID, popularFood.idMeal)
        intent.putExtra(Constants.Details.FOOD_NAME, popularFood.strMeal)
        intent.putExtra(Constants.Details.FOOD_THUMB, popularFood.strMealThumb)
        startActivity(intent)
    }

    override fun onCategoryClick(category: Category) {
        val intent = Intent(activity, CategoryActivity::class.java)
        intent.putExtra(Constants.Details.CATEGORY_NAME, category.strCategory)
        startActivity(intent)
    }

}