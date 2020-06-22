package company.tap.tapuilibrary.atoms

import android.content.Context
import android.graphics.Canvas
import android.graphics.Path
import android.graphics.RectF
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatImageView
import company.tap.tapuilibrary.R
import company.tap.tapuilibrary.TapView
import company.tap.thememanager.theme.ImageViewTheme


/**
 * Created by AhlaamK on 4/29/20.

Copyright (c) 2020    Tap Payments.
All rights reserved.
 **/
/** Displays image resources, for example Bitmap or Drawable resources. TapImageView is also commonly used to apply
 * tints to an image and handle image scaling.
 * **/
open class TapImageView(context: Context?, attrs: AttributeSet?) : AppCompatImageView(context, attrs),
    TapView<ImageViewTheme> {
    override fun setTheme(theme: ImageViewTheme) {
        theme.imageResource?.let {setImageResource(R.drawable.tap_logo)  }
        theme.contentDescription?.let { contentDescription = "Tap Logo"
        }
    }
}