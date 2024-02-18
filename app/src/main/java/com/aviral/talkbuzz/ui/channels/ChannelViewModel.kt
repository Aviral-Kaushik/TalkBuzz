package com.aviral.talkbuzz.ui.channels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aviral.talkbuzz.domain.repository.TalkBuzzRepository
import com.aviral.talkbuzz.utils.Constants.MESSAGING_TYPE
import com.aviral.talkbuzz.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import io.getstream.chat.android.client.models.User
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class ChannelViewModel @Inject constructor(
    private val talkBuzzRepository: TalkBuzzRepository
) : ViewModel() {

    private val _createChannelEvent = MutableSharedFlow<CreateChannelEvent>()
    val createChannelEvent = _createChannelEvent.asSharedFlow()

    fun logout() {
//        client.disconnect()
        talkBuzzRepository.logoutUser()
    }

    fun getUser(): User? {
//        return client.getCurrentUser()
        return talkBuzzRepository.getUser()
    }

    fun createChannel(channelName: String) {
        val trimmedChannelName = channelName.trim()

        viewModelScope.launch {

            if (trimmedChannelName.isEmpty()) {
                _createChannelEvent.emit(CreateChannelEvent.Error("Invalid Channel Name"))
                return@launch
            }

            talkBuzzRepository.createChannel(
                channelType = MESSAGING_TYPE,
                channelId = UUID.randomUUID().toString(),
                channelName = trimmedChannelName
            ).collect {result ->
                when (result) {

                    is Resource.Success -> {
                        _createChannelEvent.emit(CreateChannelEvent.Success)
                    }

                    is Resource.Loading -> {
                        _createChannelEvent.emit(CreateChannelEvent.Loading(result.isLoading))
                    }

                    is Resource.Error -> {
                        _createChannelEvent.emit(
                            CreateChannelEvent.Error(result.message ?: "Unknown Error")
                        )
                    }

                }
            }

//            val result = client.channel(
//                channelType = "messaging",
//                channelId = UUID.randomUUID().toString()
//            ).create(
//                mapOf(
//                    "name" to trimmedChannelName
////                    "image" to someImageUrl
//                )
//            ).await()

//            if (result.isError) {
//                Log.d("TalkCreateDialog", "createChannel: result: ${result.error().message}")
//                _createChannelEvent.emit(
//                    CreateChannelEvent.Error(result.error().message ?: "Unknown Error")
//                )
//
//                return@launch
//            }
//
//            _createChannelEvent.emit(CreateChannelEvent.Success)
        }
    }

}