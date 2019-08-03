package com.hopovo.mobile.android.prepexam.datasources.remote.authentication

/**
 * Provides an access token for request authorization.
 */
interface AccessTokenProvider {

    /**
     * Returns basic credentials (<client_id>:<client_secret>) encoded in Base64.
     */
    fun basicCredentials(): String

    /**
     * Returns an access token. In the event that you don't have a token return null.
     */
    fun token(): String?

    /**
     * Refreshes the token and returns it. This call should be made synchronously.
     * In the event that the token could not be refreshed return null.
     */
    fun refreshToken(): String?
}