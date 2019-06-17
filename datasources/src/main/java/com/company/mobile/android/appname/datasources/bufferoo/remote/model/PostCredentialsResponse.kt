package com.company.mobile.android.appname.datasources.bufferoo.remote.model

import com.company.mobile.android.appname.datasources.remote.authentication.JWTUtils
import com.google.gson.annotations.SerializedName
import org.json.JSONException
import timber.log.Timber
import java.io.UnsupportedEncodingException

// JSON Web Token. See: https://jwt.io/introduction/
data class PostCredentialsResponse(
    @SerializedName("access_token")
    val access_token: String,
    @SerializedName("token_type")
    val token_type: String,
    @SerializedName("expires_in")
    val expires_in: Long,
    @SerializedName("refresh_token")
    val refresh_token: String? = "",
    @SerializedName("scope")
    val scope: String? = ""
) {

    fun getId(): String {
        return try {
            JWTUtils.getFieldFromBody(access_token, "id")
        } catch (e: UnsupportedEncodingException) {
            Timber.e(e, "JWT charset is not UTF-8")
            ""
        } catch (e: JSONException) {
            Timber.e(e, "Id inside JWT cannot be extracted")
            ""
        }
    }

    fun getExpirationTimestamp(): Long {
        return try {
            JWTUtils.getFieldFromBody(access_token, "exp").toLong()
        } catch (e: UnsupportedEncodingException) {
            Timber.e(e, "JWT charset is not UTF-8")
            0L
        } catch (e: JSONException) {
            Timber.e(e, "Expiration timestamp inside JWT cannot be extracted")
            0L
        } catch (e: NumberFormatException) {
            Timber.e(e, "Expiration timestamp inside JWT cannot be converted to number")
            0L
        }
    }
}
