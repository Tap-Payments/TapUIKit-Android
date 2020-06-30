package company.tap.tapuilibrary.views

import android.content.Context
import android.util.AttributeSet
import android.widget.LinearLayout
import company.tap.tapuilibrary.R
import company.tap.tapuilibrary.atoms.TapProgressIndicator
import company.tap.tapuilibrary.interfaces.TapProgressIndicatorInterface

/**
 *
 * Created by Mario Gamal on 6/30/20
 * Copyright © 2020 Tap Payments. All rights reserved.
 *
 */
class TapLoadingView(context: Context?, attrs: AttributeSet?) :
    LinearLayout(context, attrs), TapProgressIndicatorInterface {

    var innerCircle: TapProgressIndicator
    var outerCircle: TapProgressIndicator
    var onProgressCompletedListener: OnProgressCompletedListener? = null

    init {
        inflate(context, R.layout.tap_loading_view, this)
        innerCircle = findViewById(R.id.inner_circle)
        outerCircle = findViewById(R.id.outer_circle)
        outerCircle.setTapProgressIndicatorInterface(this)
    }

    fun setOnProgressCompleteListener(onProgressCompletedListener: OnProgressCompletedListener) {
        this.onProgressCompletedListener = onProgressCompletedListener
    }

    fun completeProgress() {
        innerCircle.setProgressCompat(100, true)
        outerCircle.setProgressCompat(100, true)
    }

    override fun onProgressEnd() {
        onProgressCompletedListener?.onProgressCompleted()
    }

    interface OnProgressCompletedListener {
        fun onProgressCompleted()
    }
}