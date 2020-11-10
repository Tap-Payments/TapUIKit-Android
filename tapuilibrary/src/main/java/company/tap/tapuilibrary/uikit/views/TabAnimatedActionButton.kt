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
import company.tap.tapuilibrary.fontskit.enums.TapFont
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
 * Created on 6/24/20
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
    private val textView by lazy {TextView(context)  }


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
        initActionButtonDataSource()
    }

    private fun initActionButtonDataSource(backgroundColor: Int? = null, textColor:Int? = null, buttonText: String? = null ){
        dataSource = ActionButtonDataSource(
            text = buttonText ?: "Pay",
            textSize = 18f,
            textColor = textColor ?: Color.parseColor(ThemeManager.getValue("actionButton.Invalid.titleLabelColor")),
            cornerRadius = 100f,
            successImageResources = R.drawable.checkmark,
            backgroundColor = backgroundColor ?: Color.parseColor(ThemeManager.getValue("actionButton.Invalid.backgroundColor"))
        )
    }

    /**
     * public setter for action button interface
     *
     * @param actionButtonInterface
     */
    fun setButtonInterface(actionButtonInterface: TapActionButtonInterface) {
        this.actionButtonInterface = actionButtonInterface
    }

    fun setButtonDataSource(isValid: Boolean = false,lang : String? = null, buttonText: String?= null, backgroundColor: Int, textColor:Int? = null) {
        if (isValid)
        {
            initValidBackground(backgroundColor)
            initActionButtonDataSource(backgroundColor, textColor,buttonText)
        } else{
            initInvalidBackground(backgroundColor)
            initActionButtonDataSource(backgroundColor, textColor, buttonText)
        }
        removeAllViews()
        addView(getTextView(lang?: "en"))
    }

    fun addTapLoadingView() {
        tapLoadingView = TapLoadingView(context, null)
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
     * setup the initValidBackground background drawable color and corner radius from datasource
     */

    private fun initValidBackground(backgroundColor: Int) {
        dataSource?.cornerRadius?.let {
            backgroundDrawable.cornerRadius = it
        }
        backgroundDrawable.color =ColorStateList.valueOf(backgroundColor)?: ColorStateList.valueOf(Color.parseColor(ThemeManager.getValue("actionButton.Valid.paymentBackgroundColor")))
        background = backgroundDrawable
        elevation = 0F
    }

    /**
     * setup the initInvalidBackground background drawable color and corner radius from datasource
     */
    private fun initInvalidBackground(backgroundColor: Int) {
        dataSource?.cornerRadius?.let {
            backgroundDrawable.cornerRadius = it
        }
        backgroundDrawable.color = ColorStateList.valueOf(backgroundColor)
        background = backgroundDrawable
        elevation = 0F
    }

    private fun getTextView(lang : String): TextView {
        if (lang == "en") setFontEnglish(textView)
        else setFontArabic(textView)


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

    fun setFontEnglish(textView:TextView ){
        textView.typeface = Typeface.createFromAsset(
            context?.assets, TapFont.tapFontType(
                TapFont.RobotoLight
            )
        )
    }

    fun setFontArabic(textView:TextView){
        textView.typeface = Typeface.createFromAsset(
            context?.assets, TapFont.tapFontType(
                TapFont.TajawalLight
            )
        )
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