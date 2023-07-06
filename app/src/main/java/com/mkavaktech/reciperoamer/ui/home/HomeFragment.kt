package com.mkavaktech.reciperoamer.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.mkavaktech.reciperoamer.data.entities.CategoryMeal
import com.mkavaktech.reciperoamer.data.entities.Meal
import com.mkavaktech.reciperoamer.databinding.FragmentHomeBinding
import com.mkavaktech.reciperoamer.ui.food_details.FoodDetailsActivity
import com.mkavaktech.reciperoamer.utils.Constants
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment(), PopularFoodAdapter.PopularFoodListener {
    private lateinit var binding: FragmentHomeBinding
    private val homeViewModel: HomeViewModel by viewModels()
    private lateinit var randomFood: Meal
    private lateinit var popularFoodAdapter: PopularFoodAdapter

    companion object {
        const val foodId: String = Constants.PACKAGE_NAME + ".foodId"
        const val foodName: String = Constants.PACKAGE_NAME + ".foodName"
        const val foodThumb: String = Constants.PACKAGE_NAME + ".foodThumb"
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        popularFoodAdapter = PopularFoodAdapter(this)

        setupRecyclerView()

        homeViewModel.getRandomFood()
        homeViewModel.getPopularFoods()

        observerRandomFood()
        observerPopularFood()

        onRandomFoodClick()
    }

    private fun onRandomFoodClick() {
        binding.randomFoodImage.setOnClickListener {
            val intent = Intent(activity, FoodDetailsActivity::class.java)
            intent.putExtra(foodId, randomFood.idMeal)
            intent.putExtra(foodName, randomFood.strMeal)
            intent.putExtra(foodThumb, randomFood.strMealThumb)
            startActivity(intent)
        }
    }

    private fun observerRandomFood() {
        homeViewModel.randomFoodLiveData.observe(
            viewLifecycleOwner
        ) { food ->
            Glide.with(this@HomeFragment).load(food.strMealThumb).into(binding.randomFoodImage)
            binding.randomFoodTxt.text = food.strMeal
            this.randomFood = food
        }
    }

    private fun observerPopularFood() {
        homeViewModel.popularFoodLiveData.observe(
            viewLifecycleOwner
        ) { foodList ->
            popularFoodAdapter.setFood(foodList as ArrayList<CategoryMeal>)
        }
    }

    private fun setupRecyclerView() {
        binding.popularFoodRecView.apply {
            layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
            adapter = popularFoodAdapter
        }
    }

    override fun onPopularFoodClick(popularFood: CategoryMeal) {
        val intent = Intent(activity, FoodDetailsActivity::class.java)
        intent.putExtra(foodId, popularFood.idMeal)
        intent.putExtra(foodName, popularFood.strMeal)
        intent.putExtra(foodThumb, popularFood.strMealThumb)
        startActivity(intent)
    }

}