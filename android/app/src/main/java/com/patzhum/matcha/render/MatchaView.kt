package com.patzhum.matcha.render

import android.content.Context
import android.util.AttributeSet
import android.view.View
import java.lang.reflect.Type

/**
 * Created by patri on 2018-02-25.
 */
abstract class MatchaView <ViewType : View, StateType : RenderUtil.ViewState> {
    abstract fun render(view : ViewType, state : StateType)
    abstract val stateClass : Class<StateType>
    abstract fun getInstance(context: Context) : ViewType
}