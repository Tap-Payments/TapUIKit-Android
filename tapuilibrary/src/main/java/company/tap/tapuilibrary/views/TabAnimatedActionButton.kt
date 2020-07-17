package company.tap.tapuilibrary.views

import android.R.attr.resource
import android.content.Context
import android.content.res.ColorStateList
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
//import android.graphics.drawable.Drawable
//import android.graphics.drawable.GradientDrawable
import android.util.AttributeSet
import android.view.Gravity
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.annotation.DrawableRes
import androidx.cardview.widget.CardView
import androidx.core.view.setMargins
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.resource.gif.GifDrawable
import com.bumptech.glide.request.RequestListener
import company.tap.tapuilibrary.R
import company.tap.tapuilibrary.animation.AnimationEngine
import company.tap.tapuilibrary.animation.MorphingAnimation
import company.tap.tapuilibrary.animation.MorphingAnimation.AnimationTarget.*
import company.tap.tapuilibrary.datasource.ActionButtonDataSource
import company.tap.tapuilibrary.datasource.AnimationDataSource
import company.tap.tapuilibrary.enums.ActionButtonState
import company.tap.tapuilibrary.enums.ActionButtonState.*
import company.tap.tapuilibrary.interfaces.TapActionButtonInterface
import company.tap.tapuilibrary.ktx.setImage


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
        morphingAnimation = MorphingAnimation(this)
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

    fun setButtonDataSource(dataSource: ActionButtonDataSource) {
        this.dataSource = dataSource
        initBackground()
        addView(getTextView())
    }

    fun addTapLoadingView() {
        tapLoadingView = TapLoadingView(context, null)
        tapLoadingView?.setOnProgressCompleteListener(this)
        addChildView(tapLoadingView!!)
    }

    fun changeButtonState(state: ActionButtonState) {
        this.state = state
        when (state) {
            ERROR,  SUCCESS -> {
//                tapProgressIndicatorInterface?.onProgressEnd()
                tapLoadingView?.setOnProgressCompleteListener(this)
                addTapLoadingView()
                startStateAnimation()
            }
            LOADING -> {
//                addView(getImageView(R.drawable.loader,2))
                addTapLoadingView()
                startStateAnimation()
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

    private fun getImageView(@DrawableRes imageRes: Int, gifLoopCount: Int,  actionAfterAnimationDone: ()-> Unit): ImageView {
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
//        tapLoadingView?.completeProgress()
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