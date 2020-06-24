package company.tap.tapuilibrary.animation

import android.animation.*
import company.tap.tapuilibrary.views.TabAnimatedActionButton

/**
 * Created by Mario Gamal on 6/24/20
 * Copyright © 2020 Tap Payments. All rights reserved.
 */
class MorphingAnimation(private val mParams: Params) {

    private var cornerAnimation: ObjectAnimator? = null
    private var bgColorAnimation: ObjectAnimator? = null
    private var heightAnimation: ValueAnimator? = null
    private var widthAnimation: ValueAnimator? = null

    class Params private constructor(val button: TabAnimatedActionButton) {
        var fromCornerRadius: Float? = null
        var toCornerRadius: Float?  = null
        var fromHeight: Int?  = null
        var toHeight: Int?  = null
        var fromWidth: Int?  = null
        var toWidth: Int?  = null
        var fromColor: Int?  = null
        var toColor: Int?  = null
        var duration: Int?  = null
        var animationListener: TabAnimatedActionButton.AnimationListener? = null

        fun duration(duration: Int): Params {
            this.duration = duration
            return this
        }

        fun listener(animationListener: TabAnimatedActionButton.AnimationListener): Params {
            this.animationListener = animationListener
            return this
        }

        fun color(
            fromColor: Int,
            toColor: Int
        ): Params {
            this.fromColor = fromColor
            this.toColor = toColor
            return this
        }

        fun cornerRadius(
            fromCornerRadius: Int,
            toCornerRadius: Int
        ): Params {
            this.fromCornerRadius = fromCornerRadius.toFloat()
            this.toCornerRadius = toCornerRadius.toFloat()
            return this
        }

        fun height(
            fromHeight: Int,
            toHeight: Int
        ): Params {
            this.fromHeight = fromHeight
            this.toHeight = toHeight
            return this
        }

        fun width(
            fromWidth: Int,
            toWidth: Int
        ): Params {
            this.fromWidth = fromWidth
            this.toWidth = toWidth
            return this
        }

        companion object {
            fun create(button: TabAnimatedActionButton): Params {
                return Params(
                    button
                )
            }
        }

    }

    fun start() {


        val background = mParams.button.mGradientDrawable
        cornerAnimation = ObjectAnimator.ofFloat(background, "cornerRadius", mParams.fromCornerRadius!!, mParams.toCornerRadius!!)


        bgColorAnimation = ObjectAnimator.ofInt(background, "color", mParams.fromColor!!, mParams.toColor!!)
        bgColorAnimation?.setEvaluator(ArgbEvaluator())


        heightAnimation = ValueAnimator.ofInt(mParams.fromHeight!!, mParams.toHeight!!)
        heightAnimation?.addUpdateListener { valueAnimator: ValueAnimator ->
            val `val` = valueAnimator.animatedValue as Int
            val layoutParams = mParams.button.layoutParams
            layoutParams.height = `val`
            mParams.button.layoutParams = layoutParams
        }


        widthAnimation = ValueAnimator.ofInt(mParams.fromWidth!!, mParams.toWidth!!)
        widthAnimation?.addUpdateListener { valueAnimator: ValueAnimator ->
            val `val` = valueAnimator.animatedValue as Int
            val layoutParams = mParams.button.layoutParams
            layoutParams.width = `val`
            mParams.button.layoutParams = layoutParams
        }


        val animatorSet = AnimatorSet()
        mParams.duration?.let {
            animatorSet.duration = it.toLong()
        }
        animatorSet.playTogether(cornerAnimation, bgColorAnimation, heightAnimation, widthAnimation)
        animatorSet.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator) {
                    mParams.animationListener?.onAnimationEnd()
            }
        })
        animatorSet.start()
    }

}