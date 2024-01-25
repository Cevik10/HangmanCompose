package com.hakancevik.hangman.di

import android.content.Context
import com.hakancevik.hangman.data.AlphabetDataSource
import com.hakancevik.hangman.data.HangmanDataSource
import com.hakancevik.hangman.R
import com.hakancevik.hangman.presentation.game_home.GameViewModel
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {


    @Singleton
    @Provides
    fun provideGameViewModel(
        hangmanDataSource: HangmanDataSource
    ): GameViewModel {
        return GameViewModel(hangmanDataSource)
    }


    @Singleton
    @Provides
    fun provideHangmanDataSource(@ApplicationContext context: Context): HangmanDataSource {
        return HangmanDataSource(context)
    }

    @Singleton
    @Provides
    fun provideAlphabetDataSource(@ApplicationContext context: Context): AlphabetDataSource {
        return AlphabetDataSource(context, R.array.alphabet_array)
    }

}