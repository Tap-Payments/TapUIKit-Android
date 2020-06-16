package company.tap.tapuilibrary.atoms

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatButton
import company.tap.tapuilibrary.TapView
import company.tap.thememanager.theme.ButtonTheme

/**
 * Created by AhlaamK on 4/15/20.

Copyright (c) 2020    Tap Payments.
All rights reserved.
 **/
open class TapButton(context: Context, attributeSet: AttributeSet) :
    AppCompatButton(context, attributeSet),
    TapView<ButtonTheme> {
    override fun setTheme(theme: ButtonTheme) {
        theme.textColor?.let { setTextColor(it) }
        theme.textSize?.let { textSize = it }
        theme.letterSpacing?.let { letterSpacing = it }
        invalidate()
    }
}