package com.mkavaktech.reciperoamer.data.entities

import com.mkavaktech.reciperoamer.R

class InfoItem(
    val iconResId: Int = 0,
    val title: String,
    val colorResId: Int = 0,
    val isHeader: Boolean = false
) {
    companion object {
        val itemList = arrayListOf(
            InfoItem(title = "App", isHeader = true),
            InfoItem(R.drawable.ic_tour, "Application Tour", R.color.cerise),
            InfoItem(R.drawable.ic_lock, "Privacy Policy", R.color.byzantium),
            InfoItem(R.drawable.ic_rate, "Rate the app", R.color.gold),
            InfoItem(title = "General", isHeader = true),
            InfoItem(R.drawable.ic_connect, "Connect with Me", R.color.brightBlue),
            InfoItem(R.drawable.ic_feedback, "Send feedback", R.color.black),
            InfoItem(title = "My Other App", isHeader = true),
            InfoItem(R.drawable.ic_app, "Daily Horoscopes", R.color.red),
        )
    }
}