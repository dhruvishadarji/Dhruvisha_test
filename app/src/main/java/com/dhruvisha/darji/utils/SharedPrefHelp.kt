package com.dhruvisha.darji.utils

import android.content.Context
import android.content.SharedPreferences
import com.dhruvisha.darji.BuildConfig

import com.google.gson.Gson
import java.io.Serializable


object SharedPrefsHelp {
    private const val prefFileNmae =
        BuildConfig.APPLICATION_ID + ".pref"

    fun setString(context: Context, key: String?, value: String?) {
        val settings: SharedPreferences =
            context.getSharedPreferences(prefFileNmae, 0)
        val editor = settings.edit()
        editor.putString(key, value)
        editor.apply()
    }

    fun setObject(context: Context, key: String?, value: Serializable?) {
        val settings: SharedPreferences =
            context.getSharedPreferences(prefFileNmae, 0)
        val editor = settings.edit()
        editor.putString(key, Gson().toJson(value))
        editor.apply()
    }

    fun setBoolean(context: Context, key: String?, value: Boolean?) {
        val settings: SharedPreferences =
            context.getSharedPreferences(prefFileNmae, 0)
        val editor = settings.edit()
        editor.putBoolean(key, value!!)
        editor.apply()
    }

    fun getString(context: Context, key: String?, defValue: String?): String? {
        val settings: SharedPreferences =
            context.getSharedPreferences(prefFileNmae, 0)
        return settings.getString(key, defValue)
    }

    fun getBoolean(
        context: Context,
        key: String?,
        defValue: Boolean?
    ): Boolean {
        val settings: SharedPreferences =
            context.getSharedPreferences(prefFileNmae, 0)
        return settings.getBoolean(key, defValue!!)
    }

    fun getObject(context: Context, key: String?, defValue: String?): String? {
        val settings: SharedPreferences =
            context.getSharedPreferences(prefFileNmae, 0)
        return settings.getString(key, defValue)
    }

    fun clearSharedPrefs(context: Context) {
        val prefs: SharedPreferences = context.getSharedPreferences(prefFileNmae, 0)
        val editor = prefs.edit()
        editor.clear().apply()
    }
}