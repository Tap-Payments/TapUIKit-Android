package company.tap.tapuilibrary.uikit.views

import android.animation.ValueAnimator
import android.annotation.SuppressLint
import android.content.Context
import android.content.res.ColorStateList
import android.graphics.*
import android.text.InputFilter
import android.text.TextPaint
import android.text.TextUtils
import android.text.method.MovementMethod
import android.util.AttributeSet
import android.util.Log
import android.view.*
import android.view.animation.DecelerateInterpolator
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import androidx.annotation.ColorInt
import androidx.annotation.Px
import androidx.appcompat.widget.AppCompatEditText
import androidx.core.view.ViewCompat
import company.tap.tapuilibrary.fontskit.enums.TapFont
import company.tap.tapuilibrary.themekit.ThemeManager
import company.tap.tapuilibrary.R

/**
 * Created  on 7/12/20.

Copyright (c) 2020    Tap Payments.
All rights reserved.
 **/



class TapOTPView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = R.attr.otpViewStyle
) : AppCompatEditText(context, attrs, defStyleAttr) {

    private val mViewType: Int

    var mOtpItemCount: Int = 0

    private var mOtpItemWidth: Int = 0
    private var mOtpItemHeight: Int = 0
    private var mOtpItemRadius: Int = 0
    private var mOtpItemSpacing: Int = 0

    private val mPaint: Paint
    private val mTextPaint: TextPaint
    private val mAnimatorTextPaint: Paint


    /**
     * Gets the line colors for the different states (normal, selected, focused) of the CustomOtpView.
     *
     * @attr ref R.styleable#PinView_lineColor
     * @see .setLineColor
     * @see .setLineColor
     */
    var lineColors: ColorStateList? = null
        private set

    /**
     *
     * Return the current color selected for normal line.
     *
     * @return Returns the current item's line color.
     */
    @get:ColorInt
    var currentLineColor =
        Color.parseColor(ThemeManager.getValue("TapOtpView.OtpController.activeBottomColor"))
        //private set
    private var mLineWidth: Int = 0
    @get:ColorInt
    var inactiveLineColor =
        Color.parseColor(ThemeManager.getValue("TapOtpView.OtpController.bottomLineColor"))
    private val mTextRect = Rect()
    private val mItemBorderRect = RectF()
    private val mItemLineRect = RectF()
    private val mPath = Path()
    private val mItemCenterPoint = PointF()

    private var mDefaultAddAnimator: ValueAnimator? = null
    private var isAnimationEnable = false

    private var mBlink: Blink? = null
    private var isCursorVisible: Boolean = false
    private var drawCursor: Boolean = false
    private var mCursorHeight: Float = 0.toFloat()
    private var mCursorWidth: Int = 0
    private var mCursorColor: Int = 0

    /**
     * @return Returns the width of the item's line.
     * @see .setLineWidth
     */
    /**
     * Sets the line width.
     *
     * @attr ref R.styleable#PinView_lineWidth
     * @see .getLineWidth
     */
    var lineWidth: Int
        get() = mLineWidth
        set(@Px borderWidth) {
            mLineWidth = borderWidth
            checkItemRadius()
            requestLayout()
        }

    /**
     * @return Returns the count of items.
     * @see .setItemCount
     */
    /**
     * Sets the count of items.
     *
     * @attr ref R.styleable#PinView_itemCount
     * @see .getItemCount
     */
    var itemCount: Int
        get() = mOtpItemCount
        set(count) {
            mOtpItemCount = count
            setMaxLength(count)
            requestLayout()
        }

    /**
     * @return Returns the radius of square.
     * @see .setItemRadius
     */
    /**
     * Sets the radius of square.
     *
     * @attr ref R.styleable#PinView_itemRadius
     * @see .getItemRadius
     */
    var itemRadius: Int
        get() = mOtpItemRadius
        set(@Px itemRadius) {
            mOtpItemRadius = itemRadius
            checkItemRadius()
            requestLayout()
        }

    /**
     * @return Returns the height of item.
     * @see .setItemHeight
     */
    /**
     * Sets the height of item.
     *
     * @attr ref R.styleable#PinView_itemHeight
     * @see .getItemHeight
     */
    var itemHeight: Int
        get() = mOtpItemHeight
        set(@Px itemHeight) {
            mOtpItemHeight = itemHeight
            updateCursorHeight()
            requestLayout()
        }

    /**
     * @return Returns the width of item.
     * @see .setItemWidth
     */
    /**
     * Sets the width of item.
     *
     * @attr ref R.styleable#PinView_itemWidth
     * @see .getItemWidth
     */
    var itemWidth: Int
        get() = mOtpItemWidth
        set(@Px itemWidth) {
            mOtpItemWidth = itemWidth
            checkItemRadius()
            requestLayout()
        }

    /**
     * @return Returns the width (in pixels) of cursor.
     * @see .setCursorWidth
     */
    //region Cursor

    /**
     * Sets the width (in pixels) of cursor.
     *
     * @attr ref R.styleable#PinView_cursorWidth
     * @see .getCursorWidth
     */
    var cursorWidth: Int
        get() = mCursorWidth
        set(@Px width) {
            mCursorWidth = width
            if (isCursorVisible()) {
                invalidateCursor(false)
            }
        }

    /**
     * Gets the cursor color.
     *
     * @return Return current cursor color.
     * @see .setCursorColor
     */
    /**
     * Sets the cursor color.
     *
     * @param color A color value in the form 0xAARRGGBB.
     * Do not pass a resource ID. To get a color value from a resource ID, call
     * [getColor][android.support.v4.content.ContextCompat.getColor].
     * @attr ref R.styleable#PinView_cursorColor
     * @see .getCursorColor
     */
    var cursorColor: Int
        get() = mCursorColor
        set(@ColorInt color) {
            mCursorColor = color
            if (isCursorVisible()) {
                invalidateCursor(false)
            }
        }


    /**
     * @return Returns the spacing between two items.
     * @see .setItemSpacing
     */
    /**
     * Specifies extra space between two items.
     *
     * @attr ref R.styleable#PinView_itemSpacing
     * @see .getItemSpacing
     */
    var itemSpacing: Int
        @Px
        get() = mOtpItemSpacing
        set(@Px itemSpacing) {
            mOtpItemSpacing = itemSpacing
            requestLayout()
        }


    init {

        val res = resources

        mPaint = Paint(Paint.ANTI_ALIAS_FLAG)

        mPaint.style = Paint.Style.STROKE

        mTextPaint = TextPaint(Paint.ANTI_ALIAS_FLAG)
        mTextPaint.density = res.displayMetrics.density
        mTextPaint.style = Paint.Style.FILL
        mTextPaint.textSize = textSize

        mAnimatorTextPaint = TextPaint(mTextPaint)

        mTextPaint.typeface = Typeface.createFromAsset(
            context.assets, TapFont.tapFontType(
                TapFont.RobotoLight
            )
        )

        val theme = context.theme

        val a = theme.obtainStyledAttributes(attrs, R.styleable.CustomOtpView, defStyleAttr, 0)

        mViewType = a.getInt(
            R.styleable.CustomOtpView_viewType,
            VIEW_TYPE_RECTANGLE
        )
        mOtpItemCount = a.getInt(
            R.styleable.CustomOtpView_itemCount,
            DEFAULT_COUNT
        )
        mOtpItemHeight = a.getDimension(
            R.styleable.CustomOtpView_itemHeight,
            res.getDimensionPixelSize(R.dimen.otp_customotp_view_item_size).toFloat()
        ).toInt()
        mOtpItemWidth = a.getDimension(
            R.styleable.CustomOtpView_itemWidth,
            res.getDimensionPixelSize(R.dimen.otp_customotp_view_item_size).toFloat()
        ).toInt()

        mOtpItemSpacing = a.getDimensionPixelSize(
            R.styleable.CustomOtpView_itemSpacing,
            res.getDimensionPixelSize(R.dimen.otp_customotp_view_item_spacing)
        )




        if (mOtpItemCount /2 == 0)
            for (i in 0 until mOtpItemCount ){
                if(i ==( (mOtpItemCount/2) -1 )) {
                    mOtpItemSpacing = (a.getDimensionPixelSize(
                        R.styleable.CustomOtpView_itemSpacing,
                        res.getDimensionPixelSize(R.dimen.otp_customotp_view_item_spacing) + 2))
                }
            }


        /// set space between
        Log.d("mOtpItemCount", mOtpItemCount.toString())
        println(mOtpItemCount.toString())


        mOtpItemRadius = a.getDimension(R.styleable.CustomOtpView_itemRadius, 0f).toInt()
        mLineWidth = a.getDimension(
            R.styleable.CustomOtpView_lineWidth,
            res.getDimensionPixelSize(R.dimen.otp_customotp_view_item_line_width).toFloat()
        ).toInt()
//        lineColors = a.getColorStateList(R.styleable.CustomOtpView_lineColor)
        lineColors =
            ColorStateList.valueOf(Color.parseColor(ThemeManager.getValue("TapOtpView.OtpController.bottomLineColor")))
        isCursorVisible = a.getBoolean(R.styleable.CustomOtpView_android_cursorVisible, true)
        mCursorColor = a.getColor(R.styleable.CustomOtpView_cursorColor, currentTextColor)
        mCursorWidth = a.getDimensionPixelSize(
            R.styleable.CustomOtpView_cursorWidth,
            res.getDimensionPixelSize(R.dimen.otp_customotp_view_cursor_width)
        )

        a.recycle()

        if (lineColors != null) {
//            currentLineColor = lineColors!!.defaultColor
        }
        updateCursorHeight()

        checkItemRadius()

        setMaxLength(mOtpItemCount)
        mPaint.strokeWidth = mLineWidth.toFloat()
        setupAnimator()

        super.setCursorVisible(true)
        mCursorColor =  Color.parseColor("#007aff")
        cursorColor =  Color.parseColor("#007aff")
        setTextIsSelectable(false)

    }

    private fun setMaxLength(maxLength: Int) {
        filters = if (maxLength >= 0) {
            arrayOf<InputFilter>(InputFilter.LengthFilter(maxLength))
        } else {
            NO_FILTERS
        }
    }

    private fun setupAnimator() {
        mDefaultAddAnimator = ValueAnimator.ofFloat(0.5f, 1f)
        mDefaultAddAnimator!!.duration = 150
        mDefaultAddAnimator!!.interpolator = DecelerateInterpolator()
        mDefaultAddAnimator!!.addUpdateListener { animation ->
            val scale = animation.animatedValue as Float
            val alpha = (255 * scale).toInt()
            mAnimatorTextPaint.textSize = textSize * scale
            mAnimatorTextPaint.alpha = alpha
            postInvalidate()
        }
    }

    private fun checkItemRadius() {
        if (mViewType == VIEW_TYPE_LINE) {
            val halfOfLineWidth = mLineWidth.toFloat() / 2
            if (mOtpItemRadius > halfOfLineWidth) {
                throw IllegalArgumentException("The itemRadius can not be greater than lineWidth when viewType is line")
            }
        }
        val halfOfItemWidth = mOtpItemWidth.toFloat() / 2
        if (mOtpItemRadius > halfOfItemWidth) {
            throw IllegalArgumentException("The itemRadius can not be greater than itemWidth")
        }
    }

    @SuppressLint("RestrictedApi")
    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {

        val widthMode = View.MeasureSpec.getMode(widthMeasureSpec)
        val heightMode = View.MeasureSpec.getMode(heightMeasureSpec)
        val widthSize = View.MeasureSpec.getSize(widthMeasureSpec)
        val heightSize = View.MeasureSpec.getSize(heightMeasureSpec)
        var width: Int
        val height: Int
        val boxHeight = mOtpItemHeight
        if (widthMode == View.MeasureSpec.EXACTLY) {
            // Parent has told us how big to be. So be it.
            width = widthSize
        } else {
            val boxesWidth = (mOtpItemCount - 1) * mOtpItemSpacing + mOtpItemCount * mOtpItemWidth
            width = boxesWidth + ViewCompat.getPaddingEnd(this) + ViewCompat.getPaddingStart(this)
            if (mOtpItemSpacing == 0) {
                width -= (mOtpItemCount - 1) * mLineWidth
            }
            if (mOtpItemCount /2 == 0)
                for (i in 0 until mOtpItemCount ){
                    if(i ==( (mOtpItemCount/2) -1 )) {
                        mOtpItemSpacing = (lineSpacingExtra + 4).toInt()
                    }
                }

        }

        height = if (heightMode == View.MeasureSpec.UNSPECIFIED) {
            // Parent has told us how big to be. So be it.
            heightSize
        } else {
            boxHeight + paddingTop + paddingBottom
        }

        setMeasuredDimension(width, height)
//        when {
//            isFocused && text?.length != mOtpItemCount  -> setMeasuredDimension(width, height )
//
//            text?.length == mOtpItemCount -> {
//                setMeasuredDimension(width, height)
//            }
//            else -> {
//                setMeasuredDimension(width, height)
//            }
//        }

        /////// check if mOtpItemCount set action for change button to valid

    }


    private fun hideKeyboard() {
        val imm: InputMethodManager =
            context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(windowToken, 0)
    }


    override fun onTextChanged(
        text: CharSequence,
        start: Int,
        lengthBefore: Int,
        lengthAfter: Int
    ) {
        if (start != text.length) {
            moveSelectionToEnd()
        }

        makeBlink()

        if (isAnimationEnable) {
            val isAdd = lengthAfter - lengthBefore > 0
            if (isAdd) {
                if (mDefaultAddAnimator != null) {
                    mDefaultAddAnimator!!.end()
                    mDefaultAddAnimator!!.start()
                }
            }
        }
    }

    override fun onFocusChanged(focused: Boolean, direction: Int, previouslyFocusedRect: Rect?) {
        super.onFocusChanged(focused, direction, previouslyFocusedRect)

        if (focused) {
            moveSelectionToEnd()
            makeBlink()
        } else {
//            hideKeyboard()
        }
    }

    override fun onSelectionChanged(selStart: Int, selEnd: Int) {
        super.onSelectionChanged(selStart, selEnd)
        if (selEnd != text?.length) {
            moveSelectionToEnd()
        }
    }

    private fun moveSelectionToEnd() {
        text?.length?.let { setSelection(it) }
    }

    override fun drawableStateChanged() {
        super.drawableStateChanged()

        if (lineColors == null || lineColors!!.isStateful) {
            updateColors()
        }
    }

    override fun onDraw(canvas: Canvas) {
        canvas.save()

        updatePaints()
        drawPinView(canvas)

        canvas.restore()
    }

    private fun updatePaints() {
        mPaint.color = inactiveLineColor
        mPaint.style = Paint.Style.STROKE
        mPaint.strokeWidth = mLineWidth.toFloat()
        mTextPaint.color = currentTextColor
    }

    private fun drawPinView(canvas: Canvas) {

        if (mOtpItemCount /2 == 0)
            for (i in 0 until mOtpItemCount ){
                if(i ==( (mOtpItemCount/2) -1 )) {
                    mOtpItemSpacing = (lineSpacingExtra + 4).toInt()

                }
            }


        for (i in 0 until mOtpItemCount) {

            updateItemRectF(i)
            updateCenterPoint()

            if (mViewType == VIEW_TYPE_RECTANGLE) {
                drawPinBox(canvas, i)
            } else {
                drawPinLine(canvas, i)
            }

            if (DBG) {
                drawAnchorLine(canvas)
            }

            if (text?.length!! > i) {
                if (isPasswordInputType(
                        inputType
                    )
                ) {
                    drawCircle(canvas, i)
                } else {
                    drawText(canvas, i)
                }
            } else if (!TextUtils.isEmpty(hint) && hint.length == mOtpItemCount) {
                drawHint(canvas, i)
            }
        }

        // highlight the next item
        if (isFocused && text?.length != mOtpItemCount) {
            val index = text?.length
            if (index != null) {
                updateItemRectF(index)
            }
            updateCenterPoint()

           // mPaint.color = getLineColorForState(android.R.attr.state_selected)
            mPaint.color =currentLineColor

            drawCursor(canvas)

            if (mViewType == VIEW_TYPE_RECTANGLE) {
                if (index != null) {
                    drawPinBox(canvas, index)
                }
            } else {
                if (index != null) {
                    drawPinLine(canvas, index)
                }
            }
        }
    }

    private fun getLineColorForState(vararg states: Int): Int {
        return if (lineColors != null) lineColors!!.getColorForState(
            states,
            currentLineColor
        ) else currentLineColor
    }

    private fun drawPinBox(canvas: Canvas, i: Int) {
        var drawRightCorner = false
        var drawLeftCorner = false
        if (mOtpItemSpacing != 0) {
            drawRightCorner = true
            drawLeftCorner = drawRightCorner
        } else {
            if (i == 0 && i != mOtpItemCount - 1) {
                drawLeftCorner = true
            }
            if (i == mOtpItemCount - 1 && i != 0) {
                drawRightCorner = true
            }
        }
        updateRoundRectPath(
            mItemBorderRect,
            mOtpItemRadius.toFloat(),
            mOtpItemRadius.toFloat(),
            drawLeftCorner,
            drawRightCorner
        )
        canvas.drawPath(mPath, mPaint)
    }

    private fun drawPinLine(canvas: Canvas, i: Int) {
        var l: Boolean
        var r: Boolean
        r = true
        l = r
        if (mOtpItemSpacing == 0 && mOtpItemCount > 1) {
            when (i) {
                0 -> {
                    // draw only left round
                    r = false
                }
                mOtpItemCount - 1 -> {
                    // draw only right round
                    l = false
                }
                2 -> {
                    r = true
                }
                else -> {
                    // draw rect
                    r = false
                    l = r
                }
            }
        }
        mPaint.style = Paint.Style.FILL
        mPaint.strokeWidth = mLineWidth.toFloat() / 10
        val halfLineWidth = mLineWidth.toFloat() / 2
        mItemLineRect.set(
            mItemBorderRect.left,
            mItemBorderRect.bottom - halfLineWidth,
            mItemBorderRect.right,
            mItemBorderRect.bottom + halfLineWidth
        )

        updateRoundRectPath(mItemLineRect, mOtpItemRadius.toFloat(), mOtpItemRadius.toFloat(), l, r)
        canvas.drawPath(mPath, mPaint)
    }

    private fun drawCursor(canvas: Canvas) {
        if (drawCursor) {
            val cx = mItemCenterPoint.x
            val cy = mItemCenterPoint.y
            val y = cy - mCursorHeight / 2

            val color = mPaint.color
            val width = mPaint.strokeWidth
            mPaint.color = mCursorColor
            mPaint.strokeWidth = mCursorWidth.toFloat()

            canvas.drawLine(cx, y, cx, y + mCursorHeight, mPaint)

            mPaint.color = color
            mPaint.strokeWidth = width
        }
    }

    private fun updateRoundRectPath(rectF: RectF, rx: Float, ry: Float, l: Boolean, r: Boolean) {
        updateRoundRectPath(rectF, rx, ry, l, r, r, l)
    }

    private fun updateRoundRectPath(
        rectF: RectF, rx: Float, ry: Float,
        tl: Boolean, tr: Boolean, br: Boolean, bl: Boolean
    ) {
        mPath.reset()

        val l = rectF.left
        val t = rectF.top
        val r = rectF.right
        val b = rectF.bottom

        val w = r - l
        val h = b - t

        val lw = w - 2 * rx// line width
        val lh = h - 2 * ry// line height

        mPath.moveTo(l, t + ry)

        if (tl) {
            mPath.rQuadTo(0f, -ry, rx, -ry)// top-left corner
        } else {
            mPath.rLineTo(0f, -ry)
            mPath.rLineTo(rx, 0f)
        }

        mPath.rLineTo(lw, 0f)

        if (tr) {
            mPath.rQuadTo(rx, 0f, rx, ry)// top-right corner
        } else {
            mPath.rLineTo(rx, 0f)
            mPath.rLineTo(0f, ry)
        }

        mPath.rLineTo(0f, lh)

        if (br) {
            mPath.rQuadTo(0f, ry, -rx, ry)// bottom-right corner
        } else {
            mPath.rLineTo(0f, ry)
            mPath.rLineTo(-rx, 0f)
        }

        mPath.rLineTo(-lw, 0f)

        if (bl) {
            mPath.rQuadTo(-rx, 0f, -rx, -ry)// bottom-left corner
        } else {
            mPath.rLineTo(-rx, 0f)
            mPath.rLineTo(0f, -ry)
        }

        mPath.rLineTo(0f, -lh)

        mPath.close()
    }

    private fun updateItemRectF(i: Int) {
        val halfLineWidth = mLineWidth.toFloat() / 2
        var left = scrollX.toFloat() + ViewCompat.getPaddingStart(this)
            .toFloat() + (i * (mOtpItemSpacing + mOtpItemWidth)).toFloat() + halfLineWidth
        if (mOtpItemSpacing == 0 && i > 0) {
            left -= mLineWidth * i
        }
        val right = left + mOtpItemWidth - mLineWidth
        val top = scrollY.toFloat() + paddingTop.toFloat() + halfLineWidth
        val bottom = top + mOtpItemHeight - mLineWidth

        mItemBorderRect.set(left, top, right, bottom)
    }

    private fun drawText(canvas: Canvas, i: Int) {
        val paint = getPaintByIndex(i)
        // 1, Rect(4, -39, 20, 0)
        // 您, Rect(2, -47, 51, 3)
        // *, Rect(0, -39, 23, -16)
        // =, Rect(4, -26, 26, -10)
        // -, Rect(1, -19, 14, -14)
        // +, Rect(2, -32, 29, -3)
        text?.let { drawTextAtBox(canvas, paint, it, i) }
    }

    private fun drawHint(canvas: Canvas, i: Int) {
        val paint = getPaintByIndex(i)
        paint.color = currentHintTextColor
        drawTextAtBox(canvas, paint, hint, i)
    }

    private fun drawTextAtBox(canvas: Canvas, paint: Paint, text: CharSequence, charAt: Int) {
        paint.getTextBounds(text.toString(), charAt, charAt + 1, mTextRect)
        val cx = mItemCenterPoint.x
        val cy = mItemCenterPoint.y
        val x = cx - Math.abs(mTextRect.width().toFloat()) / 2 - mTextRect.left.toFloat()
        val y = cy + Math.abs(
            mTextRect.height().toFloat()
        ) / 2 - mTextRect.bottom// always center vertical
        canvas.drawText(text, charAt, charAt + 1, x, y, paint)
    }

    private fun drawCircle(canvas: Canvas, i: Int) {
        val paint = getPaintByIndex(i)
        val cx = mItemCenterPoint.x
        val cy = mItemCenterPoint.y
        canvas.drawCircle(cx, cy, paint.textSize / 2, paint)
    }

    private fun getPaintByIndex(i: Int): Paint {
        return if (isAnimationEnable && i == text?.length?.minus(1) ?: 1) {
            mAnimatorTextPaint.color = mTextPaint.color
            mAnimatorTextPaint
        } else {
            mTextPaint
        }
    }

    /**
     * For seeing the font position
     */
    private fun drawAnchorLine(canvas: Canvas) {
        var cx = mItemCenterPoint.x
        var cy = mItemCenterPoint.y
        mPaint.strokeWidth = 1f
        cx -= mPaint.strokeWidth / 2
        cy -= mPaint.strokeWidth / 2

        mPath.reset()
        mPath.moveTo(cx, mItemBorderRect.top)
        mPath.lineTo(cx, mItemBorderRect.top + Math.abs(mItemBorderRect.height()))
        canvas.drawPath(mPath, mPaint)

        mPath.reset()
        mPath.moveTo(mItemBorderRect.left, cy)
        mPath.lineTo(mItemBorderRect.left + Math.abs(mItemBorderRect.width()), cy)
        canvas.drawPath(mPath, mPaint)

        mPath.reset()

        mPaint.strokeWidth = mLineWidth.toFloat()
    }

    private fun updateColors() {
        var inval = false

        val color: Int
        color = if (lineColors != null) {
            lineColors!!.getColorForState(drawableState, 0)
        } else {
            currentTextColor
        }

//        if (color != currentLineColor) {
//            currentLineColor = color
//            inval = true
//        }

        if (inval) {
            invalidate()
        }
    }

    private fun updateCenterPoint() {
        val cx = mItemBorderRect.left + Math.abs(mItemBorderRect.width()) / 2
        val cy = mItemBorderRect.top + Math.abs(mItemBorderRect.height()) / 2
        mItemCenterPoint.set(cx, cy)
    }

    override fun getDefaultMovementMethod(): MovementMethod {
        return InitialMovementMethod.getInstance()
    }

    /**
     * Sets the line color for all the states (normal, selected,
     * focused) to be this color.
     *
     * @param color A color value in the form 0xAARRGGBB.
     * Do not pass a resource ID. To get a color value from a resource ID, call
     * [getColor][android.support.v4.content.ContextCompat.getColor].
     * @attr ref R.styleable#PinView_lineColor
     * @see .setLineColor
     * @see .getLineColors
     */
    fun setLineColorInactive(@ColorInt color: Int) {
        lineColors = ColorStateList.valueOf(color)
       // updateColors()
        inactiveLineColor =color
    }
    fun setLineColorActive(@ColorInt color: Int) {
        lineColors = ColorStateList.valueOf(color)
        // updateColors()
        currentLineColor =color
    }
    /**
     * Sets the line color.
     *
     * @attr ref R.styleable#PinView_lineColor
     * @see .setLineColor
     * @see .getLineColors
     */
    fun setLineColor(colors: ColorStateList?) {
        if (colors == null) {
            throw NullPointerException()
        }

        lineColors = colors
        updateColors()
    }

    /**
     * Specifies whether the text animation should be enabled or disabled.
     * By the default, the animation is disabled.
     *
     * @param enable True to start animation when adding text, false to transition immediately
     */
    fun setAnimationEnable(enable: Boolean) {
        isAnimationEnable = enable
    }

    override fun setTextSize(size: Float) {
        super.setTextSize(size)
        updateCursorHeight()
    }

    override fun setTextSize(unit: Int, size: Float) {
        super.setTextSize(unit, size)
        updateCursorHeight()
    }

    override fun setCursorVisible(visible: Boolean) {
        if (isCursorVisible != visible) {
            isCursorVisible = visible
            invalidateCursor(isCursorVisible)
            makeBlink()
        }
    }

    override fun isCursorVisible(): Boolean {
        return isCursorVisible
    }

    override fun onScreenStateChanged(screenState: Int) {
        super.onScreenStateChanged(screenState)
        when (screenState) {
            View.SCREEN_STATE_ON -> resumeBlink()
            View.SCREEN_STATE_OFF -> suspendBlink()
        }
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        resumeBlink()
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        suspendBlink()
    }

    private fun shouldBlink(): Boolean {
        return isCursorVisible() && isFocused
    }

    private fun makeBlink() {
        if (shouldBlink()) {
            if (mBlink == null) {
                mBlink = Blink()
            }
            removeCallbacks(mBlink)
            drawCursor = false
            postDelayed(mBlink, BLINK.toLong())
        } else {
            if (mBlink != null) {
                removeCallbacks(mBlink)
            }
        }
    }

    private fun suspendBlink() {
        if (mBlink != null) {
            mBlink!!.cancel()
            invalidateCursor(false)
        }
    }

    private fun resumeBlink() {
        if (mBlink != null) {
            mBlink!!.uncancel()
            makeBlink()
        }
    }

    private fun invalidateCursor(showCursor: Boolean) {
        if (drawCursor != showCursor) {
            drawCursor = showCursor
            invalidate()
        }
    }

    private fun updateCursorHeight() {
        val delta = 2 * dpToPx(2f)
        mCursorHeight = if (mOtpItemHeight - textSize > delta) textSize + delta else textSize
    }

    private inner class Blink : Runnable {
        private var mCancelled: Boolean = false

        override fun run() {
            if (mCancelled) {
                return
            }

            removeCallbacks(this)

            if (shouldBlink()) {
                invalidateCursor(!drawCursor)
                postDelayed(this, BLINK.toLong())
            }
        }

        fun cancel() {
            if (!mCancelled) {
                removeCallbacks(this)
                mCancelled = true
            }
        }

        internal fun uncancel() {
            mCancelled = false
        }
    }
    //endregion

    private fun dpToPx(dp: Float): Int {
        return (dp * resources.displayMetrics.density + 0.5f).toInt()
    }


    companion object {

        private val TAG = "CustomOtpView"

        private val DBG = false

        private val BLINK = 1000

        private val DEFAULT_COUNT = 4

        private val NO_FILTERS = arrayOfNulls<InputFilter>(0)

        private val VIEW_TYPE_RECTANGLE = 0
        private val VIEW_TYPE_LINE = 1

        private fun isPasswordInputType(inputType: Int): Boolean {
            val variation =
                inputType and (EditorInfo.TYPE_MASK_CLASS or EditorInfo.TYPE_MASK_VARIATION)
            return (variation == EditorInfo.TYPE_CLASS_TEXT or EditorInfo.TYPE_TEXT_VARIATION_PASSWORD
                    || variation == EditorInfo.TYPE_CLASS_TEXT or EditorInfo.TYPE_TEXT_VARIATION_WEB_PASSWORD
                    || variation == EditorInfo.TYPE_CLASS_NUMBER or EditorInfo.TYPE_NUMBER_VARIATION_PASSWORD)
        }
    }
}