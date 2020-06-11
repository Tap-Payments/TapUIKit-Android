package company.tap.tapuilibrary.views

import android.content.Context
import android.util.AttributeSet
import android.widget.LinearLayout
import company.tap.tapuilibrary.R


/**
 * Created by AhlaamK on 6/11/20.

Copyright (c) 2020    Tap Payments.
All rights reserved.
 **/
class TapAmountSectionView (context: Context?, attrs: AttributeSet?) : LinearLayout(context, attrs) {
    init {
        inflate(
            context,
            R.layout.tap_main_amount, this
        )
    }
}