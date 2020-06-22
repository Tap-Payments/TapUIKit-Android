package company.tap.tapuilibrary.views

import android.content.Context
import android.util.AttributeSet
import android.widget.LinearLayout
import company.tap.tapuilibrary.R

/**
 * Created by AhlaamK on 6/15/20.

Copyright (c) 2020    Tap Payments.
All rights reserved.
 **/
/***
 * TapItemsView  is a molecule for setting Amount, Description and Quantity.
 */
class TapItemsView(context: Context?, attrs: AttributeSet?) : LinearLayout(context, attrs) {
    init {
        inflate(context, R.layout.tap_items_view, this)
    }
}