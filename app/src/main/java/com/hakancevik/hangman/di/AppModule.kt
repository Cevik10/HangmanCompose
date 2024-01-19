package com.hakancevik.hangman.di

import android.app.Application
import com.hakancevik.hangman.presentation.game_home.GameViewModel
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.scopes.ViewModelScoped
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {


    @Singleton
    @Provides
    fun provideHangmanViewModel(): GameViewModel {
        return GameViewModel()
    }

}