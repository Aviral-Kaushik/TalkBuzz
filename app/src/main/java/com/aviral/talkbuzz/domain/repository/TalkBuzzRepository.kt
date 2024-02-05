package com.aviral.talkbuzz.domain.repository

import com.aviral.talkbuzz.utils.Resource
import io.getstream.chat.android.client.ChatClient
import io.getstream.chat.android.client.models.User
import kotlinx.coroutines.flow.Flow

interface TalkBuzzRepository {

    suspend fun connectUser(
        userId: String,
        token: String
    ) : Flow<Resource<Boolean>>

    suspend fun createChannel(
        channelType: String,
        channelId: String
    ) : Flow<Resource<Boolean>>

    suspend fun getUser(
        client: ChatClient
    ) : User?

    suspend fun logoutUser(
        client: ChatClient
    )

}