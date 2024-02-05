package com.aviral.talkbuzz.data.repository

import com.aviral.talkbuzz.domain.repository.TalkBuzzRepository
import com.aviral.talkbuzz.utils.Resource
import io.getstream.chat.android.client.ChatClient
import io.getstream.chat.android.client.call.await
import io.getstream.chat.android.client.models.User
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TalkBuzzRepositoryImplementation @Inject constructor(
    private val client: ChatClient
) : TalkBuzzRepository {


    override suspend fun connectUser(user: User, token: String): Flow<Resource<Boolean>> {

        return flow {
            emit(Resource.Loading(true))

            val result = client.connectUser(
                user = user,
                token = token
            ).await()

            if (result.isError) {
                emit(Resource.Loading(false))

                emit(
                    Resource.Error(message = result.error().message ?: "Unknown Error")
                )

                return@flow
            }

            emit(
                Resource.Success(data = true)
            )
            emit(Resource.Loading(false))

        }

    }


    override suspend fun createChannel(
        channelType: String,
        channelId: String,
        channelName: String
    ): Flow<Resource<Boolean>> {

        return flow {

            emit(Resource.Loading(true))

            val result = client.channel(
                channelType = channelType,
                channelId = channelId
            ).create(
                mapOf(
                    "name" to channelName
                )
            ).await()

            if (result.isError) {
                emit(Resource.Loading(false))

                emit(Resource.Error(
                    result.error().message ?: "Unknown Error"
                ))

                return@flow
            }

            emit(Resource.Loading(false))

            emit(
                Resource.Success(true)
            )

        }

    }

    override suspend fun getUser(): User? {
        return client.getCurrentUser()
    }

    override suspend fun logoutUser() {
        client.disconnect()
    }

}