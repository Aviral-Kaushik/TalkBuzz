package com.aviral.talkbuzz.ui.userAuthentication.register

sealed class RegisterEvent {

    object Success : RegisterEvent()

    data class RegistrationFails(private val error: String) : RegisterEvent()

    object UsernameTooShort : RegisterEvent()

    object InvalidPassword : RegisterEvent()

}
