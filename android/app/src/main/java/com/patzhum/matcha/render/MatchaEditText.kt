package com.patzhum.matcha.render

import android.content.Context
import android.widget.EditText

/**
 * Created by patri on 2018-02-25.
 */
class MatchaEditText : MatchaView<EditText, MatchaEditText.State>() {

    override fun getInstance(context: Context): EditText {
        return EditText(context)
    }

    override val stateClass = State::class.java

    override fun render(view : EditText, state : State) {
        super.render(view, state)
        view.apply {
            setText(state.text)
            setTextColor(RenderUtil.tryParseColor(state.textColor, currentTextColor))
            textSize = RenderUtil.tryParseFloat(state.textSize, textSize)
        }
    }

    class State(
            val text : String? = null,
            val textColor: String? = null,
            val textSize: String? = null
    ) : MatchaView.State(EditText::class.java.simpleName)
}

