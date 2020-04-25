package com.hopovo.mobile.android.prepexam.app.common.util

import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Environment
import android.util.Base64
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

object PhotoUtils {

    const val IMAGE_TARGET_SIZE = 720
    const val IMAGE_COMPRESSION = 60

    @JvmStatic
    @Throws(IOException::class)
    fun createImageFile(): File {
        // Create an image file name
        val timeStamp: String? = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.ENGLISH).format(Date())
        val storageDir: File? = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
        return File.createTempFile(
                "ATENEA${timeStamp}_", /* prefix */
                ".jpg", /* suffix */
                storageDir /* directory */
        )
    }

    @JvmStatic
    fun getResizedPhoto(path: String, targetSize: Int): Bitmap? {
        val scaleFactor = BitmapUtils.getScaleFactor(path, targetSize)
        val originalBitmap = BitmapFactory.decodeFile(path)

        return originalBitmap?.let { bitmap ->
            BitmapUtils.getResizedBitmap(bitmap, Math.round(bitmap.width * scaleFactor), Math.round(bitmap.height * scaleFactor), false)
        }
    }

    fun encodeBase64(resources: Resources, bitmap: Bitmap): String {
        //encode image to base64 string
        val baos = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos)
        val imageBytes = baos.toByteArray()
        val imageString = Base64.encodeToString(imageBytes, Base64.DEFAULT)

        return imageString
    }

    fun decodeBase64(base64Str: String): Bitmap {
        //decode base64 string to image
        val imageBytes = Base64.decode(base64Str, Base64.DEFAULT)
        val decodedImage = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)

        return decodedImage
    }
}