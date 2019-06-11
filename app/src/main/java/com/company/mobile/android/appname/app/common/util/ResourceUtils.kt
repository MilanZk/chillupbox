package com.company.mobile.android.appname.app.common.util

import android.content.Context
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat

object ResourceUtils {

    val DRAWABLE_TYPE = "drawable"
    val STRING_TYPE = "string"

    //    public static int getDrawableResourceId(@NonNull final String resourceName) {
    //        int result = -1;
    //
    //        try {
    //            Class res = R.drawable.class;
    //            Field field = res.getField(resourceName);
    //            result = field.getInt(null);
    //        } catch (final Exception e) {
    //            Timber.e(e, "Drawable resource named \'%s\' was not found: %s", resourceName, e.getDebugMessage());
    //        }
    //
    //        return result;
    //    }

    @JvmStatic
    fun getResourceId(context: Context, name: String, defType: String): Int {
        return context.resources.getIdentifier(name, defType, context.packageName)
    }

    @JvmStatic
    fun getColorString(context: Context, @ColorRes colorId: Int): String {
        val resourceColorId = ContextCompat.getColor(context, colorId)
        val colorString = String.format("%X", resourceColorId)

        return if (colorString.length <= 6) colorString else colorString.substring(2) /*!!strip alpha value!!*/
    }
}
