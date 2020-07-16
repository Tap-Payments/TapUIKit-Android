package company.tap.tapuilibrary.ktx

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.resource.gif.GifDrawable
import com.bumptech.glide.request.RequestListener

/**
 * Created by OLAMONIR on 7/16/20.

Copyright (c) 2020  Tap Payments.
All rights reserved.
 **/

 fun ImageView.setImage(image: ImageView, imageRes:Int, gifLoopCount: Int): ImageView {
    Glide.with(this).asGif().load(imageRes).useAnimationPool(true) .listener(object :
        RequestListener<GifDrawable> {
        override fun onResourceReady(resource: GifDrawable?, model: Any?, target: com.bumptech.glide.request.target.Target<GifDrawable>?, dataSource: DataSource?, isFirstResource: Boolean): Boolean {
            if (resource is GifDrawable) {
                resource.setLoopCount(gifLoopCount)
            }
            return false
        }
        override fun onLoadFailed(e: GlideException?, model: Any?, target:com.bumptech.glide.request.target.Target<GifDrawable>?, isFirstResource: Boolean): Boolean {
            return false
        }
    }) .into(image)
    return image
}