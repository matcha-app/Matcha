package com.patzhum.matcha.render

import android.graphics.Color
import android.support.v4.graphics.ColorUtils

/**
 * Created by patri on 2018-02-22.
 */
class RenderUtil {
    companion object {
        fun tryParseColor(string : String?, defaultColor : Int = Color.BLACK) : Int {
            string ?: return defaultColor
            return Color.parseColor(string)
        }
        fun tryParseFloat(string : String?, defaultValue : Float = 0f) : Float {
            string ?: return defaultValue
            return string.toFloatOrNull() ?: defaultValue
        }
    }
}