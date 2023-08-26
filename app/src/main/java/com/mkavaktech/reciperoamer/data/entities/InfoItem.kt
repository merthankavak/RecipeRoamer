package com.mkavaktech.reciperoamer.data.entities


data class InfoItem(
    val iconResId: Int = 0,
    val title: String,
    val colorResId: Int = 0,
    val isHeader: Boolean = false
)