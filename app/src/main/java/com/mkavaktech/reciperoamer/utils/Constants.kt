package com.mkavaktech.reciperoamer.utils

class Constants {
    companion object {
        const val APP_NAME = "Recipe Roamer"
        private const val PACKAGE_NAME = "com.mkavaktech.reciperoamer"
    }

    object Database {
        const val TABLE_NAME = "meals"
        const val STR_AREA = "str_area"
        const val STR_CATEGORY = "str_category"
        const val STR_INSTRUCTIONS = "str_instructions"
        const val STR_MEAL = "str_meal"
        const val STR_MEAL_THUMB = "str_meal_thumb"
        const val STR_YOUTUBE = "str_youtube"
    }

    object Network {
        const val BASE_URL = "https://www.themealdb.com/api/json/v1/1/"
        const val RANDOM = "random.php"
        const val LOOKUP = "lookup.php"
        const val FILTER = "filter.php"
        const val CATEGORIES = "categories.php"
        const val SEARCH = "search.php"
    }

    object Onboarding {
        const val PREF_KEY_ONBOARDING = "onboarding"
        const val PREF_KEY_ONBOARDING_SHOWN = "onboarding_shown"
    }

    object Details {
        const val FOOD_ID: String = "$PACKAGE_NAME.foodId"
        const val FOOD_NAME: String = "$PACKAGE_NAME.foodName"
        const val FOOD_THUMB: String = "$PACKAGE_NAME.foodThumb"
        const val CATEGORY_NAME: String = "$PACKAGE_NAME.categoryName"
    }

    object InfoItems {
        const val CONNECT_WITH_ME: String = "https://bento.me/merthan"
        const val PRIVACY_POLICY: String =
            "https://docs.google.com/document/d/1WhKS0CF5l4xcbcNy49nW-hrrhQ4GdFd3fNYv0keY9nw"
        const val DAILY_HOROSCOPES_PACKAGE_NAME: String = "com.mkavaktech.dailyhoroscopeapp"
        const val EMAIL_ADDRESS: String = "merthan.kavak@gmail.com"
        const val EMAIL_SUBJECT: String = "Recipe Roamer Feedback"
    }
}