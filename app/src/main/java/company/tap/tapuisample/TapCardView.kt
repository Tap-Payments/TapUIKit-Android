package company.tap.tapuisample

import android.R.string
import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.View
import company.tap.tapuilibrary.TapChip
import java.io.IOException


/**
 * Created by AhlaamK on 4/28/20.

Copyright (c) 2020    Tap Payments.
All rights reserved.
 **/


open class TapCardView(context: Context,attributeSet: AttributeSet) : TapChip(context,attributeSet) {

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        view = View.inflate(context,R.layout.view_custom_tapcard,this)

    }

}









