package com.mkavaktech.reciperoamer.presentation.food_details

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.graphics.BlendModeColorFilterCompat
import androidx.core.graphics.BlendModeCompat
import com.bumptech.glide.Glide
import com.mkavaktech.reciperoamer.R
import com.mkavaktech.reciperoamer.data.entities.Meal
import com.mkavaktech.reciperoamer.databinding.ActivityFoodDetailsBinding
import com.mkavaktech.reciperoamer.utils.Constants
import dagger.hilt.android.AndroidEntryPoint
import es.dmoral.toasty.Toasty


@AndroidEntryPoint
class FoodDetailsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFoodDetailsBinding

    private val foodDetailsViewModel: FoodDetailsViewModel by viewModels()

    private lateinit var foodId: String
    private lateinit var foodName: String
    private lateinit var foodThumb: String
    private lateinit var videoLink: String

    private var foodForFavorite: Meal? = null
    private var isFoodFavorite: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFoodDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        getFoodInfoFromIntent()
        setupToolBarSettings()
        setFoodInfo()

        foodDetailsViewModel.getFoodDetails(foodId)
        foodDetailsViewModel.getFavoriteFood(foodId)

        observeFoodDetails()
        observeFavFood()
        favoriteOnClick()
    }

    private fun observeFavFood() {

        foodDetailsViewModel.foodFavoriteLiveData.observe(this) { food ->
            if (food != null) {
                isFoodFavorite = true
                binding.addFavBtn.setImageResource(R.drawable.ic_favorites_on)
            }
        }
    }

    private fun favoriteOnClick() {
        binding.addFavBtn.setOnClickListener {
            if (!isFoodFavorite) {
                foodForFavorite?.let {
                    foodDetailsViewModel.addToFavoriteFood(it)
                    isFoodFavorite = true
                    binding.addFavBtn.setImageResource(R.drawable.ic_favorites_on)

                    Toasty.success(
                        this,
                        getString(R.string.added_to_favorites_successfully),
                        Toast.LENGTH_SHORT,
                        true
                    ).show()
                }
            } else {
                foodForFavorite?.let {
                    foodDetailsViewModel.removeFavoriteFood(it)
                    isFoodFavorite = false
                    binding.addFavBtn.setImageResource(R.drawable.ic_favorites)
                    Toasty.warning(
                        this, getString(R.string.removed_from_favorites), Toast.LENGTH_SHORT, true
                    ).show()
                }
            }
        }
    }

    private fun setupToolBarSettings() {
        val toolbar = binding.toolBar
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        binding.collapsingToolBar.setCollapsedTitleTextColor(
            ContextCompat.getColor(
                this, R.color.white
            )
        )
    }

    private fun observeFoodDetails() {

        foodDetailsViewModel.foodDetailsLiveData.observe(this) { food ->
            "${getString(R.string.categories)}: ${food.strCategory}".also {
                binding.categoryTxtView.text = it
            }
            "${getString(R.string.cuisine)}: ${food.strArea}".also {
                binding.cuisineTxtView.text = it
            }
            binding.instrTxtView.text = food.strInstructions
            videoLink = food.strYoutube!!
            foodForFavorite = food
        }

    }

    private fun getFoodInfoFromIntent() {
        val intent = intent
        foodId = intent.getStringExtra(Constants.Details.FOOD_ID)!!
        foodName = intent.getStringExtra(Constants.Details.FOOD_NAME)!!
        foodThumb = intent.getStringExtra(Constants.Details.FOOD_THUMB)!!
    }

    private fun setFoodInfo() {
        Glide.with(this@FoodDetailsActivity).load(foodThumb).into(binding.foodDetailImageView)
        binding.collapsingToolBar.title = foodName
    }

    private fun videoOnClick() {
        if (videoLink.isBlank()) {
            Toasty.error(this, getString(R.string.video_not_found), Toast.LENGTH_SHORT, true).show()
        } else {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(videoLink))
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.food_details_menu, menu)
        val actionButton = menu?.findItem(R.id.actionVideo)
        val color = ContextCompat.getColor(this, R.color.white)
        actionButton?.icon?.colorFilter =
            BlendModeColorFilterCompat.createBlendModeColorFilterCompat(
                color, BlendModeCompat.SRC_ATOP
            )
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