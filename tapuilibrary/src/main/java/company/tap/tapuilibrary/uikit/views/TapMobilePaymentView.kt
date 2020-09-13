package company.tap.tapuilibrary.uikit.views

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.util.AttributeSet
import android.widget.EditText
import android.widget.LinearLayout
import androidx.core.view.size
import company.tap.tapuilibrary.R
import company.tap.tapuilibrary.themekit.theme.EditTextTheme
import company.tap.tapuilibrary.uikit.atoms.TapTextView
import company.tap.tapuilibrary.uikit.interfaces.TapView

/**
 *
 * Created on 7/1/20
 * Copyright © 2020 Tap Payments. All rights reserved.
 *
 */
class TapMobilePaymentView(context: Context?, attrs: AttributeSet?) :
    LinearLayout(context, attrs),
    TapView<EditTextTheme> {
    val mobileNumber by lazy { findViewById<EditText>(R.id.mobileNumber) }


    init {
        inflate(context, R.layout.tap_mobile_payment_view, this)
        mobileNumber.requestFocus()
    }

    fun clearNumber() {
        mobileNumber.text = null
    }

    override fun setTheme(theme: EditTextTheme) {
//            theme.backgroundTint?.let { backgroundTintList = ColorStateList.valueOf(it) }
        theme.backgroundTint?.let { mobileNumber.setBackgroundColor(it) }
        theme.textColorHint?.let { mobileNumber.setHintTextColor(it) }
        theme.letterSpacing?.let { mobileNumber.letterSpacing = it.toFloat() }
        theme.textSize?.let { mobileNumber.textSize = it.toFloat() }
    }
}
