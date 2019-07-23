package com.zgz.mobile.android.prepexam.datasources.bufferoo.remote

import android.content.Context
import com.zgz.mobile.android.prepexam.data.bufferoo.source.BufferooDataStore
import com.zgz.mobile.android.prepexam.datasources.bufferoo.remote.mapper.BufferooEntityMapper
import com.zgz.mobile.android.prepexam.datasources.bufferoo.remote.model.PostCredentialsRequest
import com.zgz.mobile.android.prepexam.datasources.remote.errorhandling.RemoteExceptionMapper
import com.zgz.mobile.android.prepexam.model.bufferoo.Bufferoo
import com.zgz.mobile.android.prepexam.model.bufferoo.Credentials
import com.zgz.mobile.android.prepexam.model.bufferoo.SignedInBufferoo
import com.zgz.mobile.android.prepexam.model.bufferoo.SignedOutBufferoo
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
            .onErrorResumeNext { throwable ->
                // If remote request fails, use remote exception mapper to transform Retrofit exception to an app exception
                Single.error(RemoteExceptionMapper.getException(throwable))
            }
            .doOnSubscribe {
                // Before sign in, remove the old token, so that the authenticator does not find a token and it adds basic authentication header
                contextWeakReference.get()?.let { context ->
                    BufferooCredentialsWallet.deleteAccessToken(context)
                } ?: throw IllegalStateException("Context is null! Credentials cannot be deleted.")
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

                    // Return result
                    SignedInBufferoo(userId)
                } ?: throw IllegalStateException("Context is null! Credentials cannot be stored.")
            }
    }

    /**
     * Retrieve [Credentials] from the [BufferooService].
     */
    override fun getCredentials(): Single<Credentials> {
        return Single.defer {
            contextWeakReference.get()?.let { context ->
                Single.just(
                    Credentials(
                        BufferooCredentialsWallet.getUsername(context),
                        BufferooCredentialsWallet.getId(context)
                    )
                )
            } ?: Single.error(IllegalStateException("Context is null! Credentials cannot be loaded."))
        }
    }

    /**
     * Retrieve a list of [Bufferoo] instances from the [BufferooService].
     */
    override fun getBufferoos(): Single<List<Bufferoo>> {
        return bufferooService.getBufferoos()
            .onErrorResumeNext { throwable ->
                // If remote request fails, use remote exception mapper to transform Retrofit exception to an app exception
                Single.error(RemoteExceptionMapper.getException(throwable))
            }
            .map { it.items }
            .map { bufferoos ->
                val entities = mutableListOf<Bufferoo>()
                bufferoos.forEach { bufferoo ->
                    entities.add(bufferooEntityMapper.mapFromRemote(bufferoo))
                }
                entities
            }
    }

    /**
     * Signs out deleting all user credentials and access token.
     */
    override fun signOut(): Single<SignedOutBufferoo> {
        return Single.defer {
            contextWeakReference.get()?.let { context ->
                val id = BufferooCredentialsWallet.getId(context)
                BufferooCredentialsWallet.deleteCredentials(context)
                Single.just(SignedOutBufferoo(id))
            } ?: Single.error(IllegalStateException("Context is null! Credentials cannot be deleted."))
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