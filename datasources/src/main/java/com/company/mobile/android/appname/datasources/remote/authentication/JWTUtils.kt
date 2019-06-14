package com.company.mobile.android.appname.datasources.remote.authentication

import android.util.Base64
import org.json.JSONException
import org.json.JSONObject
import timber.log.Timber
import java.io.UnsupportedEncodingException
import java.nio.charset.Charset

object JWTUtils {

    private val CHARSET = Charset.forName("UTF-8")

    @Throws(UnsupportedEncodingException::class)
    fun getJWTBody(JWTEncoded: String): String {
        val split = JWTEncoded.split("\\.".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
        return getJSON(split[1])
    }

    @Throws(UnsupportedEncodingException::class)
    fun logJWT(JWTEncoded: String) {
        val split = JWTEncoded.split("\\.".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
        Timber.d("JWT Header: %s", getJSON(split[0]))
        Timber.d("JWT Body: %s", getJSON(split[1]))
    }

    @Throws(UnsupportedEncodingException::class, JSONException::class)
    fun getFieldFromBody(JWTEncoded: String, field: String): String {
        val serializedJWTBody = getJWTBody(JWTEncoded)
        val jsonJWTBody = JSONObject(serializedJWTBody)
        return jsonJWTBody.getString(field)
    }

    @Throws(UnsupportedEncodingException::class)
    private fun getJSON(strEncoded: String): String {
        val decodedBytes = Base64.decode(strEncoded, Base64.URL_SAFE)
        return String(decodedBytes, CHARSET)
    }
}