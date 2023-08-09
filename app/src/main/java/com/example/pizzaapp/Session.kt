package com.example.pizzaapp

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences

class Session (context: Context) {

    private val PREFS_NAME = "Session"

    private val preferences: SharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    var email: String?
        get() = preferences.getString(PREFS_NAME, "")
        set(value) = preferences.edit().putString(PREFS_NAME, value).apply()

}