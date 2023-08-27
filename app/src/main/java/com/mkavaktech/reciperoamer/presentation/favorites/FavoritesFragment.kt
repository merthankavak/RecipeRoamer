package com.mkavaktech.reciperoamer.presentation.favorites

import android.content.Intent
import android.graphics.Color
import android.os.Bundle

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels


import androidx.recyclerview.widget.LinearLayoutManager

import com.google.android.material.snackbar.Snackbar
import com.mkavaktech.reciperoamer.R

import com.mkavaktech.reciperoamer.data.entities.Meal

import com.mkavaktech.reciperoamer.databinding.FragmentFavouritesBinding
import com.mkavaktech.reciperoamer.presentation.food_details.FoodDetailsActivity
import com.mkavaktech.reciperoamer.utils.Constants
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class FavouritesFragment : Fragment(), FavoriteFoodsAdapter.FavoriteFoodsListener {
    private lateinit var binding: FragmentFavouritesBinding
    private val favoritesViewModel: FavoritesViewModel by viewModels()
    private lateinit var favoriteFoodsAdapter: FavoriteFoodsAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentFavouritesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        favoritesViewModel.getFavoriteFoods()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        observeFavFoodList()
    }

    private fun observeFavFoodList() {
        favoritesViewModel.favoritesFoodLiveData.observe(viewLifecycleOwner) { favFoodList ->
            favoriteFoodsAdapter.setFavFood(favFoodList as ArrayList<Meal>)
            checkNotFoundAnim()
        }
    }


    private fun setupRecyclerView() {
        favoriteFoodsAdapter = FavoriteFoodsAdapter(this)
        binding.favFoodsRecView.apply {
            layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
            adapter = favoriteFoodsAdapter
        }
    }

    private fun checkNotFoundAnim() {
        val favAnimationView = binding.favAnimationView
        if (favoriteFoodsAdapter.itemCount == 0) {
            favAnimationView.visibility = View.VISIBLE
            favAnimationView.playAnimation()
        } else {
            favAnimationView.cancelAnimation()
            favAnimationView.visibility = View.INVISIBLE
        }
    }

    override fun onFavoriteFoodsClick(meal: Meal) {
        val intent = Intent(activity, FoodDetailsActivity::class.java)
        intent.putExtra(Constants.Details.FOOD_ID, meal.idMeal)
        intent.putExtra(Constants.Details.FOOD_NAME, meal.strMeal)
        intent.putExtra(Constants.Details.FOOD_THUMB, meal.strMealThumb)
        startActivity(intent)
    }

    override fun onFavIconClick(adapterPosition: Int) {
        val deletedFood: Meal = favoriteFoodsAdapter.foodList()[adapterPosition]
        favoriteFoodsAdapter.removeItem(adapterPosition)
        Snackbar.make(
            binding.favFoodsRecView,
            "${getString(R.string.removed_from_favorites)}: " + deletedFood.strMeal,
            Snackbar.LENGTH_LONG
        ).setAction(
            getString(R.string.undo)
        ) {
            favoriteFoodsAdapter.addItem(adapterPosition, deletedFood)
            favoritesViewModel.addToFavoriteFood(deletedFood)
            checkNotFoundAnim()
        }.setBackgroundTint(Color.RED).setActionTextColor(Color.BLACK).show()

        favoritesViewModel.removeFavoriteFood(deletedFood)
        checkNotFoundAnim()
    }
}