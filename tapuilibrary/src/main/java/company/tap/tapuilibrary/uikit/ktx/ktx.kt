package company.tap.tapuilibrary.uikit.ktx

import android.R.attr.radius
import android.graphics.drawable.Drawable
import android.view.View
import android.widget.ImageView
import androidx.core.view.ViewCompat
import androidx.vectordrawable.graphics.drawable.Animatable2Compat
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.resource.gif.GifDrawable
import com.bumptech.glide.request.RequestListener
import com.google.android.material.shape.CornerFamily
import com.google.android.material.shape.MaterialShapeDrawable
import com.google.android.material.shape.ShapeAppearanceModel


/**
 * Created by OLAMONIR on 7/16/20.

Copyright (c) 2020  Tap Payments.
All rights reserved.
 **/

 fun ImageView.setImage(image: ImageView, imageRes:Int, gifLoopCount: Int, actionAfterAnimationDone: ()-> Unit): ImageView {
    Glide.with(this).asGif().load(imageRes).useAnimationPool(true) .listener(object :
        RequestListener<GifDrawable> {
        override fun onResourceReady(resource: GifDrawable?, model: Any?, target: com.bumptech.glide.request.target.Target<GifDrawable>?, dataSource: DataSource?, isFirstResource: Boolean): Boolean {
            if (resource is GifDrawable) {
                resource.setLoopCount(gifLoopCount)
                resource.registerAnimationCallback(object :
                    Animatable2Compat.AnimationCallback() {
                    override fun onAnimationEnd(drawable: Drawable) {
                        //do whatever after specified number of loops complete
                        actionAfterAnimationDone()
                    }
                })
            }
            return false
        }
        override fun onLoadFailed(e: GlideException?, model: Any?, target:com.bumptech.glide.request.target.Target<GifDrawable>?, isFirstResource: Boolean): Boolean {
            return false
        }
    }) .into(image)
    return image
}


/**
 * Method to draw bordered view
 * setBorderedView ( view: View, cornerRadius:Float,strokeWidth: Float, strokeColor: Int,tintColor: Int )
 */

fun setBorderedView(view: View, cornerRadius:Float,strokeWidth: Float, strokeColor: Int,tintColor: Int, shadowColor: Int) {
    val shapeAppearanceModel = ShapeAppearanceModel()
        .toBuilder()
        .setAllCorners(CornerFamily.ROUNDED, cornerRadius)
        .build()
    val shapeDrawable = MaterialShapeDrawable(shapeAppearanceModel)
    ViewCompat.setBackground(view, shapeDrawable)
    shapeDrawable.setStroke(strokeWidth, strokeColor)
    shapeDrawable.setShadowColor(shadowColor)
    shapeDrawable.setTint(tintColor)
    shapeDrawable.shadowRadius= 10
    shapeDrawable.elevation = 5f
}



fun setTopBorders(view: View, cornerRadius:Float,strokeWidth: Float, strokeColor: Int,tintColor: Int, shadowColor: Int) {
    val shapeAppearanceModel = ShapeAppearanceModel()
        .toBuilder()
        .setTopLeftCorner(CornerFamily.ROUNDED, cornerRadius)
        .setTopRightCorner(CornerFamily.ROUNDED, cornerRadius)
        .build()
    val shapeDrawable = MaterialShapeDrawable(shapeAppearanceModel)
    ViewCompat.setBackground(view, shapeDrawable)
    shapeDrawable.setStroke(strokeWidth, strokeColor)
    shapeDrawable.setShadowColor(shadowColor)
    shapeDrawable.setTint(tintColor)
    shapeDrawable.shadowRadius= 10
    shapeDrawable.elevation = 20f
}
fun setBottomBorders(view: View, cornerRadius:Float,strokeWidth: Float, strokeColor: Int,tintColor: Int, shadowColor: Int) {
    val shapeAppearanceModel = ShapeAppearanceModel()
        .toBuilder()
        .setBottomLeftCorner(CornerFamily.ROUNDED, cornerRadius)
        .setBottomRightCorner(CornerFamily.ROUNDED, cornerRadius)
        .build()
    val shapeDrawable = MaterialShapeDrawable(shapeAppearanceModel)
    ViewCompat.setBackground(view, shapeDrawable)
    shapeDrawable.setStroke(strokeWidth, strokeColor)
    shapeDrawable.setShadowColor(shadowColor)
    shapeDrawable.setTint(tintColor)
    shapeDrawable.shadowRadius= 10
    shapeDrawable.elevation = 20f
}
















