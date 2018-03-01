package com.patzhum.matcha.render

import android.content.Context
import android.graphics.Color
import android.os.Build
import android.view.View
import android.view.ViewGroup

/**
 * Created by patri on 2018-02-25.
 */
abstract class MatchaView <ViewType : View, StateType : MatchaView.State> {
    open fun render(view : ViewType, state : StateType) {
        state.padding?.let {
            val p = RenderUtil.tryParseInt(it, -1)
            view.setPadding(p, p, p, p)
            view.setPadding(
                    RenderUtil.tryParseInt(state.paddingHorizontal, view.paddingLeft),
                    RenderUtil.tryParseInt(state.paddingVertical, view.paddingTop),
                    RenderUtil.tryParseInt(state.paddingHorizontal, view.paddingRight),
                    RenderUtil.tryParseInt(state.paddingVertical, view.paddingBottom)
            )
            view.setPadding(
                    RenderUtil.tryParseInt(state.leftPadding, view.paddingLeft),
                    RenderUtil.tryParseInt(state.topPadding, view.paddingTop),
                    RenderUtil.tryParseInt(state.rightPadding, view.paddingRight),
                    RenderUtil.tryParseInt(state.bottomPadding, view.paddingBottom)
            )

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
    }
    abstract val stateClass : Class<StateType>
    abstract fun getInstance(context: Context) : ViewType

    open class State(
            val type : String? = "View",

            val leftPadding : String? = null,
            val topPadding : String? = null,
            val rightPadding : String? = null,
            val bottomPadding : String? = null,
            val startPadding : String? = null,
            val endPadding : String? = null,
            val padding : String? = null,
            val paddingHorizontal: String? = null,
            val paddingVertical : String? = null,

            val x : String? = null,
            val y : String? = null,

            val elevation : String? = null,
            val rotation : String? = null,
            val rotationX : String? = null,
            val rotationY : String? = null,

            val overScrollMode : String? = null,
            val layoutWidth: String? = null,
            val layoutHeight: String? = null,

            val backgroundColor: String? = null
    )
}