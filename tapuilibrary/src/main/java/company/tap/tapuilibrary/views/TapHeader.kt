package company.tap.tapuilibrary.views

import android.content.Context
import android.util.AttributeSet
import android.widget.LinearLayout
import company.tap.tapuilibrary.R
import company.tap.tapuilibrary.atoms.TapImageView
import company.tap.tapuilibrary.atoms.TapTextView

/**
 * Created by AhlaamK on 4/29/20.

Copyright (c) 2020    Tap Payments.
All rights reserved.
 **/

/**
 * TapHeader is a molecule for setting Business Name and Business Logo for Merchant
 * **/
class TapHeader(context: Context?, attrs: AttributeSet?) : LinearLayout(context, attrs) {
    /**
     * @param businessIcon is for setting the business Logo
     * */
    private var businessIcon: TapImageView
    private var businessName: TapTextView
    private var paymentFor: TapTextView

    init {
        inflate(context, R.layout.tap_main_header, this)
        businessIcon = findViewById(R.id.business_icon)
        businessName = findViewById(R.id.business_name)
        paymentFor = findViewById(R.id.payment_for)
    }

}