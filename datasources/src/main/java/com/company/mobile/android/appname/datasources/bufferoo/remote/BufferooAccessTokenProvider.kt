package com.company.mobile.android.appname.datasources.bufferoo.remote

import android.content.Context
import com.company.mobile.android.appname.datasources.remote.authentication.AccessTokenProvider
import java.lang.ref.WeakReference
import java.nio.charset.Charset

class BufferooAccessTokenProvider(_context: Context): AccessTokenProvider {

    private val contextWeakReference = WeakReference<Context>(_context)

    override fun basicCredentials(): String {
        val credentials = "$CLIENT_ID:$CLIENT_SECRET".toByteArray(Charset.forName("UTF-8"))
        return android.util.Base64.encodeToString(credentials, android.util.Base64.NO_WRAP)
    }

    override fun token(): String? {
        return contextWeakReference.get()?.let { context ->
            BufferooCredentialsWallet.getAccessToken(context)
        }
    }

    override fun refreshToken(): String? {
        return null
    }

    companion object {
        private const val CLIENT_ID = "clean"
        private const val CLIENT_SECRET = "architecture"
    }
}