package com.aviral.talkbuzz.ui.channels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.getstream.chat.android.client.ChatClient
import io.getstream.chat.android.client.call.await
import io.getstream.chat.android.client.models.User
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class ChannelViewModel @Inject constructor(
    private val client: ChatClient
) : ViewModel() {

    private val _createChannelEvent = MutableSharedFlow<CreateChannelEvent>()
    val createChannelEvent = _createChannelEvent.asSharedFlow()

    fun logout() {
        client.disconnect()
    }

    fun getUser(): User? {
        return client.getCurrentUser()
    }

    fun createChannel(channelName: String) {
        val trimmedChannelName = channelName.trim()

        viewModelScope.launch {

            if (trimmedChannelName.isEmpty()) {
                _createChannelEvent.emit(CreateChannelEvent.Error("Invalid Channel Name"))
                return@launch
            }

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
                Log.d("TalkCreateDialog", "createChannel: result: ${result.error().message}")
                _createChannelEvent.emit(
                    CreateChannelEvent.Error(result.error().message ?: "Unknown Error")
                )

                return@launch
            }

            _createChannelEvent.emit(CreateChannelEvent.Success)
        }
    }

}