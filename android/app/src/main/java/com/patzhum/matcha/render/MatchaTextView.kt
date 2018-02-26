package com.patzhum.matcha.render

import android.content.Context
import android.util.AttributeSet
import android.widget.TextView

/**
 * Created by patri on 2018-02-22.
 */
class MatchaTextView : MatchaView<TextView, MatchaTextView.State>() {

    override fun getInstance(context: Context): TextView {
        return TextView(context)
    }

    override val stateClass = State::class.java

    override fun render(view : TextView, state : State) {
        view.apply {
            RenderUtil.renderBaseView(this, state)
            text = state.text
            setTextColor(RenderUtil.tryParseColor(state.textColor, currentTextColor))
            textSize = RenderUtil.tryParseFloat(state.textSize, textSize)
            textAlignment = RenderUtil.tryParseTextAlignment(state.textAlignment, textAlignment)
        }
    }

    class State(
            val text : String? = null,
            val textColor: String? = null,
            val textSize: String? = null,
            val textAlignment: String? = null
    ) : RenderUtil.ViewState (type = TextView::class.java.simpleName)
}

