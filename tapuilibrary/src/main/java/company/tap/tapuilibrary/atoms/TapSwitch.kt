package company.tap.tapuilibrary.atoms

import android.content.Context
import android.content.res.ColorStateList
import android.util.AttributeSet
import androidx.appcompat.widget.SwitchCompat
import company.tap.tapuilibrary.TapView
import company.tap.thememanager.theme.SwitchTheme

/**
 * Created by AhlaamK on 4/15/20.

Copyright (c) 2020    Tap Payments.
All rights reserved.
 **/
/** TapSwitch is a two-state toggle switch widget that can select between two options. The user may drag the "thumb"
 *  back and forth to choose the selected option, or simply tap to toggle as if it were a checkbox
 * */
open class TapSwitch(context: Context, attributeSet: AttributeSet) :
    SwitchCompat(context, attributeSet),
    TapView<SwitchTheme> {
    override fun setTheme(theme: SwitchTheme) {
        theme.textColor?.let { setTextColor(it) }
        theme.textSize?.let { textSize = it }
        theme.letterSpacing?.let { letterSpacing = it }
        theme.thumbTint?.let { thumbTintList = ColorStateList.valueOf(it) }
        theme.trackTint?.let { trackTintList = ColorStateList.valueOf(it) }
        invalidate()
    }
}