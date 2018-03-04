package com.patzhum.matcha.render.core

import android.content.Context
import android.util.Log
import android.view.ViewGroup
import com.google.gson.Gson
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import com.patzhum.matcha.render.text.MatchaTextView

/**
 * Created by patri on 2018-02-28.
 */

abstract class MatchaViewGroup<ViewType : ViewGroup, StateType : MatchaViewGroup.State> : MatchaView<ViewType, StateType>() {

    override fun render(view: ViewType, state: StateType, context: Context) {
        super.render(view, state, context)
        val gson = Gson()
        view.apply {
            for (childStateObject in state.children) {
                Log.d("ChildState", childStateObject.toString())
                val childView = RenderUtil.renderView(childStateObject.toString(), context)
                if (childView != null) {
                    addView(childView)
                } else {
                    Log.e("MatchaViewGroup", "Warning: A child is not being rendered!");
                }
            }
        }
    }

    open class State (
        val children : Array<JsonObject> = arrayOf()
    ) : MatchaView.State()

}