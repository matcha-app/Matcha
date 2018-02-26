package com.patzhum.matcha.render

import android.content.Context
import android.graphics.Color
import android.os.Build
import android.support.v4.graphics.ColorUtils

import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import com.google.gson.Gson
import kotlin.reflect.KClass
import com.google.gson.JsonSyntaxException
import com.google.gson.stream.MalformedJsonException

/**
 * Created by patri on 2018-02-22.
 */
class RenderUtil {
    open class ViewState (
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
            val layoutHeight: String? = null
    )
    companion object {
        private val LOG_TAG = RenderUtil::class.java.simpleName

        fun renderBaseView(view : View, state : ViewState) {
            state.padding?.let {
                val p = tryParseInt(it, -1)
                view.setPadding(p, p, p, p)
                view.setPadding(
                        tryParseInt(state.paddingHorizontal, view.paddingLeft),
                        tryParseInt(state.paddingVertical, view.paddingTop),
                        tryParseInt(state.paddingHorizontal, view.paddingRight),
                        tryParseInt(state.paddingVertical, view.paddingBottom)
                )
                view.setPadding(
                        tryParseInt(state.leftPadding, view.paddingLeft),
                        tryParseInt(state.topPadding, view.paddingTop),
                        tryParseInt(state.rightPadding, view.paddingRight),
                        tryParseInt(state.bottomPadding, view.paddingBottom)
                )

                view.setPaddingRelative(
                        tryParseInt(state.startPadding, view.paddingLeft),
                        tryParseInt(state.topPadding, view.paddingTop),
                        tryParseInt(state.endPadding, view.paddingRight),
                        tryParseInt(state.bottomPadding, view.paddingBottom)
                )


                view.x = tryParseFloat(state.x, view.x)
                view.y = tryParseFloat(state.y, view.y)

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    view.elevation = tryParseFloat(state.elevation, view.elevation)
                }

                view.rotation = tryParseFloat(state.rotation, view.rotation)
                view.rotationX = tryParseFloat(state.rotationX, view.rotationX)
                view.rotationY = tryParseFloat(state.rotationY, view.rotationY)

                view.overScrollMode = tryParseInt(state.overScrollMode, view.overScrollMode)


                view.layoutParams = ViewGroup.LayoutParams(
                        tryParseLayoutSize(state.layoutWidth, WRAP_CONTENT),
                        tryParseLayoutSize(state.layoutHeight, WRAP_CONTENT))
            }
        }

        fun <ViewType: View, StateType: ViewState, MatchaViewType: MatchaView<ViewType, StateType>>
                renderView(
                context: Context,
                matchaViewClass: Class<MatchaViewType>,
                jsonString: String
        ): View{
            val gson = Gson()

            val viewFactory = matchaViewClass.newInstance()
            val view = viewFactory.getInstance(context)


            try {
                val state = gson.fromJson<StateType>(jsonString, viewFactory.stateClass)
                viewFactory.render(view, state)
            } catch (e : JsonSyntaxException) {
                Log.e(LOG_TAG, e.toString())
                throw e
            }catch (e : MalformedJsonException) {
                Log.e(LOG_TAG, e.toString())
                throw e
            }


            return view
        }

        fun getType(jsonString : String) : String? {
            val gson = Gson()

            try {
                val viewState = gson.fromJson<RenderUtil.ViewState>(jsonString, RenderUtil.ViewState::class.java)
                return viewState.type
            } catch (e : JsonSyntaxException) {
                Log.e(LOG_TAG, e.toString())
                throw e
            } catch (e : MalformedJsonException) {
                Log.e(LOG_TAG, e.toString())
                throw e
            }
        }

        fun tryParseColor(string : String?, defaultColor : Int = Color.BLACK) : Int {
            string ?: return defaultColor
            return Color.parseColor(string)
        }

        fun tryParseFloat(string : String?, defaultValue : Float = 0f) : Float {
            string ?: return defaultValue
            return string.toFloatOrNull() ?: defaultValue
        }

        fun tryParseInt(string : String?, defaultValue: Int = 0) : Int {
            string ?: return defaultValue
            return string.toIntOrNull() ?: defaultValue
        }


        fun tryParseLayoutSize(string : String?, defaultValue: Int = WRAP_CONTENT) : Int {
            string ?: return defaultValue
            return when(string) {
                "wrap_content" -> WRAP_CONTENT
                "match_parent" -> MATCH_PARENT
                else -> tryParseInt(string, defaultValue)
            }
        }

        fun tryParseTextAlignment(string : String?, defaultValue: Int = View.TEXT_ALIGNMENT_INHERIT) : Int{
            string ?: return defaultValue
            return when(string) {
                "center" -> View.TEXT_ALIGNMENT_CENTER
                "gravity" -> View.TEXT_ALIGNMENT_GRAVITY
                "inherit" -> View.TEXT_ALIGNMENT_INHERIT
                "textStart" -> View.TEXT_ALIGNMENT_TEXT_START
                "textEnd" -> View.TEXT_ALIGNMENT_TEXT_END
                "viewStart" -> View.TEXT_ALIGNMENT_VIEW_START
                "viewEnd" -> View.TEXT_ALIGNMENT_VIEW_END
                else -> defaultValue
            }
        }
    }
}