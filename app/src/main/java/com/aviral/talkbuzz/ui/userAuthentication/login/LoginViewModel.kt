package com.aviral.talkbuzz.ui.userAuthentication.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aviral.talkbuzz.utils.Constants.isValidUsername
import com.aviral.talkbuzz.data.local.SharedPreferenceManager
import dagger.hilt.android.lifecycle.HiltViewModel
import io.getstream.chat.android.client.ChatClient
import io.getstream.chat.android.client.call.await
import io.getstream.chat.android.client.models.User
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val client: ChatClient,
    private val sharedPreferenceManager: SharedPreferenceManager
) : ViewModel() {

    private val _loginEvent = MutableSharedFlow<LoginEvent>()

    val loginEvent = _loginEvent.asSharedFlow()

//    private fun isValidUsername(username: String) =
//        username.length > MIN_USERNAME_LENGTH

    fun checkPasswordAndLoginUser(username: String, password: String) {
        viewModelScope.launch {
            val result = sharedPreferenceManager.checkUserPassword(username, password)

            if (!result) {
                _loginEvent.emit(LoginEvent.InvalidPassword)
            }

            sharedPreferenceManager.loginUser(username, password)

            connectUser(username, password)
        }
    }

    private fun connectUser(username: String, password: String) {
        val trimmedUsername = username.trim()

        viewModelScope.launch {
            if (isValidUsername(trimmedUsername)) {

                val result = client.connectGuestUser(
                    userId = trimmedUsername,
                    username = trimmedUsername
                ).await()

//                client.connectUser()
//                client.devToken()

                if (result.isError) {
                    _loginEvent.emit(
                        LoginEvent.ErrorLogin(
                            result.error().message
                                ?: "Unknown Error"
                        )
                    )

                    return@launch
                }

                sharedPreferenceManager.loginUser(username, password)

                _loginEvent.emit(LoginEvent.Success)

            } else {

                _loginEvent.emit(LoginEvent.ErrorInputTooShort)

            }
        }

    }

}