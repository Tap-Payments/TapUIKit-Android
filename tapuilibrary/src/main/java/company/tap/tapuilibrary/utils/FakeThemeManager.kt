package company.tap.tapuilibrary.utils

import android.graphics.Color
import company.tap.thememanager.theme.TextViewTheme

/**
 *
 * Created by Mario Gamal on 7/15/20
 * Copyright © 2020 Tap Payments. All rights reserved.
 *
 */
object FakeThemeManager {

    fun getGoPayTabLayoutTextTheme(isSelected: Boolean): TextViewTheme {
        val theme = TextViewTheme()
        if (isSelected)
            theme.textColor = getGoPaySelectedTabColor()
        else
            theme.textColor = getGoPayUnSelectedTabColor()
        return theme
    }

    fun getGoPaySelectedTabColor() = Color.parseColor("#4b4847")
    fun getGoPayUnSelectedTabColor() = Color.parseColor("#aeaeae")
}