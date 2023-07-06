package com.mkavaktech.reciperoamer.ui.food_details

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem

import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.core.graphics.BlendModeColorFilterCompat
import androidx.core.graphics.BlendModeCompat

import com.bumptech.glide.Glide
import com.mkavaktech.reciperoamer.R
import com.mkavaktech.reciperoamer.databinding.ActivityFoodDetailsBinding
import com.mkavaktech.reciperoamer.ui.home.HomeFragment
import dagger.hilt.android.AndroidEntryPoint



@AndroidEntryPoint
class FoodDetailsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFoodDetailsBinding

    private val foodDetailsViewModel : FoodDetailsViewModel by viewModels()

    private lateinit var foodId: String
    private lateinit var foodName: String
    private lateinit var  foodThumb: String
    private lateinit var videoLink: String

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFoodDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val toolbar = findViewById<Toolbar>(R.id.toolBar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        binding.collapsingToolBar.setCollapsedTitleTextColor(ContextCompat.getColor(this, R.color.white))

        getFoodInfoFromIntent()
        setFoodInfo()

        foodDetailsViewModel.getFoodDetails(foodId)
        observeFoodDetails()
    }

    private fun observeFoodDetails() {

        foodDetailsViewModel.foodDetailsLiveData.observe(this) { food ->
            binding.categoryTxtView.text = "Category: ${food.strCategory}"
            binding.cuisineTxtView.text = "Cuisine: ${food.strArea}"
            binding.instrTxtView.text = food.strInstructions
            videoLink = food.strYoutube
        }

    }

    private fun getFoodInfoFromIntent() {
        val intent = intent
        foodId = intent.getStringExtra(HomeFragment.foodId)!!
        foodName = intent.getStringExtra(HomeFragment.foodName)!!
        foodThumb = intent.getStringExtra(HomeFragment.foodThumb)!!
    }

    private fun setFoodInfo() {
        Glide.with(this@FoodDetailsActivity).load(foodThumb).into(binding.foodDetailImageView)
        binding.collapsingToolBar.title = foodName
    }

    private fun videoOnClick() {
        Log.d("video", "videoOnClick: $videoLink")
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(videoLink))
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.food_details_menu,menu)
        val actionButton = menu?.findItem(R.id.actionVideo)
        val color = ContextCompat.getColor(this, R.color.white)
        actionButton?.icon?.colorFilter = BlendModeColorFilterCompat.createBlendModeColorFilterCompat(color, BlendModeCompat.SRC_ATOP)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                return true
            }
            R.id.actionVideo -> {
                videoOnClick()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }



}