package company.tap.tapuilibrary.views

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.util.AttributeSet
import androidx.cardview.widget.CardView
import company.tap.tapuilibrary.utils.MorphingAnimation

/**
 *
 * Created by Mario Gamal on 6/24/20
 * Copyright © 2020 Tap Payments. All rights reserved.
 *
 */
class TabAnimatedActionButton: CardView {

    var mGradientDrawable: GradientDrawable = GradientDrawable()

    constructor(context: Context) : super(context) {
        init(context)
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        init(context)
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init(context)
    }

    private fun init(context: Context) {
        mGradientDrawable.cornerRadius = 10f
        mGradientDrawable.color = ColorStateList.valueOf(Color.BLUE)
        background = mGradientDrawable
    }

    fun startAnimation(params : MorphingAnimation.Params) {
        MorphingAnimation(params).start()
    }

    interface AnimationListener {
        fun onAnimationEnd()
    }
}