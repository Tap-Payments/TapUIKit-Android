package company.tap.tapuilibrary.views

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.transition.AutoTransition
import android.transition.TransitionManager
import android.util.AttributeSet
import android.view.Gravity
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import company.tap.tapuilibrary.R
import company.tap.tapuilibrary.animation.AnimationEngine
import company.tap.tapuilibrary.animation.MorphingAnimation
import company.tap.tapuilibrary.datasource.ActionButtonDataSource
import company.tap.tapuilibrary.enums.ActionButtonState
import company.tap.tapuilibrary.interfaces.TapActionButtonInterface

/**
 *
 * Created by Mario Gamal on 6/24/20
 * Copyright © 2020 Tap Payments. All rights reserved.
 *
 */
class TabAnimatedActionButton: CardView {

    private lateinit var payText: TextView
    private var dataSource: ActionButtonDataSource? = null
    var mGradientDrawable: GradientDrawable = GradientDrawable()
    var actionButtonInterface: TapActionButtonInterface? = null

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
        mGradientDrawable.color = ColorStateList.valueOf(context.resources.getColor(R.color.button_green))
        payText = TextView(context)
        payText.text = "Pay!"
        payText.textSize = 20f
        payText.gravity = Gravity.CENTER
        payText.setTextColor(Color.WHITE)
        addView(payText)
        background = mGradientDrawable
    }

    fun setButtonInterface(actionButtonInterface: TapActionButtonInterface) {
        this.actionButtonInterface = actionButtonInterface
    }

    fun setButtonDataSource(dataSource: ActionButtonDataSource) {

    }

    fun changeButtonState(state: ActionButtonState) {

    }

    fun addChildView(view: View) {
        AnimationEngine.applyTransition(this)
        removeAllViews()
        addView(view)
    }

    fun onMorphingAnimationEnd() {
        val errorIcon = ImageView(context)
        errorIcon.setImageResource(R.drawable.ic_error)
        addChildView(errorIcon)
    }
}