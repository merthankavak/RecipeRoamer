package com.mkavaktech.reciperoamer.presentation

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.mkavaktech.reciperoamer.R
import com.mkavaktech.reciperoamer.databinding.ActivityMainBinding
import com.mkavaktech.reciperoamer.presentation.onboarding.OnboardingActivity
import com.mkavaktech.reciperoamer.utils.Constants
import com.mkavaktech.reciperoamer.utils.Helper
import dagger.hilt.android.AndroidEntryPoint
import io.ak1.BubbleTabBar

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var bubbleTabBar: BubbleTabBar
    private lateinit var navController: NavController
    private lateinit var navHostFragment: NavHostFragment

    private lateinit var sharedPreferences: SharedPreferences


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)

        Thread.sleep(3000)
        installSplashScreen()

        setContentView(binding.root)

        sharedPreferences =
            getSharedPreferences(Constants.Onboarding.PREF_KEY_ONBOARDING, Context.MODE_PRIVATE)
        decideAndNavigate()

        navHostFragment =
            supportFragmentManager.findFragmentById(R.id.navHostFragment) as NavHostFragment
        navController = navHostFragment.navController

        setupBottomNavBar()
    }

    private fun decideAndNavigate() {
        if (!isOnboardingShown()) {
            startOnBoardingActivity()
        }
    }

    private fun isOnboardingShown(): Boolean {
        return sharedPreferences.getBoolean(Constants.Onboarding.PREF_KEY_ONBOARDING_SHOWN, false)
    }

    private fun setupBottomNavBar() {
        bubbleTabBar = binding.bottomNavBar
        bubbleTabBar.addBubbleListener { id ->
            Helper.onNavDestinationSelected(id, navController)
        }
        navController.addOnDestinationChangedListener { _, destination, _ ->
            bubbleTabBar.setSelectedWithId(destination.id, false)
        }
    }

    private fun startOnBoardingActivity() {
        val intent = Intent(this, OnboardingActivity::class.java)
        startActivity(intent)
        finish()
    }
}