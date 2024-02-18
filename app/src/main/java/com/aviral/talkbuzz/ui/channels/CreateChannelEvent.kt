package com.aviral.talkbuzz.ui.channels

sealed class CreateChannelEvent {

    data class Error(val error: String) : CreateChannelEvent()

    object Success : CreateChannelEvent()

    data class Loading(val isLoading: Boolean) : CreateChannelEvent()

}
