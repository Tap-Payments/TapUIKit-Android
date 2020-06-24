package company.tap.tapuilibrary.utils

import android.animation.*
import android.graphics.Color
import company.tap.tapuilibrary.views.TabAnimatedActionButton

/**
 * Created by Mario Gamal on 6/24/20
 * Copyright © 2020 Tap Payments. All rights reserved.
 */
class MorphingAnimation(private val mParams: Params) {
    class Params private constructor(val button: TabAnimatedActionButton) {
        var fromCornerRadius = 0f
        var toCornerRadius = 0f
        var fromHeight = 0
        var toHeight = 0
        var fromWidth = 0
        var toWidth = 0
        var fromColor = 0
        var toColor = 0
        var duration = 0
        var animationListener: TabAnimatedActionButton.AnimationListener? =
            null

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
                return Params(button)
            }
        }

    }

    fun start() {
        val background = mParams.button.mGradientDrawable
        val cornerAnimation = ObjectAnimator.ofFloat(
            background,
            "cornerRadius",
            mParams.fromCornerRadius,
            mParams.toCornerRadius
        )
        val bgColorAnimation =
            ObjectAnimator.ofInt(background, "color", mParams.fromColor, mParams.toColor)
        bgColorAnimation.setEvaluator(ArgbEvaluator())
        val heightAnimation =
            ValueAnimator.ofInt(mParams.fromHeight, mParams.toHeight)
        heightAnimation.addUpdateListener { valueAnimator: ValueAnimator ->
            val `val` = valueAnimator.animatedValue as Int
            val layoutParams = mParams.button.layoutParams
            layoutParams.height = `val`
            mParams.button.layoutParams = layoutParams
        }
        val widthAnimation = ValueAnimator.ofInt(mParams.fromWidth, mParams.toWidth)
        widthAnimation.addUpdateListener { valueAnimator: ValueAnimator ->
            val `val` = valueAnimator.animatedValue as Int
            val layoutParams = mParams.button.layoutParams
            layoutParams.width = `val`
            mParams.button.layoutParams = layoutParams
        }
        val animatorSet = AnimatorSet()
        animatorSet.duration = mParams.duration.toLong()
        animatorSet.playTogether(cornerAnimation, bgColorAnimation, heightAnimation, widthAnimation)
        animatorSet.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator) {
                if (mParams.animationListener != null) {
                    mParams.animationListener!!.onAnimationEnd()
                }
            }
        })
        animatorSet.start()
    }

}