package com.patzhum.matcha.render

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.widget.TextView

/**
 * Created by patri on 2018-02-22.
 */
class MatchaTextView @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : TextView(context, attrs, defStyleAttr) {

    fun render(state : State) {
        text = state.text
        setTextColor(RenderUtil.tryParseColor(state.textColor, currentTextColor))
        textSize = RenderUtil.tryParseFloat(state.textSize, textSize)
    }

    class State (
            val text : String? = null,
            val textColor: String? = null,
            val textSize: String? = null
    )
}

