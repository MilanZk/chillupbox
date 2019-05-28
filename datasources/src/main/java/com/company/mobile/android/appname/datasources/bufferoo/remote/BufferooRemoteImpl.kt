package com.company.mobile.android.appname.datasources.bufferoo.remote

import android.content.Context
import com.company.mobile.android.appname.data.bufferoo.source.BufferooDataStore
import com.company.mobile.android.appname.datasources.bufferoo.remote.mapper.BufferooEntityMapper
import com.company.mobile.android.appname.datasources.bufferoo.remote.model.PostCredentialsRequest
import com.company.mobile.android.appname.model.bufferoo.Bufferoo
import com.company.mobile.android.appname.model.bufferoo.SignedInBufferoo
import io.reactivex.Completable
import io.reactivex.Single
import java.lang.ref.WeakReference

/**
 * Remote implementation for retrieving Bufferoo instances. This class implements the
 * [BufferooRemote] from the Data layer as it is that layers responsibility for defining the
 * operations in which data store implementation layers can carry out.
 */
class BufferooRemoteImpl constructor(
    private val bufferooService: BufferooService,
    private val bufferooEntityMapper: BufferooEntityMapper,
    _context: Context
) : BufferooDataStore {

    private val contextWeakReference = WeakReference<Context>(_context)

    /**
     * Sign in using the [BufferooService].
     */
    override fun signIn(username: String, password: String): Single<SignedInBufferoo> {
        val signInRequest = PostCredentialsRequest(username, password)
        return bufferooService.postCredentials(signInRequest)
            .doOnSubscribe {
                // Before sign in, remove the old token, so that the authenticator does not find a token and it adds basic authentication header
                contextWeakReference.get()?.let { context ->
                    BufferooCredentialsWallet.deleteAccessToken(context)
                }
            }
            .map { signInResponse ->
                // Extract user id
                val userId = signInResponse.getId()
                val tokenExpirationTimestamp = signInResponse.getExpirationTimestamp()

                // Store credentials
                contextWeakReference.get()?.let { context ->
                    BufferooCredentialsWallet.setUsername(context, username)
                    BufferooCredentialsWallet.setId(context, userId)
                    BufferooCredentialsWallet.setAccessToken(context, signInResponse.access_token)
                    BufferooCredentialsWallet.setExpirationTimestamp(context, tokenExpirationTimestamp)
                    BufferooCredentialsWallet.setRefreshToken(context, signInResponse.refresh_token ?: "")
                }

                // Return result
                SignedInBufferoo(userId)
            }
    }

    /**
     * Retrieve a list of [Bufferoo] instances from the [BufferooService].
     */
    override fun getBufferoos(): Single<List<Bufferoo>> {
        return bufferooService.getBufferoos()
            .map { it.team }
            .map { bufferoos ->
                val entities = mutableListOf<Bufferoo>()
                bufferoos.forEach { bufferoo ->
                    entities.add(bufferooEntityMapper.mapFromRemote(bufferoo))
                }
                entities
            }
    }

    override fun clearBufferoos(): Completable {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun saveBufferoos(bufferoos: List<Bufferoo>): Completable {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun isValidCache(): Single<Boolean> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun setLastCacheTime(lastCache: Long) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}