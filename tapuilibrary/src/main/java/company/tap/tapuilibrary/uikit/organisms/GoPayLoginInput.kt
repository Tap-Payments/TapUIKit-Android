package company.tap.tapuilibrary.uikit.organisms

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.Typeface
import android.text.Editable
import android.text.InputType
import android.text.TextWatcher
import android.util.AttributeSet
import android.util.Log
import android.util.Patterns
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.core.widget.doAfterTextChanged
import com.google.android.material.tabs.TabLayout
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.hbb20.CountryCodePicker
import company.tap.taplocalizationkit.LocalizationManager
import company.tap.tapuilibrary.R
import company.tap.tapuilibrary.fontskit.enums.TapFont
import company.tap.tapuilibrary.themekit.ThemeManager
import company.tap.tapuilibrary.themekit.theme.EditTextTheme
import company.tap.tapuilibrary.themekit.theme.SeparatorViewTheme
import company.tap.tapuilibrary.themekit.theme.TextViewTheme
import company.tap.tapuilibrary.uikit.atoms.TapSeparatorView
import company.tap.tapuilibrary.uikit.atoms.TapTextView
import company.tap.tapuilibrary.uikit.datasource.ActionButtonDataSource
import company.tap.tapuilibrary.uikit.datasource.GoPayLoginDataSource
import company.tap.tapuilibrary.uikit.enums.GoPayLoginMethod.EMAIL
import company.tap.tapuilibrary.uikit.enums.GoPayLoginMethod.PHONE
import company.tap.tapuilibrary.uikit.interfaces.GoPayLoginInterface
import company.tap.tapuilibrary.uikit.interfaces.OpenOTPInterface
import company.tap.tapuilibrary.uikit.interfaces.TapView
import company.tap.tapuilibrary.uikit.utils.FakeThemeManager
import company.tap.tapuilibrary.uikit.views.TabAnimatedActionButton

/**
 *
 * Created on 7/14/20
 * Copyright © 2020 Tap Payments. All rights reserved.
 *
 */
class GoPayLoginInput(context: Context?, attrs: AttributeSet?) :
    LinearLayout(context, attrs), CountryCodePicker.OnCountryChangeListener,
    TapView<EditTextTheme> {


    val textInput by lazy { findViewById<TextInputEditText>(R.id.gopay_text_input) }
    val loginTabLayout by lazy { findViewById<TabLayout>(R.id.login_type) }
    val textInputLayout by lazy { findViewById<TextInputLayout>(R.id.text_input_layout) }
    val loginMethodImage by lazy { findViewById<ImageView>(R.id.login_method_icon) }
    val actionButton by lazy { findViewById<TabAnimatedActionButton>(R.id.gopay_button) }
    val goPayHint by lazy { findViewById<TapTextView>(R.id.gopay_hint) }
    val countryCodePicker by lazy { findViewById<CountryCodePicker>(R.id.countryCodePicker) }
    val goPayLinear by lazy { findViewById<LinearLayout>(R.id.goPayLinear) }
    val goPayTabSeparator by lazy { findViewById<TapSeparatorView>(R.id.goPayTabSeparator) }
    val goPayTabSeparator_ by lazy { findViewById<TapSeparatorView>(R.id.goPayTabSeparator_) }
    val loginInputLayout by lazy { findViewById<LinearLayout>(R.id.login_input_layout) }
//    val countryCodeTxt by lazy { findViewById<TapTextView>(R.id.countryCodeTxt) }

    var dataSource: GoPayLoginDataSource? = null
    private var loginInterface: GoPayLoginInterface? = null
    private var openOTPInterface: OpenOTPInterface? = null
    var inputType = EMAIL
    private var countryCode: String? = null

    init {
        inflate(context, R.layout.gopay_login_input, this)
        if (context?.let { LocalizationManager.getLocale(it).language } == "en") setFontsEnglish() else setFontsArabic()
        initTheme()
        setSeparatorTheme()
    }

    private fun initTheme() {
        goPayLinear.setBackgroundColor(Color.parseColor(ThemeManager.getValue("goPay.loginBar.backgroundColor")))
        loginTabLayout.setSelectedTabIndicatorColor(Color.parseColor("#a8a8a8"))
        loginTabLayout.setBackgroundColor(Color.parseColor(ThemeManager.getValue("goPay.loginBar.backgroundColor")))
        loginTabLayout.tabTextColors =
            ColorStateList.valueOf(Color.parseColor(ThemeManager.getValue("goPay.loginBar.title.selected.textColor")))
        var textThem = TextViewTheme()
        textThem.textColor =
            Color.parseColor(ThemeManager.getValue("goPay.loginBar.hintLabel.textColor"))
        textThem.textSize = ThemeManager.getFontSize("goPay.loginBar.hintLabel.textFont")
        goPayHint.setTheme(textThem)
        goPayHint.setBackgroundColor(Color.parseColor(ThemeManager.getValue("goPay.loginBar.backgroundColor")))
        textInput.setHintTextColor(Color.parseColor(ThemeManager.getValue("phoneCard.textFields.placeHolderColor")))
        textInput.setTextColor(Color.parseColor(ThemeManager.getValue("emailCard.textFields.textColor")))
        textInput.textSize = ThemeManager.getFontSize("emailCard.textFields.font").toFloat()
        loginInputLayout.setBackgroundColor(Color.parseColor(ThemeManager.getValue("goPay.loginBar.inputFieldBackground")))
        countryCodePicker.setDialogBackgroundColor(Color.parseColor(ThemeManager.getValue("goPay.loginBar.backgroundColor")))



        countryCodePicker.textView_selectedCountry?.typeface = Typeface.createFromAsset(
            context?.assets, TapFont.tapFontType(
                TapFont.RobotoLight
            )
        )
        textInput?.typeface = Typeface.createFromAsset(
            context?.assets, TapFont.tapFontType(
                TapFont.RobotoLight
            )
        )
        goPayHint?.typeface = Typeface.createFromAsset(
            context?.assets, TapFont.tapFontType(
                TapFont.RobotoLight
            )
        )

    }

    private fun setSeparatorTheme() {
        val separatorViewTheme = SeparatorViewTheme()
        separatorViewTheme.strokeColor =
            Color.parseColor(ThemeManager.getValue("tapSeparationLine.backgroundColor"))
        separatorViewTheme.strokeHeight = ThemeManager.getValue("tapSeparationLine.height")
        goPayTabSeparator.setTheme(separatorViewTheme)
        goPayTabSeparator_.setTheme(separatorViewTheme)
    }

    private fun initCountryCodePicker() {
        countryCodePicker.setDefaultCountryUsingNameCode("KW")
        countryCode = countryCodePicker.defaultCountryCode
        countryCodePicker.ccpDialogShowFlag = false
        countryCodePicker.showArrow(false)
        countryCodePicker.contentColor = Color.parseColor(ThemeManager.getValue("phoneCard.textFields.placeHolderColor"))
        countryCodePicker.launchCountrySelectionDialog()
        countryCode = countryCodePicker.selectedCountryCode
        countryCodePicker.setDialogBackgroundColor(Color.parseColor(ThemeManager.getValue("goPay.loginBar.backgroundColor")))
    }

    fun changeDataSource(dataSource: GoPayLoginDataSource) {
        this.dataSource = dataSource
        initTabLayout()
        initTextInput()
        changeInputType()
        initButton()
    }

    fun setLoginInterface(loginInterface: GoPayLoginInterface) {
        this.loginInterface = loginInterface
    }

    fun setOpenOTPInterface(openOTPInterface: OpenOTPInterface) {
        this.openOTPInterface = openOTPInterface
    }

    private fun initButton() {
        actionButton.isEnabled = false
        actionButton.setButtonDataSource(
            false,
            context?.let { LocalizationManager.getLocale(it).language },
            LocalizationManager.getValue("next", "Common"),
            Color.parseColor(ThemeManager.getValue("actionButton.Invalid.backgroundColor")),
            Color.parseColor(ThemeManager.getValue("actionButton.Valid.titleLabelColor"))
        )

        actionButton.setOnClickListener {
            when (inputType) {
                EMAIL -> loginInterface?.onEmailValidated()
                PHONE -> loginInterface?.onPhoneValidated()
            }
        }
    }

//    private fun getButtonDataSource(color: Int): ActionButtonDataSource {
//        return ActionButtonDataSource(
//            text = "Next",
//            textSize = 16f,
//            textColor = Color.WHITE,
//            cornerRadius = 100f,
//            backgroundColor = color
//        )
//    }

    private fun initTextInput() {
        textInput.doAfterTextChanged {
            if (isValidInput(it.toString())) {
                enableNext()
                sendPhoneNumber()
            } else
                disableNext()
        }
    }

    fun sendPhoneNumber() {
        var replaced = ""
        var countryCodeReplaced = ""
        if (textInput.text.toString().length > 7)
            replaced = (textInput.text.toString()).replaceRange(1, 6, "....")

        countryCodeReplaced = (countryCodePicker.selectedCountryCode).replace("+", " ")
        countryCodeReplaced.let {
            openOTPInterface?.getPhoneNumber(
                textInput.text.toString(),
                it,
                replaced
            )
        }
    }

    private fun isValidInput(text: String): Boolean {
        return when (inputType) {
            EMAIL -> isValidEmail(text)
            PHONE -> isValidPhone(text)
        }
    }

    private fun disableNext() {
        actionButton.isEnabled = false
        actionButton.setButtonDataSource(
            false,
            context?.let { LocalizationManager.getLocale(it).language },
            LocalizationManager.getValue("next", "Common"),
            Color.parseColor(ThemeManager.getValue("actionButton.Invalid.backgroundColor")),
            Color.parseColor(ThemeManager.getValue("actionButton.Invalid.titleLabelColor"))
        )
        loginTabLayout.setSelectedTabIndicatorColor(FakeThemeManager.getGoPayUnValidatedColor())
    }

    private fun isValidEmail(email: String): Boolean {
        return Patterns.EMAIL_ADDRESS
            .matcher(email)
            .matches()
    }

    private fun isValidPhone(phone: String): Boolean {
        return phone.length > 7
    }

    private fun enableNext() {
        loginTabLayout.setSelectedTabIndicatorColor(FakeThemeManager.getGoPayValidatedColor())
        actionButton.isEnabled = true
        actionButton.setButtonDataSource(
            true,
            context?.let { LocalizationManager.getLocale(it).language },
            LocalizationManager.getValue("next", "Common"),
            Color.parseColor(ThemeManager.getValue("actionButton.Valid.goLoginBackgroundColor")),
            Color.parseColor(ThemeManager.getValue("actionButton.Valid.titleLabelColor"))
        )

    }

    fun initTabLayout() {
        loginTabLayout.removeAllTabs()
        loginTabLayout.addTab(
            loginTabLayout.newTab().setCustomView(
                getThemedTabText(dataSource?.emailTabText ?: "EMAIL", true)
            )
        )

        loginTabLayout.addTab(
            loginTabLayout.newTab().setCustomView(
                getThemedTabText(dataSource?.phoneTabText ?: "PHONE", false)
            )
        )

        loginTabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabReselected(tab: TabLayout.Tab?) {}
            override fun onTabUnselected(tab: TabLayout.Tab?) {
                val tabText = tab?.customView as TapTextView
                if (LocalizationManager.getLocale(context).language == "en") {
                    tabText.typeface = Typeface.createFromAsset(
                        context?.assets, TapFont.tapFontType(
                            TapFont.RobotoRegular
                        )
                    )
                } else {
                    tabText.typeface = Typeface.createFromAsset(
                        context?.assets, TapFont.tapFontType(
                            TapFont.TajawalRegular
                        )
                    )
                }
                tabText.setTextColor(Color.parseColor(ThemeManager.getValue("goPay.loginBar.title.otherSegmentSelected.textColor")))

            }

            override fun onTabSelected(tab: TabLayout.Tab?) {
                val tabText = tab?.customView as TapTextView
                if (LocalizationManager.getLocale(context).language == "en") {
                    tabText.typeface = Typeface.createFromAsset(
                        context?.assets, TapFont.tapFontType(
                            TapFont.RobotoRegular
                        )
                    )
                } else {
                    tabText.typeface = Typeface.createFromAsset(
                        context?.assets, TapFont.tapFontType(
                            TapFont.TajawalRegular
                        )
                    )
                }
                tabText.setTextColor(Color.parseColor(ThemeManager.getValue("goPay.loginBar.title.selected.textColor")))
                inputType = if (tab.position == 0) EMAIL else PHONE
                if (tab.position == 0) {
                    countryCodePicker.visibility = View.GONE
                } else countryCodePicker.visibility = View.VISIBLE

                changeInputType()
            }
        })
    }

    private fun changeInputType() {
        textInput.text = null
        countryCodePicker.visibility = View.GONE
        loginMethodImage.visibility = View.VISIBLE

        textInputLayout.typeface = Typeface.createFromAsset(
            context?.assets, TapFont.tapFontType(
                TapFont.RobotoLight
            )
        )
        when (inputType) {

            EMAIL -> {
                textInput.hint = dataSource?.emailInputHint ?: "mail@mail.com"
                textInput.inputType = InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS
                textInput.setTextColor(Color.parseColor(ThemeManager.getValue("emailCard.textFields.textColor")))
                loginMethodImage.setImageResource(R.drawable.ic_mail)
                countryCodePicker.visibility = View.GONE
            }
            PHONE -> {
                countryCodePicker.visibility = View.VISIBLE
                textInput.hint = dataSource?.phoneInputHint ?: "00000000"
                textInput.inputType = InputType.TYPE_CLASS_PHONE
                textInput.setTextColor(Color.parseColor(ThemeManager.getValue("phoneCard.textFields.textColor")))
                loginMethodImage.setImageResource(R.drawable.ic_mobile)
                loginMethodImage.setOnClickListener { initCountryCodePicker() }
                setListenerForPhone()

            }
        }
    }

    fun setListenerForPhone() {
        textInput.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                countryCodePicker.contentColor =
                    Color.parseColor(ThemeManager.getValue("phoneCard.textFields.textColor"))

            }

            override fun afterTextChanged(mobileText: Editable) {
                if (mobileText.length > 1) {
                    countryCodePicker.contentColor =
                        Color.parseColor(ThemeManager.getValue("phoneCard.textFields.textColor"))
                } else {
                    countryCodePicker.contentColor =
                        Color.parseColor(ThemeManager.getValue("phoneCard.textFields.placeHolderColor"))
                }

            }

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
            }
        })
    }

    private fun getThemedTabText(text: String, isSelected: Boolean): TapTextView {
        val tabText = TapTextView(context, null)
        tabText.setTheme(FakeThemeManager.getGoPayTabLayoutTextTheme(isSelected))
        tabText.text = text

        if (LocalizationManager.getLocale(context).language == "en") {
            tabText.typeface = Typeface.createFromAsset(
                context?.assets, TapFont.tapFontType(
                    TapFont.RobotoRegular
                )
            )
        } else {
            tabText.typeface = Typeface.createFromAsset(
                context?.assets, TapFont.tapFontType(
                    TapFont.TajawalRegular
                )
            )
        }

        return tabText
    }

    override fun setTheme(theme: EditTextTheme) {
        theme.maxLines?.let { it }
        theme.textColor?.let { it }
        theme.textSize?.let { it }
        theme.letterSpacing?.let { it }
        theme.textColorHint?.let { }
        theme.backgroundTint?.let { backgroundTintList = ColorStateList.valueOf(it) }
    }

    fun setFontsEnglish() {
        textInput?.typeface = Typeface.createFromAsset(
            context?.assets, TapFont.tapFontType(
                TapFont.RobotoLight
            )
        )


        goPayHint?.typeface = Typeface.createFromAsset(
            context?.assets, TapFont.tapFontType(
                TapFont.RobotoLight
            )
        )
    }

    fun setFontsArabic() {
        textInput?.typeface = Typeface.createFromAsset(
            context?.assets, TapFont.tapFontType(
                TapFont.TajawalLight
            )
        )
        goPayHint?.typeface = Typeface.createFromAsset(
            context?.assets, TapFont.tapFontType(
                TapFont.TajawalLight
            )
        )

    }

    fun getSuccessDataSource(
        backgroundColor: Int,
        text: String,
        textColor: Int
    ): ActionButtonDataSource {
        return ActionButtonDataSource(
            text = text,
            textSize = 18f,
            textColor = textColor,
            cornerRadius = 100f,
            successImageResources = company.tap.checkout.R.drawable.checkmark,
            backgroundColor = backgroundColor
        )
    }

    override fun onCountrySelected() {
        countryCode = countryCodePicker.selectedCountryCode
//        countryCodeTxt.text = countryCodePicker.selectedCountryCode
    }


}