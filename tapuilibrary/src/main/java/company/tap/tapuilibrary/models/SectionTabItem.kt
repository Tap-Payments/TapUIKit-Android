package company.tap.tapuilibrary.models

import android.graphics.drawable.Drawable
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
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
    var imageView: ImageView? = null,
    var indicator: View? = null
)