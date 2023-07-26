package com.mkavaktech.reciperoamer.utils
import androidx.navigation.NavController
import androidx.navigation.NavOptions

class Helper {
    companion object {
        fun onNavDestinationSelected(
            itemId: Int,
            navController: NavController
        ): Boolean {
            val builder = NavOptions.Builder()
                .setLaunchSingleTop(true)
                .setPopUpTo(itemId,true)

            return try {
                navController.navigate(itemId, null, builder.build())
                true
            } catch (e: IllegalArgumentException) {
                false
            }
        }
    }

}