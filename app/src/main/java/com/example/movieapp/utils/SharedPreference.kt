package com.example.movieapp.utils

import android.content.Context
import android.content.SharedPreferences

class SharedPreference {

    private val PREFS_NAME = AppConstants.PREFS_NAME
    private val PREFS_KEY = AppConstants.PREFS_KEY

    fun setValue(context: Context, value: Int){
        val preferences = context.getSharedPreferences(PREFS_NAME,Context.MODE_PRIVATE)
        val editor: SharedPreferences.Editor = preferences.edit()
        editor.putInt(PREFS_KEY, value)
        editor.apply()
    }

    fun getValue(context: Context):Int{
        val preferences = context.getSharedPreferences(PREFS_NAME,Context.MODE_PRIVATE)
        val key = preferences.getInt(PREFS_KEY,0)
        return key
    }
}