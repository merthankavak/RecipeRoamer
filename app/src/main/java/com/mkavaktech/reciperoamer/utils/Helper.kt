package com.mkavaktech.reciperoamer.utils


import androidx.navigation.NavController
import androidx.navigation.NavOptions
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class Helper {
    companion object {
        fun onNavDestinationSelected(
            itemId: Int, navController: NavController
        ): Boolean {
            val builder = NavOptions.Builder().setLaunchSingleTop(true).setPopUpTo(itemId, true)

            return try {
                navController.navigate(itemId, null, builder.build())
                true
            } catch (e: IllegalArgumentException) {
                false
            }
        }

        fun <T> debounce(
            waitMs: Long = 300L, coroutineScope: CoroutineScope, destinationFunction: (T) -> Unit
        ): (T) -> Unit {
            var debounceJob: Job? = null
            return { param: T ->
                debounceJob?.cancel()
                debounceJob = coroutineScope.launch {
                    delay(waitMs)
                    destinationFunction(param)
                }
            }
        }
    }

}