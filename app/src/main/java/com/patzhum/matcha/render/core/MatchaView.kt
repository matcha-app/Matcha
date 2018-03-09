package com.patzhum.matcha.render.core

import android.content.Context
import android.graphics.Color
import android.os.Build
import android.util.Log
import android.view.View
import android.view.ViewGroup

/**
 * Created by patri on 2018-02-25.
 */
abstract class MatchaView <ViewType : View, StateType : MatchaView.State> {
    open fun render(view: ViewType, state: StateType, context: Context) {
        val padding = RenderUtil.tryParseInt(state.padding, 0)
        var paddingLeft = padding
        var paddingRight = padding
        var paddingTop = padding
        var paddingBottom = padding

        paddingLeft = RenderUtil.tryParseInt(state.paddingHorizontal, paddingLeft)
        paddingTop = RenderUtil.tryParseInt(state.paddingVertical, paddingTop)
        paddingRight = RenderUtil.tryParseInt(state.paddingHorizontal, paddingRight)
        paddingBottom = RenderUtil.tryParseInt(state.paddingVertical, paddingBottom)

        paddingLeft = RenderUtil.tryParseInt(state.leftPadding, paddingLeft)
        paddingTop = RenderUtil.tryParseInt(state.topPadding, paddingTop)
        paddingRight = RenderUtil.tryParseInt(state.rightPadding, paddingRight)
        paddingBottom = RenderUtil.tryParseInt(state.bottomPadding, paddingBottom)
        
        view.setPadding(paddingLeft, paddingTop, paddingRight, paddingBottom)

        view.setPaddingRelative(
                RenderUtil.tryParseInt(state.startPadding, view.paddingLeft),
                RenderUtil.tryParseInt(state.topPadding, view.paddingTop),
                RenderUtil.tryParseInt(state.endPadding, view.paddingRight),
                RenderUtil.tryParseInt(state.bottomPadding, view.paddingBottom)
        )


        view.x = RenderUtil.tryParseFloat(state.x, view.x)
        view.y = RenderUtil.tryParseFloat(state.y, view.y)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            view.elevation = RenderUtil.tryParseFloat(state.elevation, view.elevation)
        }

        view.rotation = RenderUtil.tryParseFloat(state.rotation, view.rotation)
        view.rotationX = RenderUtil.tryParseFloat(state.rotationX, view.rotationX)
        view.rotationY = RenderUtil.tryParseFloat(state.rotationY, view.rotationY)

        view.overScrollMode = RenderUtil.tryParseInt(state.overScrollMode, view.overScrollMode)


        view.layoutParams = ViewGroup.LayoutParams(
                RenderUtil.tryParseLayoutSize(state.layoutWidth, ViewGroup.LayoutParams.WRAP_CONTENT),
                RenderUtil.tryParseLayoutSize(state.layoutHeight, ViewGroup.LayoutParams.WRAP_CONTENT))

        view.setBackgroundColor(RenderUtil.tryParseColor(state.backgroundColor, Color.TRANSPARENT))

    }
    abstract val stateClass : Class<StateType>
    abstract fun getInstance(context: Context) : ViewType

    open class State(
            open val type : String? = "View",

            open val leftPadding : String? = null,
            open val topPadding : String? = null,
            open val rightPadding : String? = null,
            open val bottomPadding : String? = null,
            open val startPadding : String? = null,
            open val endPadding : String? = null,
            open val padding : String? = null,
            open val paddingHorizontal: String? = null,
            open val paddingVertical : String? = null,

            open val x : String? = null,
            open val y : String? = null,

            open val elevation : String? = null,
            open val rotation : String? = null,
            open val rotationX : String? = null,
            open val rotationY : String? = null,

            open val overScrollMode : String? = null,
            open val layoutWidth: String? = null,
            open val layoutHeight: String? = null,

            open val backgroundColor: String? = null
    )
}