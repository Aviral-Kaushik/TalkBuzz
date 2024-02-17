package com.aviral.talkbuzz.ui.userAuthentication.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aviral.talkbuzz.utils.Constants.isValidPassword
import com.aviral.talkbuzz.utils.Constants.isValidUsername
import com.aviral.talkbuzz.data.local.SharedPreferenceManager
import com.aviral.talkbuzz.domain.repository.TalkBuzzRepository
import com.aviral.talkbuzz.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import io.getstream.chat.android.client.models.User
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val sharedPreferenceManager: SharedPreferenceManager,
    private val talkBuzzRepository: TalkBuzzRepository
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

//            val result = client.connectGuestUser(
//                userId = username,
//                username = username
//            ).await()


            talkBuzzRepository.connectUser(
                User(id = username)
            ).collect {result ->
                when (result) {
                    is Resource.Success -> {
                        _registerEvent.emit(RegisterEvent.Success)

                        registerUser(username, password)
                    }

                    is Resource.Error -> {
                        _registerEvent.emit(RegisterEvent.RegistrationFails(result.message.toString()))
                    }

                    is Resource.Loading -> {
                        _registerEvent.emit(RegisterEvent.Loading(result.isLoading))
                    }
                }
            }

//            if (result.isError) {
//                _registerEvent.emit(
//                    RegisterEvent.RegistrationFails(
//                        result.error().message ?: "Unknown Error"
//                    )
//                )
//                return@launch
//            }

//            _registerEvent.emit(RegisterEvent.Success)

//            registerUser(username, password)
        }

    }

}