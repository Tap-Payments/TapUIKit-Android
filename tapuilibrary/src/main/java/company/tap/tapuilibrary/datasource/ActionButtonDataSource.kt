package company.tap.tapuilibrary.datasource

import android.graphics.drawable.Drawable
import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes

/**
 *
 * Created by Mario Gamal on 6/25/20
 * Copyright © 2020 Tap Payments. All rights reserved.
 *
 */
data class ActionButtonDataSource(
    val text: String? = null,
    val successImage: Drawable? = null,
    val errorImage: Drawable? = null,
    val cornerRadius: Float? = null,

    @ColorInt
    val backgroundColor: Int? = null,

    @ColorRes
    val backgroundColorResources: Int? = null,

    @DrawableRes
    val successImageResources: Int? = null,

    @DrawableRes
    val errorImageResources: Int? = null
)