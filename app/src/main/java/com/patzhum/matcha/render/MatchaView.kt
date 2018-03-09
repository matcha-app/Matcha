package com.patzhum.matcha.render

import android.content.Context
import android.view.View

/**
 * Created by patri on 2018-03-05.
 */
class MatchaView : MatchaBaseView<View, MatchaBaseView.State>() {
    override val stateClass: Class<State>
        get() = State::class.java

    override fun getInstance(context: Context): View {
        return View(context)
    }

}