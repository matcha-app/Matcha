package com.patzhum.matcha.render

import android.content.Context
import android.graphics.Color
import android.os.Build

import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import com.google.gson.stream.MalformedJsonException

/**
 * Created by patri on 2018-02-22.
 */
class RenderUtil {
    companion object {
        private val LOG_TAG = RenderUtil::class.java.simpleName

        fun <ViewType: View, StateType: MatchaView.State, MatchaViewType: MatchaView<ViewType, StateType>>
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
    }
}