package com.mkavaktech.reciperoamer.ui.category

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.GridLayoutManager
import com.mkavaktech.reciperoamer.R
import com.mkavaktech.reciperoamer.data.entities.FoodByCategory
import com.mkavaktech.reciperoamer.databinding.ActivityCategoryBinding
import com.mkavaktech.reciperoamer.ui.food_details.FoodDetailsActivity
import com.mkavaktech.reciperoamer.ui.home.HomeFragment
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
        observerFoods()
    }

    private fun setupToolBarSettings() {
        val toolbar = findViewById<Toolbar>(R.id.categoryToolBar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        binding.categoryToolBar.setTitleTextColor(
            ContextCompat.getColor(
                this, R.color.white
            )
        )
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
        categoryName = intent.getStringExtra(HomeFragment.categoryName)!!
    }

    private fun observerFoods() {
        categoryViewModel.foodsLiveData.observe(this) { foodList ->
            categoryFoodAdapter.setFoods(foodList as ArrayList<FoodByCategory>)
        }

    }

    override fun onFoodClick(foodByCategory: FoodByCategory) {
        val intent = Intent(this, FoodDetailsActivity::class.java)
        intent.putExtra(HomeFragment.foodId, foodByCategory.idMeal)
        intent.putExtra(HomeFragment.foodName, foodByCategory.strMeal)
        intent.putExtra(HomeFragment.foodThumb, foodByCategory.strMealThumb)
        startActivity(intent)
    }
}