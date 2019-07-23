package com.zgz.mobile.android.prepexam.datasources.bufferoo.remote

import android.content.Context
import android.text.TextUtils

object BufferooCredentialsWallet {

    private val API_KEY = BufferooCredentialsWallet::class.java.name
    private const val USERNAME_KEY = "username"
    private const val USERNAME_DEFAULT_VALUE = ""
    private const val ID_KEY = "id"
    private const val ID_DEFAULT_VALUE = ""
    private const val ACCESS_TOKEN_KEY = "accessToken"
    private const val ACCESS_TOKEN_DEFAULT_VALUE = ""
    private const val EXPIRATION_TIMESTAMP_KEY = "expirationTimestamp"
    private const val EXPIRATION_TIMESTAMP_DEFAULT_VALUE = 0L
    private const val REFRESH_TOKEN_KEY = "refreshToken"
    private const val REFRESH_TOKEN_DEFAULT_VALUE = ""

    fun getUsername(context: Context): String {
        return loadStringValue(context, API_KEY, USERNAME_KEY, USERNAME_DEFAULT_VALUE)
    }

    fun setUsername(context: Context, username: String) {
        saveStringValue(context, API_KEY, USERNAME_KEY, username)
    }

    fun getId(context: Context): String {
        return loadStringValue(context, API_KEY, ID_KEY, ID_DEFAULT_VALUE)
    }

    fun setId(context: Context, id: String) {
        saveStringValue(context, API_KEY, ID_KEY, id)
    }

    fun getAccessToken(context: Context): String {
        return loadStringValue(context, API_KEY, ACCESS_TOKEN_KEY, ACCESS_TOKEN_DEFAULT_VALUE)
    }

    fun setAccessToken(context: Context, accessToken: String) {
        saveStringValue(context, API_KEY, ACCESS_TOKEN_KEY, accessToken)
    }

    fun getExpirationTimestamp(context: Context): Long {
        return loadLongValue(context, API_KEY, EXPIRATION_TIMESTAMP_KEY, EXPIRATION_TIMESTAMP_DEFAULT_VALUE)
    }

    fun setExpirationTimestamp(context: Context, expirationTimestamp: Long) {
        saveLongValue(context, API_KEY, EXPIRATION_TIMESTAMP_KEY, expirationTimestamp)
    }

    fun getRefreshToken(context: Context): String {
        return loadStringValue(context, API_KEY, REFRESH_TOKEN_KEY, REFRESH_TOKEN_DEFAULT_VALUE)
    }

    fun setRefreshToken(context: Context, refreshToken: String) {
        saveStringValue(context, API_KEY, REFRESH_TOKEN_KEY, refreshToken)
    }

    fun areCredentialsAvailable(context: Context): Boolean {
        return !TextUtils.isEmpty(getAccessToken(context))
    }

    fun deleteAccessToken(context: Context) {
        deleteValue(context, API_KEY, ACCESS_TOKEN_KEY)
        deleteValue(context, API_KEY, EXPIRATION_TIMESTAMP_KEY)
    }

    fun deleteCredentials(context: Context) {
        deleteValue(context, API_KEY, USERNAME_KEY)
        deleteValue(context, API_KEY, ID_KEY)
        deleteValue(context, API_KEY, ACCESS_TOKEN_KEY)
        deleteValue(context, API_KEY, EXPIRATION_TIMESTAMP_KEY)
        deleteValue(context, API_KEY, REFRESH_TOKEN_KEY)
    }

    private fun deleteValue(context: Context, apiKey: String, dataKey: String) {
        val editor = context.getSharedPreferences(apiKey, Context.MODE_PRIVATE).edit()
        editor.remove(dataKey)
        editor.apply()
    }

    private fun loadStringValue(context: Context, apiKey: String, dataKey: String, defaultValue: String): String {
        val sharedPreferences = context.getSharedPreferences(apiKey, Context.MODE_PRIVATE)
        return sharedPreferences.getString(dataKey, defaultValue)!! // If default value cannot be null, this can be safely unwrapped
    }

    private fun saveStringValue(context: Context, apiKey: String, dataKey: String, value: String) {
        val editor = context.getSharedPreferences(apiKey, Context.MODE_PRIVATE).edit()
        editor.putString(dataKey, value)
        editor.apply()
    }

    private fun loadLongValue(context: Context, apiKey: String, dataKey: String, defaultValue: Long): Long {
        val sharedPreferences = context.getSharedPreferences(apiKey, Context.MODE_PRIVATE)
        return sharedPreferences.getLong(dataKey, defaultValue)
    }

    private fun saveLongValue(context: Context, apiKey: String, dataKey: String, value: Long) {
        val editor = context.getSharedPreferences(apiKey, Context.MODE_PRIVATE).edit()
        editor.putLong(dataKey, value)
        editor.apply()
    }
}
