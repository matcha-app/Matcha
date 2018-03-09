package com.patzhum.matcha.render.layouts

import android.content.Context
import android.widget.LinearLayout
import com.patzhum.matcha.render.core.MatchaView
import com.patzhum.matcha.render.core.MatchaViewGroup
import com.patzhum.matcha.render.core.RenderUtil

/**
 * Created by patri on 2018-02-28.
 */
class MatchaLinearLayout : MatchaViewGroup<LinearLayout, MatchaLinearLayout.State>() {
    override val stateClass = State::class.java

    override fun getInstance(context: Context): LinearLayout {
        return LinearLayout(context)
    }

    override fun render(view: LinearLayout, state: State) {
        super.render(view, state)
        view.apply {
            orientation = RenderUtil.tryParseOrientation(state.orientation, orientation)
        }
    }

    open class State(
        open val orientation : String? = null
    ) : MatchaViewGroup.State()
}