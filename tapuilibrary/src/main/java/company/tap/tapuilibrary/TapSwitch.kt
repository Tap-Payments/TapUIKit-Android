package company.tap.tapuilibrary

import android.content.Context
import android.content.res.ColorStateList
import android.util.AttributeSet
import androidx.appcompat.widget.SwitchCompat
import company.tap.tapuilibrary.TapView
import company.tap.thememanager.atoms.SwitchTheme

/**
 * Created by AhlaamK on 4/15/20.

Copyright (c) 2020    Tap Payments.
All rights reserved.
 **/
open class TapSwitch(context: Context) : SwitchCompat(context), TapView<SwitchTheme> {
    override fun setTheme(theme: SwitchTheme) {
        setTextColor(theme.textColor)
        textSize = theme.textSize
        letterSpacing = theme.letterSpacing
        thumbTintList = ColorStateList.valueOf(theme.thumbTint)
        trackTintList = ColorStateList.valueOf(theme.trackTint)
    }
}