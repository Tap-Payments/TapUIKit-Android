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
    val textSize: Float? = null,
    val cornerRadius: Float? = null,
    val successImage: Drawable? = null,

    @ColorInt
    val textColor: Int? = null,

    @ColorRes
    val textColorResources: Int? = null,

    @DrawableRes
    val successImageResources: Int? = null,

    @ColorInt
    val errorColor: Int? = null,

    @ColorRes
    val errorColorResources: Int? = null,

    val errorImage: Drawable? = null,

    @DrawableRes
    val errorImageResources: Int? = null,

    @ColorInt
    val backgroundColor: Int? = null,

    @ColorRes
    val backgroundColorResources: Int? = null


)