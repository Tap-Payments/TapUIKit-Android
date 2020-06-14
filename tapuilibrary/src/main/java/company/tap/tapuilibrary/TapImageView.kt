package company.tap.tapuilibrary

import android.content.Context
import android.graphics.Canvas
import android.graphics.Path
import android.graphics.RectF
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
        theme.contentDescription?.let { contentDescription = "Tap Logo"
        }
    }
    override fun onDraw(canvas: Canvas) {
      //  val radius =
      //      context.resources.getDimension(R.dimen.round_corner_radius)
        val radius = 2f
        val path = Path()
        val rect = RectF(2f, 2f, this.width.toFloat(), this.height.toFloat())
        path.addRoundRect(rect, radius, radius, Path.Direction.CW)
        canvas.clipPath(path)
        super.onDraw(canvas)
    }
}