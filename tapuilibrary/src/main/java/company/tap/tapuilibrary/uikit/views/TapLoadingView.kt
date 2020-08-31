package company.tap.tapuilibrary.uikit.views

import android.content.Context
import android.util.AttributeSet
import android.widget.ImageView
import android.widget.LinearLayout
import company.tap.tapuilibrary.R
import company.tap.tapuilibrary.uikit.interfaces.TapProgressIndicatorInterface
import company.tap.tapuilibrary.uikit.ktx.setImage


/**
 *
 * Created by Mario Gamal on 6/30/20
 * Copyright © 2020 Tap Payments. All rights reserved.
 *
 */
class TapLoadingView(context: Context?, attrs: AttributeSet?) :
    LinearLayout(context, attrs) ,
    TapProgressIndicatorInterface {

    var tapLoadingImage: ImageView
    var onProgressCompletedListener: OnProgressCompletedListener? = null

    init {
        inflate(context, R.layout.tap_loading_view, this)
        tapLoadingImage = findViewById(R.id.tapLoadingImage)
        tapLoadingImage.setImage(tapLoadingImage,R.drawable.loader,1) {onProgressEnd()}
    }

    fun setOnProgressCompleteListener(onProgressCompletedListener: OnProgressCompletedListener) {
        this.onProgressCompletedListener = onProgressCompletedListener
    }

    fun completeProgress() {
        tapLoadingImage.setImage(tapLoadingImage,R.drawable.loader,1) {onProgressEnd()}
    }
    override fun onProgressEnd() {
        onProgressCompletedListener?.onProgressCompleted()
    }

    interface OnProgressCompletedListener {
        fun onProgressCompleted()
    }
}