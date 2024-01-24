package com.aviral.talkbuzz.ui.channels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.getstream.chat.android.client.ChatClient
import io.getstream.chat.android.client.call.await
import io.getstream.chat.android.client.models.User
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class ChannelViewModel @Inject constructor(
    private val client: ChatClient
) : ViewModel() {

    private val _createChannelEvent = MutableSharedFlow<CreateChannelEvent>()

    fun logout() {
        client.disconnect()
    }

    fun getUser(): User? {
        return client.getCurrentUser()
    }

    fun createChannel(channelName: String) {
        val trimmedChannelName = channelName.trim()
        if (trimmedChannelName.isEmpty()) {
            return
        }

        viewModelScope.launch {
            val result = client.channel(
                channelType = "messaging",
                channelId = UUID.randomUUID().toString()
            ).create(
                mapOf(
                    "name" to trimmedChannelName
//                    "image" to someImageUrl
                )
            ).await()

            if (result.isError) {
                return@launch
            }
        }
    }

}