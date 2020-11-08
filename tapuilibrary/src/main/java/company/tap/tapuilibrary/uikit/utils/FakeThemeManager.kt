package company.tap.tapuilibrary.uikit.utils

import android.graphics.Color
import company.tap.tapuilibrary.themekit.ThemeManager
import company.tap.tapuilibrary.themekit.theme.TextViewTheme

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
            theme.textColor =
                getGoPaySelectedTabColor()
        else
            theme.textColor =
                getGoPayUnSelectedTabColor()
        return theme
    }

    fun getGoPaySelectedTabColor() = Color.parseColor(ThemeManager.getValue("goPay.loginBar.title.selected.textColor"))
    fun getGoPayUnSelectedTabColor() = Color.parseColor(ThemeManager.getValue("goPay.loginBar.title.selected.textColor"))
    fun getGoPayValidatedColor() = Color.parseColor("#007aff")
    fun getGoPayUnValidatedColor() = Color.parseColor("#d7d7d7")
    fun getGoPaySignInButtonColor() = Color.parseColor("#b9b9b9")
}