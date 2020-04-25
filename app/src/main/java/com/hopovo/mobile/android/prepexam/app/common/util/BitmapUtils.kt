package com.hopovo.mobile.android.prepexam.app.common.util

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.util.Base64
import androidx.annotation.IntRange
import java.io.ByteArrayOutputStream

/**
 * [Bitmap] utility methods to resize images and serialize/deserialize them into [String].
 */
object BitmapUtils {

    /**
     * Converts a [Bitmap] image into a [String] encoded in Base64 using as [android.graphics.Bitmap.CompressFormat] JPEG.
     */
    @JvmStatic
    fun fromBitmapToString(bitmap: Bitmap, compression: Int): String {
        return fromBitmapToString(bitmap, Bitmap.CompressFormat.JPEG, compression)
    }

    /**
     * Converts a [Bitmap] image into a [String] encoded in Base64.
     */
    @JvmStatic
    fun fromBitmapToString(bitmap: Bitmap, compressFormat: Bitmap.CompressFormat, compression: Int): String {

        // Convert the bitmap to a byte array
        val byteArrayOutputStream = ByteArrayOutputStream()
        bitmap.compress(compressFormat, compression, byteArrayOutputStream)
        val byteArray = byteArrayOutputStream.toByteArray()
        // Convert byte array to string
        return Base64.encodeToString(byteArray, Base64.DEFAULT)
    }

    /**
     * Converts an [String] representing an image encoded in Base64 into a [Bitmap] image.
     */
    @JvmStatic
    fun fromStringToBitmap(encodedBitmap: String): Bitmap {
        val decodedString = Base64.decode(encodedBitmap, Base64.DEFAULT)
        return BitmapFactory.decodeByteArray(decodedString, 0, decodedString.size)
    }

    /**
     * Resizes a [Bitmap] image using a matrix, which seems to produce better results than
     * other methods. There is only one restriction for sizes, they must be greater than zero.
     * Therefore, aspect ratio is not warranted to be preserved.
     * IMPORTANT: This function returns a new bitmap, so the input bitmap can be safely
     * [Bitmap.recycle]d. However, note that this approach may increase memory usage.
     */
    @JvmStatic
    fun getResizedBitmap(bitmap: Bitmap, @IntRange(from = 1) newWidth: Int, @IntRange(from = 1) newHeight: Int, copy: Boolean): Bitmap {
        val width = bitmap.width
        val height = bitmap.height
        val scaleWidth = newWidth.toFloat() / width
        val scaleHeight = newHeight.toFloat() / height

        if (newHeight > 0 && newWidth > 0) {
            // Create matrix for the manipulation
            val matrix = Matrix()
            // Add resize to the matrix
            matrix.postScale(scaleWidth, scaleHeight)
            // Resize the bitmap
            val resizedBitmap = Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix, false)
            return if (copy) {
                // resizedBitmap is a bitmap pointing to the same image buffer of input "bitmap" but with a
                // new configuration. Therefore, it is a must to create a copy.
                resizedBitmap.copy(resizedBitmap.config, false)
            } else {
                resizedBitmap
            }
        } else {
            throw IllegalArgumentException("New dimensions contain sizes with zero or negative values. Height: $newHeight width: $newWidth")
        }
    }

    @JvmStatic
    fun getScaleFactor(photoPath: String, targetSize: Int): Float {
        var scaleFactor = 1.0f

        BitmapFactory.Options().apply {
            // Get the dimensions of the bitmap
            inJustDecodeBounds = true
            BitmapFactory.decodeFile(photoPath, this)
            val photoW: Int = outWidth
            val photoH: Int = outHeight

            // Determine how much to scale down the image
            scaleFactor = Math.min(1.0f, targetSize.toFloat() / Math.min(photoW, photoH).toFloat())
        }

        return scaleFactor
    }
}

