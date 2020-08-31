package company.tap.tapuilibrary.uikit.animation

import android.animation.*
import android.graphics.drawable.GradientDrawable
import android.view.View
import androidx.annotation.ColorInt
import company.tap.tapuilibrary.uikit.animation.MorphingAnimation.AnimationTarget.*
import company.tap.tapuilibrary.uikit.datasource.AnimationDataSource

/**
 * Created by Mario Gamal on 6/24/20
 * Copyright © 2020 Tap Payments. All rights reserved.
 */
class MorphingAnimation(private val animatedView: View) {

    private var animationEndListener: OnAnimationEndListener? = null

    fun setAnimationEndListener(animationEndListener: OnAnimationEndListener) {
        this.animationEndListener = animationEndListener
    }

    fun start(dataSource: AnimationDataSource, vararg targets: AnimationTarget) {
        val animators = ArrayList<Animator>()
        targets.forEach {
            when (it) {
                CORNERS -> {
                    if (dataSource.background != null && dataSource.fromCorners != null && dataSource.toCorners != null)
                        animators.add(
                            getCornerAnimation(
                                dataSource.background,
                                dataSource.fromCorners,
                                dataSource.toCorners
                            )
                        )
                }
                COLOR -> {
                    if (dataSource.background != null && dataSource.fromColor != null && dataSource.toColor != null)
                        animators.add(
                            getColorAnimation(
                                dataSource.background,
                                dataSource.fromColor,
                                dataSource.toColor
                            )
                        )
                }
                HEIGHT -> {
                    if (dataSource.fromHeight != null && dataSource.toHeight != null)
                        animators.add(
                            getDimensionAnimation(
                                HEIGHT,
                                dataSource.fromHeight,
                                dataSource.toHeight
                            )
                        )
                }
                WIDTH -> {
                    if (dataSource.fromWidth != null && dataSource.toWidth != null)
                        animators.add(
                            getDimensionAnimation(
                                WIDTH,
                                dataSource.fromWidth,
                                dataSource.toWidth
                            )
                        )
                }
            }
        }

        val animatorSet = AnimatorSet()
        dataSource.duration?.let {
            animatorSet.duration = it.toLong()
        }
        animatorSet.playTogether(animators)
        animatorSet.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator) {
                animationEndListener?.onMorphAnimationEnd()
            }
        })
        animatorSet.start()
    }

    private fun getCornerAnimation(background: GradientDrawable, from: Float, to: Float): Animator {
        return ObjectAnimator.ofFloat(background, "cornerRadius", from, to)
    }

    private fun getColorAnimation(
        background: GradientDrawable,
        @ColorInt from: Int,
        @ColorInt to: Int
    ): Animator {
        val bgColorAnimation = ObjectAnimator.ofInt(background, "color", from, to)
        bgColorAnimation?.setEvaluator(ArgbEvaluator())
        return bgColorAnimation
    }

    private fun getDimensionAnimation(dimension: AnimationTarget, from: Int, to: Int): Animator {
        val dimensionAnimation = ValueAnimator.ofInt(from, to)
        dimensionAnimation?.addUpdateListener { valueAnimator ->
            val animatedValue = valueAnimator.animatedValue as Int
            val layoutParams = animatedView.layoutParams
            when (dimension) {
                HEIGHT -> layoutParams.height = animatedValue
                WIDTH -> layoutParams.width = animatedValue
                else -> return@addUpdateListener
            }
            animatedView.layoutParams = layoutParams
        }
        return dimensionAnimation
    }

    enum class AnimationTarget {
        HEIGHT,
        WIDTH,
        COLOR,
        CORNERS
    }

    interface OnAnimationEndListener {
        fun onMorphAnimationEnd()
    }
}