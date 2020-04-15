package tapuilibrarykotlin

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatToggleButton

/**
 * Created by AhlaamK on 4/15/20.

Copyright (c) 2020    Tap Payments.
All rights reserved.
 **/
open class TapToggle : AppCompatToggleButton {
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)
}