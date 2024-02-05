package com.aviral.talkbuzz.ui.userAuthentication.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aviral.talkbuzz.utils.Constants.isValidPassword
import com.aviral.talkbuzz.utils.Constants.isValidUsername
import com.aviral.talkbuzz.data.local.SharedPreferenceManager
import dagger.hilt.android.lifecycle.HiltViewModel
import io.getstream.chat.android.client.ChatClient
import io.getstream.chat.android.client.call.await
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val client: ChatClient,
    private val sharedPreferenceManager: SharedPreferenceManager
) : ViewModel() {

    private val _registerEvent = MutableSharedFlow<RegisterEvent>()

    val registerEvent = _registerEvent

    private suspend fun registerUser(username: String, password: String) {

        if (!isValidUsername(username)) {
            _registerEvent.emit(RegisterEvent.UsernameTooShort)
            return
        }

        if (!isValidPassword(password)) {
            _registerEvent.emit(RegisterEvent.InvalidPassword)
            return
        }

        sharedPreferenceManager.loginUser(username, password)

        connectUser(username, password)

    }

    fun connectUser(username: String, password: String) {

        viewModelScope.launch {

            val result = client.connectGuestUser(
                userId = username,
                username = username
            ).await()

            if (result.isError) {
                _registerEvent.emit(
                    RegisterEvent.RegistrationFails(
                        result.error().message ?: "Unknown Error"
                    )
                )
                return@launch
            }

            _registerEvent.emit(RegisterEvent.Success)


            registerUser(username, password)
        }

    }

}