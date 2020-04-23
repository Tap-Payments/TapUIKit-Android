package company.tap.tapuilibrary

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatButton
import company.tap.thememanager.atoms.ButtonTheme

/**
 * Created by AhlaamK on 4/15/20.

Copyright (c) 2020    Tap Payments.
All rights reserved.
 **/
open class TapButton(context: Context, attributeSet: AttributeSet) :
    AppCompatButton(context, attributeSet), TapView<ButtonTheme> {
    override fun setTheme(theme: ButtonTheme) {
        setTextColor(theme.textColor)
        textSize = theme.textSize
        letterSpacing = theme.letterSpacing
    }
}