package company.tap.tapuilibrary.uikit.views

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.Typeface
import android.graphics.drawable.GradientDrawable
import android.util.AttributeSet
import android.view.Gravity
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.annotation.DrawableRes
import androidx.cardview.widget.CardView
import androidx.core.view.setMargins
import company.tap.tapuilibrary.R
import company.tap.tapuilibrary.themekit.ThemeManager
import company.tap.tapuilibrary.uikit.animation.MorphingAnimation
import company.tap.tapuilibrary.uikit.animation.MorphingAnimation.AnimationTarget.*
import company.tap.tapuilibrary.uikit.datasource.ActionButtonDataSource
import company.tap.tapuilibrary.uikit.datasource.AnimationDataSource
import company.tap.tapuilibrary.uikit.enums.ActionButtonState
import company.tap.tapuilibrary.uikit.enums.ActionButtonState.ERROR
import company.tap.tapuilibrary.uikit.enums.ActionButtonState.SUCCESS
import company.tap.tapuilibrary.uikit.interfaces.TapActionButtonInterface
import company.tap.tapuilibrary.uikit.ktx.setImage


/**
 *
 * Created by Mario Gamal on 6/24/20
 * Copyright © 2020 Tap Payments. All rights reserved.
 *
 */
class TabAnimatedActionButton : CardView, MorphingAnimation.OnAnimationEndListener,
    TapLoadingView.OnProgressCompletedListener {

    private lateinit var morphingAnimation: MorphingAnimation
    private lateinit var state: ActionButtonState
    private var dataSource: ActionButtonDataSource? = null
    private var backgroundDrawable: GradientDrawable = GradientDrawable()
    private var actionButtonInterface: TapActionButtonInterface? = null
    private var tapLoadingView: TapLoadingView? = null

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
        morphingAnimation =
            MorphingAnimation(this)
        morphingAnimation.setAnimationEndListener(this)
    }

    /**
     * public setter for action button interface
     *
     * @param actionButtonInterface
     */
    fun setButtonInterface(actionButtonInterface: TapActionButtonInterface) {
        this.actionButtonInterface = actionButtonInterface
    }

    fun setButtonDataSource(dataSource: ActionButtonDataSource,  isValid: Boolean = false) {
        this.dataSource = dataSource
        if (isValid)
        {
            initValidBackground()
            setDataSource(Color.parseColor(ThemeManager.getValue("actionButton.Valid.paymentBackgroundColor")), Color.parseColor(ThemeManager.getValue("actionButton.Valid.titleLabelColor")))
        } else{
            initInvalidBackground()
            setDataSource(Color.parseColor(ThemeManager.getValue("actionButton.Invalid.backgroundColor")), Color.parseColor(ThemeManager.getValue("actionButton.Invalid.titleLabelColor")))
        }
        addView(getTextView())
    }

    fun addTapLoadingView() {
        tapLoadingView =
            TapLoadingView(context, null)
        tapLoadingView?.setOnProgressCompleteListener(this)
        addChildView(tapLoadingView!!)
    }

    fun changeButtonState(state: ActionButtonState) {
        this.state = state
        addTapLoadingView()
        startStateAnimation()
        when (state) {
             SUCCESS -> {
                addChildView(getImageView(R.drawable.success,1) {})
            }
            ERROR -> {
                addChildView(getImageView(R.drawable.error_gif,1) {})
            }

        }
    }



    /**
     * setup the background drawable color and corner radius from datasource
     */
    private fun initBackground() {
        dataSource?.cornerRadius?.let {
            backgroundDrawable.cornerRadius = it
        }
        dataSource?.backgroundColor?.let {
            backgroundDrawable.color = ColorStateList.valueOf(it)
        }
        background = backgroundDrawable
        elevation = 0F
    }

    private fun initValidBackground() {
        dataSource?.cornerRadius?.let {
            backgroundDrawable.cornerRadius = it
        }
        backgroundDrawable.color = ColorStateList.valueOf(Color.parseColor(ThemeManager.getValue("actionButton.Valid.paymentBackgroundColor")))
//        dataSource?.backgroundColor?.let {
//            backgroundDrawable.color = ColorStateList.valueOf(it)
//        }
        background = backgroundDrawable
        elevation = 0F
    }


    private fun setDataSource(backgroundColor: Int, textColor:Int): ActionButtonDataSource {
        return ActionButtonDataSource(
            text = "Pay",
            textSize = 18f,
            textColor = Color.WHITE,
            cornerRadius = 100f,
            successImageResources = R.drawable.checkmark,
            backgroundColor = resources.getColor(backgroundColor)
        )
    }

    private fun initInvalidBackground() {
        dataSource?.cornerRadius?.let {
            backgroundDrawable.cornerRadius = it
        }
        backgroundDrawable.color = ColorStateList.valueOf(Color.parseColor(ThemeManager.getValue("actionButton.Invalid.backgroundColor")))
//        dataSource?.backgroundColor?.let {
//            backgroundDrawable.color = ColorStateList.valueOf(it)
//        }
        background = backgroundDrawable
        elevation = 0F
    }

    private fun getTextView(): TextView {
        val textView = TextView(context)
        textView.typeface = Typeface.create("sans-serif", Typeface.NORMAL)
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

     fun getImageView(@DrawableRes imageRes: Int, gifLoopCount: Int,  actionAfterAnimationDone: ()-> Unit): ImageView {
        val image = ImageView(context)
        val params = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.MATCH_PARENT

        )
        params.setMargins(20)
        image.layoutParams = params
      return image.setImage(image,imageRes,gifLoopCount, actionAfterAnimationDone)
    }

    private fun startStateAnimation() {
        val animationDataSource =
            AnimationDataSource(
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
        morphingAnimation.start(animationDataSource, WIDTH, HEIGHT, CORNERS)
    }


    /**
     * accept any view to be added inside the action button
     *
     * @param view the child view
     */
    fun addChildView(view: View) {
//        AnimationEngine.applyTransition(this)
        removeAllViews()
        addView(view)
    }

    /**
     *
     */
    override fun onMorphAnimationEnd() {
        tapLoadingView?.completeProgress()
    }

    override fun onProgressCompleted() {
        when (state) {
            ERROR -> {
                dataSource?.errorImageResources?.let {
                    addChildView(getImageView(it,1) {})
                }
                dataSource?.errorColor?.let {
//                    AnimationEngine.applyTransition(this)
                    backgroundDrawable.color = ColorStateList.valueOf(it)
                }
            }
            SUCCESS -> dataSource?.successImageResources?.let {
                addChildView(getImageView(it,1) {})
            }
        }
    }

    /**
     * Constants values
     */
    companion object {
        const val MAX_CORNERS = 100f
        const val MAX_RADIUS = 100
        const val MAX_DURATION = 2000
    }
}