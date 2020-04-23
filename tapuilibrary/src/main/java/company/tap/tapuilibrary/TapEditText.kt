package company.tap.tapuilibrary

import android.content.Context
import android.content.res.ColorStateList
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatEditText
import company.tap.thememanager.atoms.EditTextTheme

/**
 * Created by AhlaamK on 4/14/20.

Copyright (c) 2020    Tap Payments.
All rights reserved.
 **/
open class TapEditText(context: Context, attributeSet: AttributeSet) :
    AppCompatEditText(context, attributeSet), TapView<EditTextTheme> {

    override fun setTheme(theme: EditTextTheme) {
        maxLines = theme.maxLines
        textSize = theme.textSize
        letterSpacing = theme.letterSpacing
        setTextColor(theme.textColor)
        setHintTextColor(theme.textColorHint)
        backgroundTintList = ColorStateList.valueOf(theme.backgroundTint)
    }

}