package tapuilibrarykotlin

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatEditText


/**
 * Created by AhlaamK on 4/14/20.

Copyright (c) 2020    Tap Payments.
All rights reserved.
 **/
class TapEditText : AppCompatEditText {
    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    override fun setError(error: CharSequence, icon: Drawable) {
        setCompoundDrawables(null, null, icon, null)
    }

}