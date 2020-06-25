package company.tap.tapuilibrary.views

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import android.util.AttributeSet
import android.view.Gravity
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.DrawableRes
import androidx.cardview.widget.CardView
import company.tap.tapuilibrary.animation.AnimationEngine
import company.tap.tapuilibrary.animation.MorphingAnimation
import company.tap.tapuilibrary.animation.MorphingAnimation.AnimationTarget.*
import company.tap.tapuilibrary.datasource.ActionButtonDataSource
import company.tap.tapuilibrary.datasource.AnimationDataSource
import company.tap.tapuilibrary.enums.ActionButtonState
import company.tap.tapuilibrary.enums.ActionButtonState.*
import company.tap.tapuilibrary.interfaces.TapActionButtonInterface

/**
 *
 * Created by Mario Gamal on 6/24/20
 * Copyright © 2020 Tap Payments. All rights reserved.
 *
 */
class TabAnimatedActionButton : CardView, MorphingAnimation.OnAnimationEndListener {

    private lateinit var morphingAnimation: MorphingAnimation
    private lateinit var state: ActionButtonState
    private var dataSource: ActionButtonDataSource? = null
    private var backgroundDrawable: GradientDrawable = GradientDrawable()
    var actionButtonInterface: TapActionButtonInterface? = null

    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        init()
    }

    private fun init() {
        morphingAnimation = MorphingAnimation(this)
        morphingAnimation.setAnimationEndListener(this)
    }

    fun setButtonInterface(actionButtonInterface: TapActionButtonInterface) {
        this.actionButtonInterface = actionButtonInterface
    }

    fun setButtonDataSource(dataSource: ActionButtonDataSource) {
        this.dataSource = dataSource
        initBackground()
        addView(getTextView())
    }

    fun changeButtonState(state: ActionButtonState) {
        this.state = state
        when (state) {
            ERROR, SUCCESS -> {
                startStateAnimation()
            }

        }
    }

    private fun initBackground() {
        dataSource?.cornerRadius?.let {
            backgroundDrawable.cornerRadius = it
        }
        dataSource?.backgroundColor?.let {
            backgroundDrawable.color = ColorStateList.valueOf(it)
        }
        background = backgroundDrawable
    }

    private fun getTextView(): TextView {
        val textView = TextView(context)
        dataSource?.text?.let {
            textView.text = it
        }
        dataSource?.textSize?.let {
            textView.textSize = it
        }
        dataSource?.textColor?.let {
            textView.setTextColor(it)
        }
        textView.gravity = Gravity.CENTER
        return textView
    }

    private fun getImageView(@DrawableRes imageRes: Int): ImageView {
        val image = ImageView(context)
        image.setImageResource(imageRes)
        return image
    }

    private fun startStateAnimation() {
        val animationDataSource = AnimationDataSource(
            fromHeight = height,
            toHeight = MAX_RADIUS,
            fromWidth = width,
            toWidth = MAX_RADIUS,
            fromCorners = dataSource?.cornerRadius,
            toCorners = MAX_CORNERS,
            fromColor = dataSource?.backgroundColor,
            toColor = dataSource?.errorColor,
            duration = MAX_DURATION,
            background = backgroundDrawable
        )
        morphingAnimation.start(animationDataSource, WIDTH, HEIGHT, COLOR, CORNERS)
    }

    private fun addChildView(view: View) {
        AnimationEngine.applyTransition(this)
        removeAllViews()
        addView(view)
    }

    override fun onMorphAnimationEnd() {
        when (state) {
            ERROR -> dataSource?.errorImageResources?.let {
                addChildView(getImageView(it))
            }

            SUCCESS -> dataSource?.successImageResources?.let {
                addChildView(getImageView(it))
            }
        }

    }

    companion object {
        const val MAX_CORNERS = 100f
        const val MAX_RADIUS = 150
        const val MAX_DURATION = 2000
    }
}