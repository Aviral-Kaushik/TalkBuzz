package com.aviral.talkbuzz.di

import com.aviral.talkbuzz.data.repository.TalkBuzzRepositoryImplementation
import com.aviral.talkbuzz.domain.repository.TalkBuzzRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindTalkBuzzRepository(
        talkBuzzRepositoryImplementation: TalkBuzzRepositoryImplementation
    ) : TalkBuzzRepository

}