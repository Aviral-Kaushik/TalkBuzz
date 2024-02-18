package com.aviral.talkbuzz.utils

object Constants {

    const val API_KEY = "nn43eq7emq9m"

    const val API_ID = "1273893"

    const val MIN_USERNAME_LENGTH = 3

    const val MIN_PASSWORD_LENGTH = 3

    const val MESSAGING_TYPE = "messaging"

    fun isValidUsername(username: String) = username.length > MIN_USERNAME_LENGTH

    fun isValidPassword(password: String) = password.length > MIN_PASSWORD_LENGTH

}