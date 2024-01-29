package com.aviral.talkbuzz.utils

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import com.aviral.talkbuzz.R
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class SharedPreferenceManager @Inject constructor(
    @ApplicationContext private val context: Context
) {

    private var preference: SharedPreferences = context.getSharedPreferences(
        context.getString(R.string.talk_buzz_shared_preference),
        MODE_PRIVATE
    )

    fun loginUserWithPassword(password: String): Boolean {
        val storedPassword = preference.getString(context.getString(R.string.key_shared_password), "")

        return (password == storedPassword)
    }


    fun isLogin(): Boolean {
        return preference.getBoolean(context.getString(R.string.key_is_login), false)
    }

    fun storeUserPassword(password: String) {
        val sharedPreferenceEditor = preference.edit()

        sharedPreferenceEditor.putString(context.getString(R.string.key_shared_password), password)
        sharedPreferenceEditor.putBoolean(context.getString(R.string.key_is_login), true)

        sharedPreferenceEditor.apply()

    }

}