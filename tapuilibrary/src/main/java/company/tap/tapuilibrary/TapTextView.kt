package company.tap.tapuilibrary

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView
import company.tap.thememanager.atoms.TextViewTheme

/**
 *
 * Created by Mario Gamal on 4/20/20
 * Copyright © 2020 Tap Payments. All rights reserved.
 *
 */
open class TapTextView(context: Context, attributeSet: AttributeSet) :
    AppCompatTextView(context, attributeSet), TapView<TextViewTheme> {
    override fun setTheme(theme: TextViewTheme) {
        setTextColor(theme.textColor)
        textSize = theme.textSize
        letterSpacing = theme.letterSpacing
    }
}