package com.nativedevps.support.utility.image

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.util.Base64
import java.io.*


object Utility {
    @Throws(OutOfMemoryError::class)
    fun Context.getBitmap(uri: Uri): Bitmap? {
        var inputStream: InputStream? = null
        try {
            inputStream = contentResolver.openInputStream(uri);
            return BitmapFactory.decodeStream(inputStream)
        } finally {
            inputStream?.close()
        }
    }

    fun Bitmap.toBase64(): String? {
        var byteArrayOutputStream: ByteArrayOutputStream? = null
        try {
            byteArrayOutputStream = ByteArrayOutputStream()
            compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream)
            val bytes: ByteArray = byteArrayOutputStream.toByteArray()
            return Base64.encodeToString(bytes, Base64.DEFAULT)
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            byteArrayOutputStream?.close()
        }
        return null
    }

    // convert from bitmap to byte array
    fun getBytes(bitmap: Bitmap): ByteArray? {
        val stream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 0, stream)
        return stream.toByteArray()
    }

    // convert from byte array to bitmap
    fun getImage(image: ByteArray): Bitmap? {
        return BitmapFactory.decodeByteArray(image, 0, image.size)
    }

    fun BitMapToString(bitmap: Bitmap): String? {
        val baos = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 50, baos)
        val b = baos.toByteArray()
        return Base64.encodeToString(b, Base64.DEFAULT)
    }

    fun StringToBitMap(encodedString: String?): Bitmap? {
        return try {
            val encodeByte =
                Base64.decode(encodedString, Base64.DEFAULT)
            BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.size)
        } catch (e: java.lang.Exception) {
            e.message
            null
        }
    }

    fun saveBitmap(bm: Bitmap, filePath: String?): Boolean {
        val f = File(filePath)
        if (f.exists()) {
            f.delete()
        }
        return try {
            val out = FileOutputStream(f)
            bm.compress(Bitmap.CompressFormat.JPEG, 90, out)
            out.flush()
            out.close()
            true
        } catch (var4: FileNotFoundException) {
            var4.printStackTrace()
            false
        } catch (var5: IOException) {
            var5.printStackTrace()
            false
        }
    }

}

@Throws(OutOfMemoryError::class)
fun Context.getResizedBitmap(uri: Uri, reqWidth: Int, reqHeight: Int): Bitmap? {
    var inputStream: InputStream? = null
    return try {
        inputStream = contentResolver.openInputStream(uri)
        val options = BitmapFactory.Options().apply {
            // First decode with inJustDecodeBounds=true to check dimensions
            inJustDecodeBounds = true
            BitmapFactory.decodeStream(inputStream, null, this)

            // Calculate inSampleSize
            inSampleSize = calculateInSampleSize(this, reqWidth, reqHeight)

            // Decode bitmap with inSampleSize set
            inJustDecodeBounds = false
        }
        // Reset the input stream to decode again
        inputStream?.close()
        inputStream = contentResolver.openInputStream(uri)
        BitmapFactory.decodeStream(inputStream, null, options)
    } finally {
        inputStream?.close()
    }
}

private fun calculateInSampleSize(
    options: BitmapFactory.Options, reqWidth: Int, reqHeight: Int
): Int {
    // Raw height and width of image
    val height = options.outHeight
    val width = options.outWidth
    var inSampleSize = 1

    if (height > reqHeight || width > reqWidth) {
        val halfHeight: Int = height / 2
        val halfWidth: Int = width / 2

        // Calculate the largest inSampleSize value that is a power of 2 and keeps both
        // height and width larger than the requested height and width.
        while ((halfHeight / inSampleSize) >= reqHeight && (halfWidth / inSampleSize) >= reqWidth) {
            inSampleSize *= 2
        }
    }

    return inSampleSize
}
