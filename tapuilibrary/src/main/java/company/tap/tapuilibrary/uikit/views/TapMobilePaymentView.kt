package company.tap.tapuilibrary.uikit.views

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.View
import android.widget.EditText
import android.widget.LinearLayout
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.size
import androidx.core.widget.doAfterTextChanged
import com.bumptech.glide.Glide
import company.tap.tapuilibrary.R
import company.tap.tapuilibrary.themekit.ThemeManager
import company.tap.tapuilibrary.themekit.theme.EditTextTheme
import company.tap.tapuilibrary.uikit.atoms.TapImageView
import company.tap.tapuilibrary.uikit.atoms.TapTextView
import company.tap.tapuilibrary.uikit.interfaces.GoPayLoginInterface
import company.tap.tapuilibrary.uikit.interfaces.ShowPickerInterface
import company.tap.tapuilibrary.uikit.interfaces.TapPaymentShowHideClearImage
import company.tap.tapuilibrary.uikit.interfaces.TapView

/**
 *
 * Copyright © 2020 Tap Payments. All rights reserved.
 *
 */
class TapMobilePaymentView(context: Context?, attrs: AttributeSet?) :
    LinearLayout(context, attrs),
    TapView<EditTextTheme> {
    val mobileNumber by lazy { findViewById<EditText>(R.id.mobileNumber) }
    val mobileImage by lazy { findViewById<TapImageView>(R.id.mobileImage) }
    val mobilePaymentMainLinear by lazy { findViewById<LinearLayout>(R.id.mobilePaymentMainLinear) }
    val countryCodeText by lazy { findViewById<TapTextView>(R.id.countryCodeText) }
    private var tapPaymentShowHideClearImage: TapPaymentShowHideClearImage? = null
    private var showPickerInterface: ShowPickerInterface? = null

    init {
        inflate(context, R.layout.tap_mobile_payment_view, this)
        mobileNumber.requestFocus()
        initTheme()
        initCountryCodePicker()
    }


    private fun initCountryCodePicker() {
        mobileImage.setOnClickListener {
            mobileImage.visibility = View.GONE
            countryCodeText.visibility = View.VISIBLE
            countryCodeText.text = showPickerInterface?.showPicker() }

        countryCodeText.setOnClickListener {
            mobileImage.visibility = View.GONE
            countryCodeText.visibility = View.VISIBLE
            countryCodeText.text = showPickerInterface?.showPicker() }
    }

    fun initShowPickerInterface(showPickerInterface: ShowPickerInterface) {
        this.showPickerInterface = showPickerInterface
    }


    fun setTapPaymentShowHideClearImage(tapPaymentShowHideClearImage: TapPaymentShowHideClearImage) {
        this.tapPaymentShowHideClearImage = tapPaymentShowHideClearImage
    }

    fun clearNumber() {
        mobileNumber.text = null
    }

    fun initTheme() {
        mobileImage.setBackgroundColor(Color.parseColor(ThemeManager.getValue("phoneCard.commonAttributes.backgroundColor")))
        mobilePaymentMainLinear.setBackgroundColor(Color.parseColor(ThemeManager.getValue("phoneCard.commonAttributes.backgroundColor")))
        mobileNumber.setHintTextColor(Color.parseColor(ThemeManager.getValue("phoneCard.textFields.placeHolderColor")))
        mobileNumber.textSize = ThemeManager.getFontSize("phoneCard.textFields.font").toFloat()
        mobileNumber.setTextColor(Color.parseColor(ThemeManager.getValue("phoneCard.textFields.textColor")))
    }

    override fun setTheme(theme: EditTextTheme) {
//            theme.backgroundTint?.let { backgroundTintList = ColorStateList.valueOf(it) }
        theme.backgroundTint?.let { mobileNumber.setBackgroundColor(it) }
        theme.textColorHint?.let { mobileNumber.setHintTextColor(it) }
        theme.letterSpacing?.let { mobileNumber.letterSpacing = it.toFloat() }
        theme.textSize?.let { mobileNumber.textSize = it.toFloat() }
    }

}



