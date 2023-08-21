package com.mkavaktech.reciperoamer.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.mkavaktech.reciperoamer.R
import com.mkavaktech.reciperoamer.databinding.ActivityMainBinding
import com.mkavaktech.reciperoamer.utils.Helper
import dagger.hilt.android.AndroidEntryPoint
import io.ak1.BubbleTabBar

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var bubbleTabBar: BubbleTabBar
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        bubbleTabBar = binding.bottomNavBar
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.navHostFragment) as NavHostFragment
        navController = navHostFragment.navController
        setupBottomNavBar()
    }

    private fun setupBottomNavBar() {
        bubbleTabBar.addBubbleListener { id ->
            Helper.onNavDestinationSelected(id,navController)
        }
        navController.addOnDestinationChangedListener { _, destination, _ ->
            bubbleTabBar.setSelectedWithId(destination.id, false)
        }
    }



}