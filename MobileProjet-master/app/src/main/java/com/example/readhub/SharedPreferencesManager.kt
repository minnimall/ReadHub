package com.example.readhub

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import java.util.ResourceBundle.Control

class SharedPreferencesManager(context: Context) {
    private val  preferences:SharedPreferences =
        context.getSharedPreferences("MyPreferences", Context.MODE_PRIVATE)
    var isLoggedIn:Boolean
        get() = preferences.getBoolean(KEY_IS_LOGGED_IN, false)
        set(value) = preferences.edit().putBoolean(KEY_IS_LOGGED_IN, value).apply()

    var userId:String?
        get() = preferences.getString(KEY_USER_ID, null)
        set(value) = preferences.edit().putString(KEY_USER_ID, value).apply()

    var ControlShow:Boolean
        get() = preferences.getBoolean(KEY_ShowControl, false)
        set(value) = preferences.edit().putBoolean(KEY_ShowControl, value).apply()


    fun clearUserAll(){
        preferences.edit{remove(KEY_USER_ID)}
        preferences.edit{remove(KEY_IS_LOGGED_IN)}
        preferences.edit{remove(KEY_ShowControl)}

    }
    fun clearUserLogin(){
        preferences.edit{ remove(KEY_IS_LOGGED_IN) }
    }

    companion object{
        private const val KEY_IS_LOGGED_IN = "is_logged_in"
        private  const val KEY_USER_ID = "user_id"
        private const val KEY_ShowControl = "show_control"

    }
}