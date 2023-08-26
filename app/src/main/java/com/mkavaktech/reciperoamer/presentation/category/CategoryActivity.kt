package com.mkavaktech.reciperoamer.presentation.category

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.mkavaktech.reciperoamer.data.entities.FoodByCategory
import com.mkavaktech.reciperoamer.databinding.ActivityCategoryBinding
import com.mkavaktech.reciperoamer.presentation.food_details.FoodDetailsActivity
import com.mkavaktech.reciperoamer.utils.Constants
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CategoryActivity : AppCompatActivity(), CategoryFoodAdapter.CategoryFoodListener {
    private lateinit var binding: ActivityCategoryBinding
    private lateinit var categoryName: String

    private val categoryViewModel: CategoryViewModel by viewModels()
    private lateinit var categoryFoodAdapter: CategoryFoodAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCategoryBinding.inflate(layoutInflater)
        setContentView(binding.root)
        categoryFoodAdapter = CategoryFoodAdapter(this)
        getCategoryInfoFromIntent()
        setupToolBarSettings()
        categoryViewModel.getFoodByCategory(categoryName)
        setupRecyclerView()
        observeFoods()
    }

    private fun setupToolBarSettings() {
        val toolbar = binding.categoryToolBar
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        binding.categoryToolBar.title = categoryName
    }

    private fun setupRecyclerView() {
        binding.categoryRecView.apply {
            layoutManager = GridLayoutManager(context, 3, GridLayoutManager.VERTICAL, false)
            adapter = categoryFoodAdapter
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun getCategoryInfoFromIntent() {
        val intent = intent
        categoryName = intent.getStringExtra(Constants.Details.CATEGORY_NAME)!!
    }

    private fun observeFoods() {
        categoryViewModel.foodsLiveData.observe(this) { foodList ->
            categoryFoodAdapter.setFoods(foodList as ArrayList<FoodByCategory>)
        }

    }

    override fun onFoodClick(foodByCategory: FoodByCategory) {
        val intent = Intent(this, FoodDetailsActivity::class.java)
        intent.putExtra(Constants.Details.FOOD_ID, foodByCategory.idMeal)
        intent.putExtra(Constants.Details.FOOD_NAME, foodByCategory.strMeal)
        intent.putExtra(Constants.Details.FOOD_THUMB, foodByCategory.strMealThumb)
        startActivity(intent)
    }
}