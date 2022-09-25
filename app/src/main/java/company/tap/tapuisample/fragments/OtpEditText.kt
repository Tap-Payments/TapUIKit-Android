//package company.tap.tapuisample.fragments
//
//import android.R
//import android.content.Context
//import android.graphics.Canvas
//import android.graphics.Paint
//import android.opengl.ETC1.getHeight
//import android.opengl.ETC1.getWidth
//import android.text.Editable
//import android.util.AttributeSet
//import android.view.ActionMode
//import android.view.View
//import androidx.appcompat.widget.AppCompatEditText
//
//
//class OtpEditText : AppCompatEditText {
//    private var mSpace = 24f //24 dp by default, space between the lines
//    private var mNumChars = 4f
//    private var mLineSpacing = 8f //8dp by default, height of the text from our lines
//    private val mMaxLength = 4
//    private var mLineStroke = 2f
//    private var mLinesPaint: Paint? = null
//    private var mClickListener: OnClickListener? = null
//
//    constructor(context: Context?) : super(context) {}
//    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
//        init(context, attrs)
//    }
//
//    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(
//        context,
//        attrs,
//        defStyleAttr
//    ) {
//        init(context, attrs)
//    }
//
//    private fun init(context: Context, attrs: AttributeSet) {
//        val multi = context.resources.displayMetrics.density
//        mLineStroke = multi * mLineStroke
//        mLinesPaint = Paint(getPaint())
//        mLinesPaint.setStrokeWidth(mLineStroke)
//        mLinesPaint.setColor(getResources().getColor(R.color.colorPrimaryDark))
//        setBackgroundResource(0)
//        mSpace = multi * mSpace //convert to pixels for our density
//        mLineSpacing = multi * mLineSpacing //convert to pixels for our density
//        mNumChars = mMaxLength.toFloat()
//        super.setOnClickListener(object : OnClickListener() {
//            fun onClick(v: View?) {
//                // When tapped, move cursor to end of text.
//                setSelection(getText().length())
//                if (mClickListener != null) {
//                    mClickListener.onClick(v)
//                }
//            }
//        })
//    }
//
//    fun setOnClickListener(l: OnClickListener?) {
//        mClickListener = l
//    }
//
//    fun setCustomSelectionActionModeCallback(actionModeCallback: ActionMode.Callback?) {
//        throw RuntimeException("setCustomSelectionActionModeCallback() not supported.")
//    }
//
//    protected fun onDraw(canvas: Canvas) {
//        val availableWidth: Int = getWidth() - getPaddingRight() - getPaddingLeft()
//        val mCharSize: Float
//        mCharSize = if (mSpace < 0) {
//            availableWidth / (mNumChars * 2 - 1)
//        } else {
//            (availableWidth - mSpace * (mNumChars - 1)) / mNumChars
//        }
//        var startX: Int = getPaddingLeft()
//        val bottom: Int = getHeight() - getPaddingBottom()
//
//        //Text Width
//        val text: Editable = getText()
//        val textLength = text.length
//        val textWidths = FloatArray(textLength)
//        getPaint().getTextWidths(getText(), 0, textLength, textWidths)
//        var i = 0
//        while (i < mNumChars) {
//            canvas.drawLine(startX, bottom, startX + mCharSize, bottom, mLinesPaint)
//            if (getText().length() > i) {
//                val middle = startX + mCharSize / 2
//                canvas.drawText(
//                    text,
//                    i,
//                    i + 1,
//                    middle - textWidths[0] / 2,
//                    bottom - mLineSpacing,
//                    getPaint()
//                )
//            }
//            if (mSpace < 0) {
//                (startX += mCharSize * 2).toInt()
//            } else {
//                (startX += mCharSize + mSpace.toInt()).toInt()
//            }
//            i++
//        }
//    }
//}