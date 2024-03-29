package com.aviral.talkbuzz.di

import android.content.Context
import com.aviral.talkbuzz.utils.Constants.API_KEY
import com.aviral.talkbuzz.data.local.SharedPreferenceManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import io.getstream.chat.android.client.ChatClient
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideChatClient(@ApplicationContext context: Context) =
        ChatClient.Builder(API_KEY, context).build()

    @Singleton
    @Provides
    fun provideSharedPreferenceManager(@ApplicationContext context: Context) =
        SharedPreferenceManager(context)
}