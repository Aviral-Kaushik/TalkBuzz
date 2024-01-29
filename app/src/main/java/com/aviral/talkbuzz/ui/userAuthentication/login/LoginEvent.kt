package com.aviral.talkbuzz.ui.userAuthentication.login

sealed class LoginEvent {
    object ErrorInputTooShort : LoginEvent()

    data class ErrorLogin(val error: String) : LoginEvent()

    object Success : LoginEvent()

    object InvalidPassword : LoginEvent()
}
