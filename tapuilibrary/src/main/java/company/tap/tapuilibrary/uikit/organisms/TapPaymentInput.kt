package company.tap.tapuilibrary.uikit.organisms

import android.content.Context
import android.util.AttributeSet
import android.widget.ImageView
import android.widget.LinearLayout
import company.tap.tapuilibrary.R
import company.tap.tapuilibrary.themekit.theme.TabSelectTheme
import company.tap.tapuilibrary.uikit.interfaces.TapView
import company.tap.tapuilibrary.uikit.models.TabSection
import company.tap.tapuilibrary.uikit.views.TapSelectionTabLayout

/**
 *
 * Created by Mario Gamal on 7/13/20
 * Copyright © 2020 Tap Payments. All rights reserved.
 *
 */
class TapPaymentInput(context: Context?, attrs: AttributeSet?) :
    LinearLayout(context, attrs),
    TapView<TabSelectTheme> {

    var tabLayout: TapSelectionTabLayout
    var paymentInputContainer: LinearLayout
    var clearView: ImageView

    init {
        inflate(context, R.layout.tap_payment_input, this)
        tabLayout = findViewById(R.id.sections_tablayout)
        paymentInputContainer = findViewById(R.id.payment_input_layout)
        clearView = findViewById(R.id.clear_text)
        clearView.setOnClickListener {
            rootView.invalidate()
        }
    }

    fun addTabLayoutSection(vararg sections: TabSection) {
        sections.forEach {
            tabLayout.addSection(it.items)
        }
    }
    fun clearCardNumber() {
        paymentInputContainer.clearFocus()
    }

    override fun setTheme(theme: TabSelectTheme) {
        theme.backgroundColor?.let { setBackgroundColor(it) }
        theme.selectedBackgroundColor?.let { setBackgroundColor(it) }
        theme.unselectedBackgroundColor?.let { setBackgroundColor(it) }
    }

}