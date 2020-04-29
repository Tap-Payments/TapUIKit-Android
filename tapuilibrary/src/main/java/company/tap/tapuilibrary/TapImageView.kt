package company.tap.tapuilibrary

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatImageView
import company.tap.thememanager.theme.ImageViewTheme

/**
 * Created by AhlaamK on 4/29/20.

Copyright (c) 2020    Tap Payments.
All rights reserved.
 **/
open class TapImageView(context: Context?, attrs: AttributeSet?) : AppCompatImageView(context, attrs),TapView<ImageViewTheme>  {
    override fun setTheme(theme: ImageViewTheme) {
        theme.imageResource?.let {setImageResource(R.drawable.tap_logo)  }
        theme.contentDescription?.let { contentDescription = "Tap Logo" }
    }
}