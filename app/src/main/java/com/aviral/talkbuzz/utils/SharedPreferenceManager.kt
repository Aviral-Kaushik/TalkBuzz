package com.aviral.talkbuzz.utils

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import com.aviral.talkbuzz.R

class SharedPreferenceManager constructor(
    private val context: Context
) {

    private val preference: SharedPreferences by lazy {
        context.getSharedPreferences(
            context.getString(R.string.talk_buzz_shared_preference),
            MODE_PRIVATE
        )
    }

    fun checkUserPassword(password: String): Boolean {
        val storedPassword = preference.getString(context.getString(R.string.key_shared_password), "")

        return (password == storedPassword)
    }


    fun isLogin(): Boolean {
        return preference.getBoolean(context.getString(R.string.key_is_login), false)
    }

    fun loginUser(password: String) {
        val sharedPreferenceEditor = preference.edit()

        sharedPreferenceEditor.putString(context.getString(R.string.key_shared_password), password)
        sharedPreferenceEditor.putBoolean(context.getString(R.string.key_is_login), true)

        sharedPreferenceEditor.apply()

    }

}