package company.tap.tapuilibrary.uikit.views

import android.content.Context
import android.content.res.ColorStateList
import android.util.AttributeSet
import android.widget.EditText
import android.widget.LinearLayout
import company.tap.tapuilibrary.R
import company.tap.tapuilibrary.themekit.theme.EditTextTheme
import company.tap.tapuilibrary.uikit.interfaces.TapView

/**
 *
 * Created by Mario Gamal on 7/1/20
 * Copyright © 2020 Tap Payments. All rights reserved.
 *
 */
class TapMobilePaymentView(context: Context?, attrs: AttributeSet?) :
    LinearLayout(context, attrs),
    TapView<EditTextTheme> {

    val mobileInputEditText: EditText

    init {
        inflate(context, R.layout.tap_mobile_payment_view, this)
        mobileInputEditText = findViewById(R.id.mobile_number)
        mobileInputEditText.requestFocus()
    }

    fun clearNumber() {
        mobileInputEditText.text = null
    }

    override fun setTheme(theme: EditTextTheme) {
            theme.backgroundTint?.let { backgroundTintList= ColorStateList.valueOf(it) }
            theme.textColorHint?.let { }
            theme.textSize?.let{}
        

    }
}