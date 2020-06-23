package company.tap.tapuilibrary.atoms

import android.content.Context
import android.util.AttributeSet

import com.google.android.material.card.MaterialCardView
import company.tap.tapuilibrary.interfaces.TapView
import company.tap.thememanager.theme.ChipTheme

/**
 * Created by AhlaamK on 6/10/20.

Copyright (c) 2020    Tap Payments.
All rights reserved.
 **/
open class TapDragIndicator (context: Context?, attrs: AttributeSet?): MaterialCardView(context, attrs),
    TapView<ChipTheme> {
    override fun setTheme(theme: ChipTheme) {
        theme.cardCornerRadius?.let{ radius = it.toFloat() }
        theme.cardElevation?.let { cardElevation= it.toFloat() }

    }
}