package com.patzhum.matcha.render

import android.content.Context
import android.content.res.Resources
import android.graphics.Color
import android.graphics.PorterDuff
import android.graphics.Typeface
import android.os.Build
import android.text.InputFilter
import android.text.InputType
import android.text.TextUtils
import android.text.TextUtils.split
import android.text.method.TextKeyListener

import android.util.Log
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import com.google.gson.stream.MalformedJsonException

/**
 * Created by patri on 2018-02-22.
 */
class RenderUtil {
    companion object {
        private val LOG_TAG = RenderUtil::class.java.simpleName

        private val AUTOLINK_ALL = 15
        private val AUTOLINK_MAP = 8
        private val AUTOLINK_PHONE = 4
        private val AUTOLINK_EMAIL = 2
        private val AUTOLINK_WEB = 1
        private val AUTOLINK_NONE = 0

        private val AUTOSIZETEXTYPE_NONE = 0
        private val AUTOSIZETEXTTYPE_UNIFORM = 1

        private val BREAKSTRATEGY_BALANCED = 2
        private val BREAKSTRATEGY_HIGH_QUAL = 1
        private val BREAKSTRATEGY_SIMPLE = 0

        private val CAPITALIZE_CHARS = 3
        private val CAPITALIZE_WORDS = 2
        private val CAPITALIZE_SENTENCES = 1
        private val CAPITALIZE_NONE = 0

        private val HYPHENATIONFREQ_FULL = 2
        private val HYPHENATIONFREQ_NORMAL = 1
        private val HYPHENATIONFREQ_NONE = 0

        private val MARQUEEREPEAT_FOREVER = -1

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

        fun tryParseBoolean(string: String?, defaultValue: Boolean = false) : Boolean{
            string ?: return defaultValue
            return string.toBoolean()
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

        fun tryParseTextStyle(string: String?, defaultValue: Int = Typeface.NORMAL) : Int{
            string ?:  return defaultValue
            return when(string) {
                "normal" -> Typeface.NORMAL
                "bold" -> Typeface.BOLD
                "italic" -> Typeface.ITALIC
                "bold_italic" -> Typeface.BOLD_ITALIC
                else -> defaultValue
            }
        }

        fun tryParseTypeface(string: String?, defaultValue: Int = Typeface.NORMAL) : Typeface{
            val defaultTypeface = Typeface.create(Typeface.DEFAULT, defaultValue)
            string ?: defaultTypeface
            return when(string) {
                "default" -> Typeface.create(Typeface.DEFAULT, defaultValue)
                "monospace" -> Typeface.create(Typeface.MONOSPACE, defaultValue)
                "sans_serif"-> Typeface.create(Typeface.SANS_SERIF, defaultValue)
                "serif" -> Typeface.create(Typeface.SERIF, defaultValue)
                else -> defaultTypeface
            }
        }

        fun tryParseAutolink(string: String?, defaultValue: Int = AUTOLINK_NONE) : Int{
            string ?: return defaultValue
            return when(string){
                "all" -> AUTOLINK_ALL
                "map" -> AUTOLINK_MAP
                "phone" -> AUTOLINK_PHONE
                "email" -> AUTOLINK_EMAIL
                "web" -> AUTOLINK_WEB
                "none" -> AUTOLINK_NONE
                else -> AUTOLINK_ALL    //for multiple link types piped together
            }
        }

        fun tryParseAutoSizeTextType(string: String? , defaultValue: Int = AUTOSIZETEXTYPE_NONE) : Int {
            string ?: return defaultValue
            return when (string){
                "none" -> AUTOSIZETEXTYPE_NONE
                "uniform" -> AUTOSIZETEXTTYPE_UNIFORM
                else -> defaultValue
            }
        }

        fun tryParseCaptialize
                (string: String?, defaultValue: TextKeyListener.Capitalize = TextKeyListener.Capitalize.NONE): TextKeyListener.Capitalize {
            string ?: return defaultValue
            return when(string){
                "characters" -> TextKeyListener.Capitalize.CHARACTERS
                "words" -> TextKeyListener.Capitalize.WORDS
                "sentences" -> TextKeyListener.Capitalize.SENTENCES
                "none" -> TextKeyListener.Capitalize.NONE
                else -> defaultValue
            }
        }

        fun tryParseBreakStrategy(string: String?, defaultValue: Int = BREAKSTRATEGY_SIMPLE) : Int {
            string ?: return defaultValue
            return when(string){
                "balanced" -> BREAKSTRATEGY_BALANCED
                "high_quality" -> BREAKSTRATEGY_HIGH_QUAL
                "simple" -> BREAKSTRATEGY_SIMPLE
                else -> defaultValue
            }
        }

        fun tryParseBufferType
                (string: String?, defaultValue: TextView.BufferType = TextView.BufferType.NORMAL) : TextView.BufferType{
            string ?: return defaultValue
            return when(string) {
                "editable" -> TextView.BufferType.EDITABLE
                "spannable" -> TextView.BufferType.SPANNABLE
                "normal" -> TextView.BufferType.NORMAL
                else -> defaultValue
            }
        }

        fun tryParseDrawableTintMode(string: String?, defaultValue: PorterDuff.Mode = PorterDuff.Mode.SRC_IN ) : PorterDuff.Mode {
            string ?: return defaultValue
            return when(string){
                "add" -> PorterDuff.Mode.ADD
                "multiply" -> PorterDuff.Mode.MULTIPLY
                "screen" -> PorterDuff.Mode.SCREEN
                "src_atop" -> PorterDuff.Mode.SRC_ATOP
                "src_over" -> PorterDuff.Mode.SRC_OVER
                "src_in" -> PorterDuff.Mode.SRC_IN
                else -> defaultValue
            }
        }

        fun tryParseEllipsize(string : String?, defaultValue: TextUtils.TruncateAt? = null) : TextUtils.TruncateAt? {
            string ?: return defaultValue
            return when(string){
                "end" -> TextUtils.TruncateAt.END
                "start" -> TextUtils.TruncateAt.START
                "middle" -> TextUtils.TruncateAt.MIDDLE
                "marquee" -> TextUtils.TruncateAt.MARQUEE
                else -> null
            }
        }

        fun tryParseGravity(string: String?, defaultValue: Int = (Gravity.TOP.or(Gravity.START))) : Int {
            string ?: return defaultValue
            var gravity = 0

            for (param in split(string,"|")){
                when(param){
                    "top" -> gravity.or(Gravity.TOP)
                    "bottom" -> gravity.or(Gravity.BOTTOM)
                    "center" -> gravity.or(Gravity.CENTER)
                    "center_horizontal" -> gravity.or(Gravity.CENTER_HORIZONTAL)
                    "center_vertical" -> gravity.or(Gravity.CENTER_VERTICAL)
                    "clip_horizontal" -> gravity.or(Gravity.CLIP_HORIZONTAL)
                    "clip_vertical" -> gravity.or(Gravity.CLIP_VERTICAL)
                    "end" -> gravity.or(Gravity.END)
                    "fill" -> gravity.or(Gravity.FILL)
                    "fill_horizontal" -> gravity.or(Gravity.FILL_HORIZONTAL)
                    "fill_vertical" -> gravity.or(Gravity.FILL_VERTICAL)
                    "left" -> gravity.or(Gravity.LEFT)
                    "right" -> gravity.or(Gravity.RIGHT)
                    "start" -> gravity.or(Gravity.START)
                    else -> gravity
                }
            }
            return gravity
        }

        fun tryParseHyphenationFrequency(string: String?, defaultValue: Int = HYPHENATIONFREQ_NONE): Int {
            string ?: return defaultValue
            return when(string){
                "full" -> HYPHENATIONFREQ_FULL
                "normal" -> HYPHENATIONFREQ_NORMAL
                "none" -> HYPHENATIONFREQ_NONE
                else -> defaultValue
            }
        }

        fun tryParseImeOptions(string: String?, defaultValue: Int = EditorInfo.IME_NULL): Int{
            string ?: return defaultValue
            var imeOptions = 0
            for (option in split(string,"|")){
                imeOptions.or(when(option){
                    "actionDone" -> EditorInfo.IME_ACTION_DONE
                    "actionGo" -> EditorInfo.IME_ACTION_GO
                    "actionNext"-> EditorInfo.IME_ACTION_NEXT
                    "actionNone" -> EditorInfo.IME_ACTION_NONE
                    "actionPrevious" -> EditorInfo.IME_ACTION_PREVIOUS
                    "actionSearch" -> EditorInfo.IME_ACTION_SEARCH
                    "actionSend" -> EditorInfo.IME_ACTION_SEND
                    "actionUnspecified" -> EditorInfo.IME_ACTION_UNSPECIFIED
                    "flagForceAscii" -> EditorInfo.IME_FLAG_FORCE_ASCII
                    "flagNavigateNext" -> EditorInfo.IME_FLAG_NAVIGATE_NEXT
                    "flagNavigatePrevious" -> EditorInfo.IME_FLAG_NAVIGATE_PREVIOUS
                    "flagNoAccessoryAction" -> EditorInfo.IME_FLAG_NO_ACCESSORY_ACTION
                    "flagNoEnterAction" -> EditorInfo.IME_FLAG_NO_ENTER_ACTION
                    "flagNoExtractUI" -> EditorInfo.IME_FLAG_NO_EXTRACT_UI
                    "flagNoFullScreen" -> EditorInfo.IME_FLAG_NO_FULLSCREEN
                    "flagNoPersonalizedLearning" -> EditorInfo.IME_FLAG_NO_PERSONALIZED_LEARNING
                    "normal" -> EditorInfo.IME_NULL
                    else -> defaultValue
                })
            }
            return imeOptions
        }

        fun tryParseInputType(string: String?, defaultValue: Int = InputType.TYPE_NULL) : Int {
            string ?: return defaultValue
            val inputType = 0
            for (type in split(string, "|")){
                inputType.or(when(type){
                    "date" -> InputType.TYPE_CLASS_DATETIME.or(InputType.TYPE_DATETIME_VARIATION_DATE)
                    "datetime" -> InputType.TYPE_CLASS_DATETIME.or(InputType.TYPE_DATETIME_VARIATION_NORMAL)
                    "decimal" -> InputType.TYPE_NUMBER_FLAG_DECIMAL
                    "integer" -> InputType.TYPE_CLASS_NUMBER
                    "none" -> InputType.TYPE_NULL
                    "number" -> InputType.TYPE_CLASS_NUMBER.or(InputType.TYPE_NUMBER_VARIATION_NORMAL)
                    "numberDecimal" -> InputType.TYPE_CLASS_NUMBER.or(InputType.TYPE_NUMBER_FLAG_DECIMAL)
                    "numberPassword" -> InputType.TYPE_CLASS_NUMBER.or(InputType.TYPE_NUMBER_VARIATION_PASSWORD)
                    "numberSigned" -> InputType.TYPE_CLASS_NUMBER.or(InputType.TYPE_NUMBER_FLAG_SIGNED)
                    "phone" -> InputType.TYPE_CLASS_PHONE
                    "text" -> InputType.TYPE_CLASS_TEXT.or(InputType.TYPE_TEXT_VARIATION_NORMAL)
                    "textAutoComplete" -> InputType.TYPE_TEXT_FLAG_AUTO_COMPLETE
                    "textAutoCorrect" -> InputType.TYPE_TEXT_FLAG_AUTO_CORRECT
                    "textCapCharacters" -> InputType.TYPE_TEXT_FLAG_CAP_CHARACTERS
                    "textCapSentences" -> InputType.TYPE_TEXT_FLAG_CAP_SENTENCES
                    "textCapWords" -> InputType.TYPE_TEXT_FLAG_CAP_WORDS
                    "textEmailAddress" -> InputType.TYPE_CLASS_TEXT.or(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS)
                    "textEmailSubject" -> InputType.TYPE_CLASS_TEXT.or(InputType.TYPE_TEXT_VARIATION_EMAIL_SUBJECT)
                    "textFilter" -> InputType.TYPE_CLASS_TEXT.or(InputType.TYPE_TEXT_VARIATION_FILTER)
                    "textImeMultiLine" -> InputType.TYPE_TEXT_FLAG_IME_MULTI_LINE
                    "textLongMessage" -> InputType.TYPE_CLASS_TEXT.or(InputType.TYPE_TEXT_VARIATION_LONG_MESSAGE)
                    "textMultiLine" -> InputType.TYPE_TEXT_FLAG_IME_MULTI_LINE
                    "textNoSuggestions" -> InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS
                    "textPassword" -> InputType.TYPE_CLASS_TEXT.or(InputType.TYPE_TEXT_VARIATION_PASSWORD)
                    "textPersonName" -> InputType.TYPE_CLASS_TEXT.or(InputType.TYPE_TEXT_VARIATION_PERSON_NAME)
                    "textPhonetic" -> InputType.TYPE_CLASS_TEXT.or(InputType.TYPE_TEXT_VARIATION_PHONETIC)
                    "textPostalAddress" -> InputType.TYPE_CLASS_TEXT.or(InputType.TYPE_TEXT_VARIATION_POSTAL_ADDRESS)
                    "textShortMessage" -> InputType.TYPE_CLASS_TEXT.or(InputType.TYPE_TEXT_VARIATION_SHORT_MESSAGE)
                    "textUri" -> InputType.TYPE_CLASS_TEXT.or(InputType.TYPE_TEXT_VARIATION_URI)
                    "textVisiblePassword" -> InputType.TYPE_CLASS_TEXT.or(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD)
                    "textWebEditText" -> InputType.TYPE_CLASS_TEXT.or(InputType.TYPE_TEXT_VARIATION_WEB_EDIT_TEXT)
                    "textWebEmailAddress" -> InputType.TYPE_CLASS_TEXT.or(InputType.TYPE_TEXT_VARIATION_WEB_EMAIL_ADDRESS)
                    "textWebPassword" -> InputType.TYPE_CLASS_TEXT.or(InputType.TYPE_TEXT_VARIATION_WEB_PASSWORD)
                    "time" -> InputType.TYPE_CLASS_DATETIME.or(InputType.TYPE_DATETIME_VARIATION_TIME)
                    "signed" -> InputType.TYPE_NUMBER_FLAG_SIGNED
                    else -> defaultValue
                })
            }
            return inputType
        }

        fun tryParseMarqueeRepeatLimit(string: String?, defaultValue: Int = 0) : Int{
            string ?: return defaultValue
            return when (string){
                "marquee_forever" -> MARQUEEREPEAT_FOREVER
                else -> defaultValue
            }
        }

        fun tryParseResourceId(string: String?, defaultValue: Int = 0) : Int{
            string ?: return defaultValue
            if (!string.contains("@") || !string.contains(":") || !string.contains("/")) return defaultValue

            var packageStr = string.substring(string.indexOf("@") + 1, string.indexOf(":"))
            var typeStr = string.substring(string.indexOf(":") + 1, string.indexOf("/"))
            var nameStr = string.substring(string.indexOf("/") + 1, string.length)

            return Resources.getSystem().getIdentifier(nameStr, typeStr, packageStr)
        }

        fun tryParseInputFilter(string: String?, defaultValue: Array<InputFilter> = emptyArray()): Array<InputFilter> {
            string ?: return defaultValue
            val maxLength = string.toIntOrNull()
            return when (maxLength){
                is Int -> arrayOf(InputFilter.LengthFilter(maxLength))
                else -> defaultValue
            }
        }
    }
}