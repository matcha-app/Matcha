package com.patzhum.matcha.render

import android.content.Context
import android.content.res.ColorStateList
import android.content.res.Resources
import android.os.Build
import android.support.v4.widget.TextViewCompat
import android.text.method.DigitsKeyListener
import android.text.method.PasswordTransformationMethod
import android.text.method.QwertyKeyListener
import android.widget.TextView

/**
 * Created by patri on 2018-02-22.
 */
class MatchaTextView : MatchaView<TextView, MatchaTextView.State>() {

    override fun getInstance(context: Context): TextView {
        return TextView(context)
    }

    override val stateClass = State::class.java

    override fun render(view : TextView, state : State) {
        super.render(view, state)
        view.apply {
            autoLinkMask = RenderUtil.tryParseAutolink(state.autolink, autoLinkMask)
            if (Build.VERSION.SDK_INT > 26) {
                setAutoSizeTextTypeUniformWithConfiguration(
                        RenderUtil.tryParseInt(state.autoSizeMinTextSize, autoSizeMinTextSize),
                        RenderUtil.tryParseInt(state.autoSizeMaxTextSize, autoSizeMaxTextSize),
                        RenderUtil.tryParseInt(state.autoSizeStepGranularity, autoSizeStepGranularity),
                        RenderUtil.tryParseAutoSizeTextType(state.autoSizeTextType, autoSizeTextType)
                )
                //need to implement @Arrays/resources
                setAutoSizeTextTypeUniformWithPresetSizes(
                        intArrayOf(RenderUtil.tryParseInt(state.autoSizePresetSizes)),
                        RenderUtil.tryParseAutoSizeTextType(state.autoSizeTextType, autoSizeTextType))
            }
            //autotext
            keyListener = QwertyKeyListener.getInstance(RenderUtil.tryParseBoolean(state.autoText),RenderUtil.tryParseCaptialize(state.capitalize))

            if (Build.VERSION.SDK_INT  >23) {
                breakStrategy = RenderUtil.tryParseBreakStrategy(state.breakStrategy, breakStrategy)
                compoundDrawableTintList = ColorStateList.valueOf(RenderUtil.tryParseColor(state.drawableTint, compoundDrawableTintList.defaultColor))
                compoundDrawableTintMode = RenderUtil.tryParseDrawableTintMode(state.drawableTintMode, compoundDrawableTintMode)
                hyphenationFrequency = RenderUtil.tryParseHyphenationFrequency(state.hyphenationFrequency, hyphenationFrequency)

            }

            isCursorVisible = RenderUtil.tryParseBoolean(state.cursorVisible)
            keyListener = DigitsKeyListener.getInstance(state.digits) //digits


            setCompoundDrawablesWithIntrinsicBounds(
                    RenderUtil.tryParseResourceId(state.drawableLeft),
                    RenderUtil.tryParseResourceId(state.drawableTop),
                    RenderUtil.tryParseResourceId(state.drawableRight),
                    RenderUtil.tryParseResourceId(state.drawableBottom)
            )

            setCompoundDrawablesRelativeWithIntrinsicBounds(
                    RenderUtil.tryParseResourceId(state.drawableStart),
                    RenderUtil.tryParseResourceId(state.drawableTop),
                    RenderUtil.tryParseResourceId(state.drawableEnd),
                    RenderUtil.tryParseResourceId(state.drawableBottom)
            )

            compoundDrawablePadding = RenderUtil.tryParseInt(state.drawablePadding)
            //editableText

            if (Build.VERSION.SDK_INT > 21) {
                setElegantTextHeight(RenderUtil.tryParseBoolean(state.elegantTextHeight))
                fontFeatureSettings = state.fontFeatureSettings
                letterSpacing = RenderUtil.tryParseFloat(state.letterSpacing, letterSpacing)
            }

            when (RenderUtil.tryParseBoolean(state.editable)) {
                true -> {
                    isCursorVisible = true
                    isEnabled = true
                    isFocusableInTouchMode = true
                    requestFocus()
                }
                false -> {
                    isCursorVisible = false
                    isEnabled = false
                    isFocusableInTouchMode = false
                }
            }

            ellipsize = RenderUtil.tryParseEllipsize(state.ellipsize, ellipsize)
            setEms(RenderUtil.tryParseInt(state.ems))

            freezesText = RenderUtil.tryParseBoolean(state.freezesText, freezesText)
            gravity = RenderUtil.tryParseGravity(state.gravity, gravity)
            height = RenderUtil.tryParseInt(state.height, height)
            hint = state.hint
            setImeActionLabel(state.imeActionLabel,RenderUtil.tryParseInt(state.imeActionId, imeActionId))
            imeOptions = RenderUtil.tryParseImeOptions(state.imeOptions, imeOptions)
            includeFontPadding = RenderUtil.tryParseBoolean(state.includeFontPadding, includeFontPadding)
            //inputmethod needed

            setRawInputType(RenderUtil.tryParseInputType(state.inputType))
            setLineSpacing(
                    RenderUtil.tryParseFloat(state.lineSpacingExtra,lineSpacingExtra),
                    RenderUtil.tryParseFloat(state.lineSpacingMultiplier, lineSpacingMultiplier)
            )

            setLines(RenderUtil.tryParseInt(state.lines))
            linksClickable = RenderUtil.tryParseBoolean(state.linksClickable, linksClickable)
            marqueeRepeatLimit = RenderUtil.tryParseMarqueeRepeatLimit(state.marqueeRepeatLimit, marqueeRepeatLimit)
            maxEms = RenderUtil.tryParseInt(state.maxEms, maxEms)
            maxHeight = RenderUtil.tryParseInt(state.maxHeight, maxHeight)
            //maxLength = RenderUtil.tryParseInt(state.maxLength, maxLength)
            filters = RenderUtil.tryParseInputFilter(state.maxLength)
            maxLines = RenderUtil.tryParseInt(state.maxLines, maxLines)
            maxWidth = RenderUtil.tryParseInt(state.maxWidth, maxWidth)
            minEms = RenderUtil.tryParseInt(state.minEms, minEms)
            minHeight = RenderUtil.tryParseInt(state.minHeight, minHeight)
            minLines = RenderUtil.tryParseInt(state.minLines, minLines)
            minWidth = RenderUtil.tryParseInt(state.minWidth, minWidth)

            //numeric
            inputType = RenderUtil.tryParseInputType(state.numeric)
            inputType = inputType.or(RenderUtil.tryParseInputType(state.phoneNumber))

            setHorizontallyScrolling(RenderUtil.tryParseBoolean(state.scrollHorizontally))
            setSelectAllOnFocus(RenderUtil.tryParseBoolean(state.selectAllOnFocus))
            setShadowLayer(
                    RenderUtil.tryParseFloat(state.shadowRadius, shadowRadius),
                    RenderUtil.tryParseFloat(state.shadowDx, shadowDx),
                    RenderUtil.tryParseFloat(state.shadowDy, shadowDy),
                    RenderUtil.tryParseColor(state.shadowColor, shadowColor)
            )

            setSingleLine(RenderUtil.tryParseBoolean(state.singleLine))
            transformationMethod = when (RenderUtil.tryParseBoolean(state.password)){
                true -> PasswordTransformationMethod()
                false -> null
            }

            setText(state.text, RenderUtil.tryParseBufferType(state.bufferType))
            setTextAppearance(context, RenderUtil.tryParseResourceId(state.textAppearance))
            setTextColor(RenderUtil.tryParseColor(state.textColor, currentTextColor))
            setHintTextColor(RenderUtil.tryParseColor(state.textColorHint, currentHintTextColor))
            setLinkTextColor(RenderUtil.tryParseColor(state.textColorLink))
            highlightColor = RenderUtil.tryParseColor(state.textColorHighlight)

            setAllCaps(RenderUtil.tryParseBoolean(state.textAllCaps))

            textSize = RenderUtil.tryParseFloat(state.textSize, textSize)
            textAlignment = RenderUtil.tryParseTextAlignment(state.textAlignment, textAlignment)
            setTextIsSelectable(RenderUtil.tryParseBoolean(state.textIsSelectable))
            textScaleX = RenderUtil.tryParseFloat(state.textScaleX, textScaleX)
            typeface = RenderUtil.tryParseTypeface(state.typeface, RenderUtil.tryParseTextStyle(state.textStyle))
            width = RenderUtil.tryParseInt(state.width, width)
        }
    }

    class State(
            val autolink : String? = null,
            val autoSizeMaxTextSize : String? = null,
            val autoSizeMinTextSize : String? = null,
            val autoSizePresetSizes : String? = null,
            val autoSizeStepGranularity : String? = null,
            val autoSizeTextType : String? = null,
            val autoText : String? = null,

            val breakStrategy : String? = null,
            val bufferType : String? = null,
            val capitalize : String? = null,
            val cursorVisible : String? = null,

            val digits : String? = null,
            val drawableBottom : String? = null,
            val drawableEnd : String? = null,
            val drawableLeft : String? = null,
            val drawablePadding : String? = null,
            val drawableRight : String? = null,
            val drawableStart : String? = null,
            val drawableTint : String? = null,
            val drawableTintMode : String? = null,
            val drawableTop : String? = null,
            val editable : String? = null,
            val editorExtras : String? =  null,
            val elegantTextHeight : String? = null,
            val ellipsize: String? =  null,
            val ems: String? =  null,
            val fontFamily : String? =  null,
            val fontFeatureSettings : String? =  null,
            val freezesText : String? =  null,
            val gravity : String? =  null,
            val height : String? =  null,
            val hint : String? =  null,
            val hyphenationFrequency : String? =  null,
            val imeActionId : String? =  null,
            val imeActionLabel : String? =  null,
            val imeOptions : String? =  null,
            val includeFontPadding : String? =  null,
            val inputMethod : String? =  null,
            val inputType: String? =  null,
            val letterSpacing : String? =  null,
            val lineSpacingExtra : String? =  null,
            val lineSpacingMultiplier : String? =  null,
            val lines : String? =  null,
            val linksClickable : String? =  null,
            val marqueeRepeatLimit : String? =  null,
            val maxEms : String? =  null,
            val maxHeight : String? =  null,
            val maxLength: String? =  null,
            val maxLines: String? =  null,
            val maxWidth : String? =  null,
            val minEms : String? =  null,
            val minHeight : String? =  null,
            val minLines : String? =  null,
            val minWidth : String? =  null,
            val numeric : String? =  null,
            val password : String? =  null,
            val phoneNumber : String? =  null,
            val privateImeOptions : String? =  null,
            val scrollHorizontally : String? =  null,
            val selectAllOnFocus : String? =  null,
            val shadowColor : String? =  null,
            val shadowDx : String? =  null,
            val shadowDy : String? =  null,
            val shadowRadius : String? =  null,
            val singleLine : String? =  null,

            val text : String? = null,
            val textAllCaps : String? =  null,
            val textAppearance : String? =  null,

            val textColor: String? = null,
            val textColorHighlight: String? = null,

            val textColorHint : String? =  null,
            val textColorLink : String? =  null,

            val textSize: String? = null,
            val textAlignment: String? = null,
            val textIsSelectable: String? = null,
            val textScaleX: String? = null,
            val textStyle: String? = null,
            val typeface: String? = null,
            val width: String? = null
    ) : MatchaView.State(type = TextView::class.java.simpleName)
}

