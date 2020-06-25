package company.tap.tapuilibrary.views

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.view.Gravity
import android.widget.LinearLayout
import android.widget.TextView
import androidx.annotation.ColorInt
import company.tap.tapuilibrary.R
import company.tap.tapuilibrary.atoms.TapImageView
import company.tap.tapuilibrary.atoms.TapTextView
import company.tap.tapuilibrary.datasource.HeaderDataSource

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
    private var textColor = Color.parseColor(TEXT_COLOR)
//    private var textSize = Color.parseColor(TEXT_SIZE)
    private var dataSource: HeaderDataSource? = null
    init {
        inflate(context, R.layout.tap_main_header, this)
        businessIcon = findViewById(R.id.business_icon)
        businessName = findViewById(R.id.business_name)
        paymentFor = findViewById(R.id.payment_for)
    }

    /**
     * Setter for the text color
     *
     * @param color integer color value
     */
    fun setBusinessNameColor(@ColorInt color: Int) {
        textColor = color
        businessName.setTextColor(color)
    }

    /**
     * class for holding the constant values used in the tapheader
     */
    companion object {
        const val TEXT_SIZE = "16sp"
        var TEXT_COLOR = "#4b4847"

    }
    fun setHeaderDataSource(dataSource: HeaderDataSource) {
        this.dataSource = dataSource
        addView(getTextView())
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
}