package company.tap.tapuilibrary.organisms

import android.content.Context
import android.util.AttributeSet
import android.widget.LinearLayout
import company.tap.tapcardvalidator_android.CardBrand
import company.tap.tapuilibrary.R
import company.tap.tapuilibrary.models.SectionTabItem
import company.tap.tapuilibrary.models.TabSection
import company.tap.tapuilibrary.views.TapSelectionTabLayout

/**
 *
 * Created by Mario Gamal on 7/13/20
 * Copyright © 2020 Tap Payments. All rights reserved.
 *
 */
class TapPaymentInput(context: Context?, attrs: AttributeSet?) :
    LinearLayout(context, attrs) {

    private val tabLayout: TapSelectionTabLayout
    private val paymentInputContainer: LinearLayout

    init {
        inflate(context, R.layout.tap_payment_input, this)
        tabLayout = findViewById(R.id.sections_tablayout)
        paymentInputContainer = findViewById(R.id.payment_input_layout)
    }

    fun addTabLayoutSection(vararg sections: TabSection) {
         sections.forEach {
             tabLayout.addSection(it.items)
         }
    }
}