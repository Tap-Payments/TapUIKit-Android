package company.tap.tapuilibrary.atoms

import android.content.Context
import android.util.AttributeSet
import android.view.View
import company.tap.tapuilibrary.TapView
import company.tap.thememanager.theme.SeparatorViewTheme

/**
 * Created by AhlaamK on 6/10/20.

Copyright (c) 2020    Tap Payments.
All rights reserved.
 **/
open class TapSeparatorView(context: Context?, attrs: AttributeSet?): View(context, attrs) ,
    TapView<SeparatorViewTheme> {
    override fun setTheme(theme: SeparatorViewTheme) {
        theme.strokeColor?.let { setBackgroundColor(it)}
        theme.strokeWidth?.let { minimumWidth = it }
        theme.strokeHeight?.let { minimumHeight = it }
    }
}