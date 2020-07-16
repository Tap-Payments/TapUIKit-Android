package company.tap.tapuilibrary.views

import android.content.Context
import android.util.AttributeSet
import android.widget.ImageView
import android.widget.LinearLayout
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.resource.gif.GifDrawable
import com.bumptech.glide.request.RequestListener
import company.tap.tapuilibrary.R
import company.tap.tapuilibrary.interfaces.TapProgressIndicatorInterface
import company.tap.tapuilibrary.ktx.setImage


/**
 *
 * Created by Mario Gamal on 6/30/20
 * Copyright © 2020 Tap Payments. All rights reserved.
 *
 */
class TapLoadingView(context: Context?, attrs: AttributeSet?) :
    LinearLayout(context, attrs), TapProgressIndicatorInterface {

//    var innerCircle: TapProgressIndicator
//    var outerCircle: TapProgressIndicator
    var tapLoadingImage: ImageView
    var onProgressCompletedListener: OnProgressCompletedListener? = null

    init {
        inflate(context, R.layout.tap_loading_view, this)
//        innerCircle = findViewById(R.id.inner_circle)
//        outerCircle = findViewById(R.id.outer_circle)
        tapLoadingImage = findViewById(R.id.tapLoadingImage)
//        outerCircle.setTapProgressIndicatorInterface(this)
        tapLoadingImage.setImage(tapLoadingImage,R.drawable.loader,3)
    }

    fun setOnProgressCompleteListener(onProgressCompletedListener: OnProgressCompletedListener) {
        this.onProgressCompletedListener = onProgressCompletedListener
    }

    fun completeProgress() {
        onProgressEnd()
//        innerCircle.setProgressCompat(100, true)
//        outerCircle.setProgressCompat(100, true)
    }

    override fun onProgressEnd() {
        onProgressCompletedListener?.onProgressCompleted()
    }

    interface OnProgressCompletedListener {
        fun onProgressCompleted()
    }
}