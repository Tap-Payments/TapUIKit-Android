package company.tap.tapuilibrary.views

import android.content.Context
import android.util.AttributeSet
import android.widget.LinearLayout
import company.tap.tapuilibrary.R

/**
 *
 * Created by Mario Gamal on 6/17/20
 * Copyright © 2020 Tap Payments. All rights reserved.
 *
 */
class TapSelectionTabLayout (context: Context?, attrs: AttributeSet?) : LinearLayout(context, attrs) {
    init {
        inflate(
            context,
            R.layout.tap_selection_tablayout, this
        )
    }
}