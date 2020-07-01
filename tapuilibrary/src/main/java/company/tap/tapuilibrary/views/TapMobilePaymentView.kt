package company.tap.tapuilibrary.views

import android.content.Context
import android.util.AttributeSet
import android.widget.LinearLayout
import company.tap.tapuilibrary.R

/**
 *
 * Created by Mario Gamal on 7/1/20
 * Copyright © 2020 Tap Payments. All rights reserved.
 *
 */
class TapMobilePaymentView(context: Context?, attrs: AttributeSet?) :
    LinearLayout(context, attrs) {

    init {
        inflate(context, R.layout.tap_mobile_payment_view, this)
    }
}