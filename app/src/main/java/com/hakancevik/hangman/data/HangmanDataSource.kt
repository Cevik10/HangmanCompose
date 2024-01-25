package com.hakancevik.hangman.data

import android.content.Context
import android.content.res.Configuration
import androidx.annotation.ArrayRes
import com.hakancevik.hangman.R
import java.util.Locale

class HangmanDataSource(private val context: Context) {

    fun getRandomCategory(): String {
        val allCategories = context.resources.getStringArray(R.array.all_categories)
        return allCategories.random()
    }


    fun setLanguage(languageCode: String) {
        val locale = Locale(languageCode)
        Locale.setDefault(locale)

        val configuration = Configuration(context.resources.configuration)
        configuration.setLocale(locale)

        context.resources.updateConfiguration(configuration, context.resources.displayMetrics)
    }


    fun getRandomWordByCategoryAndLanguage(category: String, languageCode: String): String {
        setLanguage(languageCode)

        val resourceId = when (category) {
            context.getString(R.string.category_fruit) -> R.array.words_fruit
            context.getString(R.string.category_animal) -> R.array.words_animal
            context.getString(R.string.category_country) -> R.array.words_country
            context.getString(R.string.category_color) -> R.array.words_color
            context.getString(R.string.category_profession) -> R.array.words_profession
            context.getString(R.string.category_sport) -> R.array.words_sport
            context.getString(R.string.category_vehicle) -> R.array.words_vehicle
            context.getString(R.string.category_music_genre) -> R.array.words_music_genre
            context.getString(R.string.category_movie_genre) -> R.array.words_movie_genre
            context.getString(R.string.category_food) -> R.array.words_food
            context.getString(R.string.category_body_part) -> R.array.words_body_part
            context.getString(R.string.category_weather) -> R.array.words_weather
            context.getString(R.string.category_instrument) -> R.array.words_instrument
            context.getString(R.string.category_emotion) -> R.array.words_emotion
            else -> 0
        }


        return if (resourceId != 0) {
            val wordsArray = context.resources.getStringArray(resourceId)
            wordsArray.randomOrNull() ?: ""
        } else {
            ""
        }
    }


}


//class HangmanDataSource(private val context: Context) {
//
//
////
////    fun getWordsByCategory(category: String): Set<String> {
////        val resourceId = when (category) {
////            context.getString(R.string.category_fruit) -> R.array.words_fruit
////            context.getString(R.string.category_animal) -> R.array.words_animal
////            context.getString(R.string.category_country) -> R.array.words_country
////            else -> 0
////        }
////
////        return if (resourceId != 0) {
////            context.resources.getStringArray(resourceId).toSet()
////        } else {
////            emptySet()
////        }
////    }
//
//
//    fun getRandomCategory(): String {
//        val allCategories = context.resources.getStringArray(R.array.all_categories)
//        return allCategories.random()
//    }
//
//    fun getRandomWord(): String {
//        val randomCategory = getRandomCategory()
//        return getRandomWordByCategory(randomCategory)
//    }
//
//
//
//     fun getRandomWordByCategory(category: String): String {
//        val resourceId = when (category) {
//            context.getString(R.string.category_fruit) -> R.array.words_fruit
//            context.getString(R.string.category_animal) -> R.array.words_animal
//            context.getString(R.string.category_country) -> R.array.words_country
//            else -> 0
//        }
//
//        return if (resourceId != 0) {
//            val wordsArray = context.resources.getStringArray(resourceId)
//            wordsArray.random()
//        } else {
//            ""
//        }
//    }
//
//
//
//}