package com.aviral.talkbuzz.domain.repository

import com.aviral.talkbuzz.utils.Resource
import io.getstream.chat.android.client.models.User
import kotlinx.coroutines.flow.Flow

interface TalkBuzzRepository {

    suspend fun connectUser(
        user: User,
        token: String = ""
    ) : Flow<Resource<Boolean>>

    suspend fun createChannel(
        channelType: String,
        channelId: String,
        channelName: String
    ) : Flow<Resource<Boolean>>

    suspend fun getUser() : User?

    suspend fun logoutUser()

}