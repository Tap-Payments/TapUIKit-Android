package company.tap.tapuilibrary

import android.content.Context
import android.content.res.ColorStateList
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatEditText
import company.tap.thememanager.theme.EditTextTheme

/**
 * Created by AhlaamK on 4/14/20.

Copyright (c) 2020    Tap Payments.
All rights reserved.
 **/
open class TapEditText(context: Context, attributeSet: AttributeSet) :
    AppCompatEditText(context, attributeSet), TapView<EditTextTheme> {

    override fun setTheme(theme: EditTextTheme) {
        theme.maxLines?.let { maxLines = it }
        theme.textColor?.let { setTextColor(it) }
        theme.textSize?.let { textSize = it }
        theme.letterSpacing?.let { letterSpacing = it }
        theme.textColorHint?.let { setHintTextColor(it) }
        theme.backgroundTint?.let { backgroundTintList = ColorStateList.valueOf(it) }
        invalidate()
    }

}