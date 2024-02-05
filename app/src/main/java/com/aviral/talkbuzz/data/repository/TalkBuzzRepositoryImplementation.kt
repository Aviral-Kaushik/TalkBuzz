package com.aviral.talkbuzz.data.repository

import com.aviral.talkbuzz.domain.repository.TalkBuzzRepository
import com.aviral.talkbuzz.utils.Resource
import io.getstream.chat.android.client.ChatClient
import io.getstream.chat.android.client.models.User
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TalkBuzzRepositoryImplementation @Inject constructor(
    client: ChatClient
) : TalkBuzzRepository {
    override suspend fun connectUser(userId: String, token: String): Flow<Resource<Boolean>> {

    }

    override suspend fun createChannel(
        channelType: String,
        channelId: String
    ): Flow<Resource<Boolean>> {

    }

    override suspend fun getUser(client: ChatClient): User? {

    }

    override suspend fun logoutUser(client: ChatClient) {

    }

}