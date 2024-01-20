package com.aviral.talkbuzz.ui.login

sealed class LoginEvent {
    object ErrorInputTooShort : LoginEvent()

    data class ErrorLogin(val error: String) : LoginEvent()

    object Success : LoginEvent()
}
