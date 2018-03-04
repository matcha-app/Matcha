package com.patzhum.matcha.render.core

import android.content.Context
import android.graphics.Color

import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.widget.LinearLayout
import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import com.google.gson.stream.MalformedJsonException
import com.patzhum.matcha.render.layouts.MatchaLinearLayout
import com.patzhum.matcha.render.text.MatchaEditText
import com.patzhum.matcha.render.text.MatchaTextView

/**
 * Created by patri on 2018-02-22.
 */
class RenderUtil {
    companion object {
        private val LOG_TAG = RenderUtil::class.java.simpleName

        fun renderView(json : String, context : Context) : View? {
            return when (RenderUtil.getType(json)) {
                "TextView" -> RenderUtil.renderView(context, MatchaTextView::class.java, json)
                "EditText" -> RenderUtil.renderView(context, MatchaEditText::class.java, json)
                "LinearLayout" -> RenderUtil.renderView(context, MatchaLinearLayout::class.java, json)
                else -> null
            }
        }
        fun <ViewType: View, StateType: MatchaView.State, MatchaViewType: MatchaView<ViewType, StateType>> renderView(
                context: Context,
                matchaViewClass: Class<MatchaViewType>,
                jsonString: String
        ): View{
            val gson = Gson()

            val viewFactory = matchaViewClass.newInstance()
            val view = viewFactory.getInstance(context)

            try {
                val state = gson.fromJson<StateType>(jsonString, viewFactory.stateClass)
                viewFactory.render(view, state, context)
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
                val viewState = gson.fromJson<MatchaView.State>(jsonString, MatchaView.State::class.java)
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

        fun tryParseOrientation(string : String?, defaultValue: Int = LinearLayout.VERTICAL) : Int {
            string ?: return defaultValue
            return when (string) {
                "vertical" -> LinearLayout.VERTICAL
                "horizontal" -> LinearLayout.HORIZONTAL
                else -> defaultValue
            }
        }

        fun getLayoutParams(string : String?, defaultWidth: Int, defaultHeight: Int) : ViewGroup.LayoutParams {
            string ?: return ViewGroup.LayoutParams(defaultWidth, defaultHeight)
            val gson = Gson()
            val state = gson.fromJson(gson.toJson(string), MatchaView.State::class.java)
            return ViewGroup.LayoutParams(RenderUtil.tryParseLayoutSize(state.layoutWidth, defaultWidth),
                    RenderUtil.tryParseLayoutSize(state.layoutHeight, defaultHeight))
        }
    }
}