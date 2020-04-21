package tapuilibrarykotlin

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import androidx.annotation.ColorInt
import androidx.annotation.Size
import androidx.appcompat.widget.AppCompatEditText
import company.tap.tapuilibrary.R


/**
 * Created by AhlaamK on 4/14/20.

Copyright (c) 2020    Tap Payments.
All rights reserved.
 **/
open class TapEditText : AppCompatEditText {
    @ColorInt
    private var inputColorInt = 0
    @Size
    private var inputTextSize = 0f
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs){init(attrs)}
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr){init(attrs)}

    override fun setError(error: CharSequence, icon: Drawable) {
       // setCompoundDrawables(null, null, icon, null)
        if (error.isNullOrEmpty()) {
            super.setError(null, icon)
            setCompoundDrawables(null, null, null, null)
        }
        else if (error.toString() == "") {
            setCompoundDrawables(null, null, icon, null)
        } else {
            super.setError(error, icon)
        }
    }
    /**
     * This init will initilaize the attributes that will be customizable through XML.
     * InputType is set to string by default.
     * Color is customizable.
     * TextSize is Customizable.
     */
    private fun init(attrs: AttributeSet?) {
        if (attrs != null) {
            val styledAttributes = context.theme.obtainStyledAttributes(
                attrs,
                R.styleable.TapEditText,
                0, 0
            )
            try {
                inputColorInt = styledAttributes.getColor(R.styleable.TapEditText_color, textColors.defaultColor)
                inputTextSize = styledAttributes.getDimension(R.styleable.TapEditText_fontSize, textSize)
            } finally {
                styledAttributes.recycle()
            }
        }
    }

}