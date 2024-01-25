package com.hakancevik.hangman.data

import android.content.Context
import androidx.annotation.ArrayRes

class AlphabetDataSource(private val context: Context, @ArrayRes private val alphabetArrayRes: Int) {

    fun getAlphabetList(): List<String> {
        return context.resources.getStringArray(alphabetArrayRes).toList()
    }
}