package company.tap.tapuilibrary.organisms

import android.content.Context
import android.util.AttributeSet
import android.widget.LinearLayout
import company.tap.tapuilibrary.R

/**
 *
 * Created by Mario Gamal on 7/14/20
 * Copyright © 2020 Tap Payments. All rights reserved.
 *
 */
class GoPayLoginInput(context: Context?, attrs: AttributeSet?) :
    LinearLayout(context, attrs)  {
    init {
        inflate(context, R.layout.gopay_login_input, this)
    }
}