package company.tap.tapuilibrary

import android.graphics.drawable.Drawable
import android.view.View
import company.tap.tapcardvalidator_android.CardBrand

/**
 *
 * Created by Mario Gamal on 6/18/20
 * Copyright © 2020 Tap Payments. All rights reserved.
 *
 */
data class SectionTabItem(
    val selectedImage: Drawable,
    val unSelectedImage: Drawable,
    val type: CardBrand,
    var view: View? = null
)