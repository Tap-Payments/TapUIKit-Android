package company.tap.tapuilibrary

import android.graphics.drawable.Drawable
import company.tap.tapcardvalidator_android.CardBrand

/**
 *
 * Created by Mario Gamal on 6/18/20
 * Copyright © 2020 Tap Payments. All rights reserved.
 *
 */
data class SectionTabItem(
    val image: Drawable,
    val type: CardBrand
)