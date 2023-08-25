package com.mkavaktech.reciperoamer.data.entities

import com.mkavaktech.reciperoamer.R

class OnboardingItem(val image: Int, val title: String, val description: String) {
    companion object {
        val itemList = arrayListOf(
            OnboardingItem(
                R.drawable.onboarding_img_1,
                "Recipe Roamer",
                "Learn cooking with video recipes and take your kitchen skills to the next level."
            ),
            OnboardingItem(
                R.drawable.onboarding_img_2,
                "Discover & Experience",
                "Discover a world of flavors with unique regional recipes."
            ),
            OnboardingItem(
                R.drawable.onboarding_img_3,
                "Favorite Recipes",
                "Explore delicious recipes, save your favorites, and dive into the joy of cooking inspiration."
            ),
        )
    }
}