package com.dhruvisha.darji

object ActivityHelper {
    fun isValidMail(email: String): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    fun isPasswordValid(stringPassword: String): Boolean {
        return stringPassword.length >= 6
    }
}